package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ProductService {

    /**
     * 添加商品新及图片
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs) throws ProductOperationException;

    /**
     * 查询商品列表页，可输入的条件有：商品名（模糊），商品状态，店铺ID，商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    Product getProductById(long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgs) throws RuntimeException;

}
