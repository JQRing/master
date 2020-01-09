package com.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.cache.JedisUtil;
import com.o2o.dao.ShopCategoryDao;
import com.o2o.entity.ShopCategory;
import com.o2o.exceptions.ShopOperationException;
import com.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
        // 定义redis的key
        String key = SCLISTKEY;
        // 定义接收对象
        List<ShopCategory> listShopCategory = null;
        // 定义Jackson数据转换操作
        ObjectMapper mapper = new ObjectMapper();
        // 凭借redis的key
        if (shopCategory == null) {
            // 若查询条件为空，则列出所有首页大类，即parentID为空的店铺类别
            key = key + "_allfirstlevel";
        } else if (shopCategory != null && shopCategory.getParent() != null && shopCategory.getParent().getShopCategoryId() != null) {
            // 若parentID不为空，则列出该parentID下的所有子类别
            key = key + "_parent" + shopCategory.getParent().getShopCategoryId();
        } else if (shopCategory != null) {
            // 列出所有子类别
            key = key + "_allsecondlevel";
        }
        // 判断redis中是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库中取出相应的数据
            listShopCategory = shopCategoryDao.queryShopCategory(shopCategory);
            // 将数据存入redis缓存中
            // 将相关实体类转换成String，存入redis对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(listShopCategory);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
                throw new ShopOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            // 若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 将String转换成集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                listShopCategory = mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new ShopOperationException(e.getMessage());
            }
        }
        return listShopCategory;
    }
}
