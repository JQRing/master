package com.o2o.service;

import com.o2o.BaseTest;
import com.o2o.dto.ImageHolder;
import com.o2o.dto.ProductExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductCategory;
import com.o2o.entity.Shop;
import com.o2o.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1l);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        // 创建缩略图文件流
        File thumbnailFile = new File("F:\\Study\\SSM大型互联网电商项目\\需要的图片\\需要的图片\\images\\item\\headtitle\\2017061320315746624.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), is);
        File productImg1 = new File("F:\\Study\\SSM大型互联网电商项目\\需要的图片\\需要的图片\\images\\item\\headtitle\\2017061320315746624.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("F:\\Study\\SSM大型互联网电商项目\\需要的图片\\需要的图片\\images\\item\\headtitle\\2017061320371786788.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> list = new ArrayList<>();
        list.add(new ImageHolder(productImg1.getName(), is1));
        list.add(new ImageHolder(productImg2.getName(), is2));
        // 添加商品并验证
        ProductExecution pe = productService.addProduct(product, imageHolder, list);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
}
