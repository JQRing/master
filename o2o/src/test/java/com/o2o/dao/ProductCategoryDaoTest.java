package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testQueryProductCategoryList() {
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(7L);
        System.out.println(list.size());
    }

    @Test
    public void testBatchInsertProductCategory(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("商品类别1");
        productCategory1.setPriority(1);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(1);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory1);
        list.add(productCategory2);

        int effectNum = productCategoryDao.batchInsertProductCategory(list);
        assertEquals(2, effectNum);
    }

    @Test
    public void testDeleteProductCategory() {
        int effectedNum = productCategoryDao.deleteProductCategory(19, 7);
        assertEquals(1, effectedNum);
    }
}
