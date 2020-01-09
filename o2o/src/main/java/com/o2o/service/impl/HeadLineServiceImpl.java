package com.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.cache.JedisUtil;
import com.o2o.dao.HeadLineDao;
import com.o2o.entity.HeadLine;
import com.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        // 定义接受对象
        List<HeadLine> headLineList = null;
        //  定义Jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();
        // 定义redis的key前缀
        String key = HLLISTKEY;
        // 拼接处redis的key
        if (headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        // 判断redis的key是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库取数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            // 将相关实体类集合转换成String，存入redis里面对应的key中
            String jsonString = mapper.writeValueAsString(headLineList);
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, HeadLine.class);
            headLineList = mapper.readValue(jsonString, javaType);
        }
        return headLineList;
    }
}
