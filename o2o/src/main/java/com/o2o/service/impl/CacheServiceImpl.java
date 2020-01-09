package com.o2o.service.impl;

import com.o2o.cache.JedisUtil;
import com.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private JedisUtil.Keys keys;

    @Override
    public void removeFormCache(String keyPrefix) {
        Set<String> keySet = keys.keys(keyPrefix + "*");
        for (String key : keySet) {
            keys.del(key);
        }
    }
}
