package com.o2o.service.impl;

import com.o2o.dao.ProductCategoryDao;
import com.o2o.dao.ProductDao;
import com.o2o.dto.ProductCategoryExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.enums.ProductCategoryStateEnum;
import com.o2o.exceptions.ProductCategoryOperationException;
import com.o2o.service.ProductCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    public static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchProductCategory(List<ProductCategory> productCategorieList)
            throws ProductCategoryOperationException {
        if (productCategorieList != null && productCategorieList.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategorieList);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchProductCategory error:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException{
        // 解除tb_product里的商品与productCategoryID的关联
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum < 0) {
                throw new ProductCategoryOperationException("商品类别更新失败！");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
        // 删除productCategoryId类别
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                logger.error("删除商品类别失败");
                throw new ProductCategoryOperationException("删除商品类别失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("deleteProductCategory error:" + e.getMessage());
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
    }
}
