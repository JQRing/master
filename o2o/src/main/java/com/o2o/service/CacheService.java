package com.o2o.service;

public interface CacheService {

    /**
     * 根据key前缀删除匹配该模式下的所有key-value。如：传入shopcategory，则以shopcategory打头的key-value全部删除
     * @param keyPrefix
     */
    void removeFormCache(String keyPrefix);
}
