package com.o2o.service;

import com.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";

    /**
     * 根据查询条件获取shopCategory
     * @param shopCategory
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategory);
}
