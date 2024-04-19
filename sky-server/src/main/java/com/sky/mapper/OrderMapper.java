package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Alan
 * @Date 2024/4/19 10:56
 * @Description
 */
@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     * @param orders
     */
    void insert(Orders orders);
}
