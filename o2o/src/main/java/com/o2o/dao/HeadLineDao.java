package com.o2o.dao;

import com.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 根据传入的查询条件（头条名查询头条）
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

    /**
     * 根据头条id查询头条信息
     * @param headLineId
     * @return
     */
    HeadLine queryHeadLineById(long headLineId);
}
