package com.o2o.service;

import com.o2o.dto.ImageHolder;
import com.o2o.dto.ShopExecution;
import com.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;

public interface ShopService {


    /**
     * 根据shopCondition返回分页数据
     * @param shopCondititon
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondititon, int pageIndex, int pageSize);

    /**
     * 通过店铺ID查询指定店铺信息
     * @param shopId
     * @return Shop shop
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息（从店家角度）
     * @param shop
     * @param thumbnail
     * @return
     * @throws RuntimeException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws RuntimeException;

    /**
     * 添加商铺
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail);
}
