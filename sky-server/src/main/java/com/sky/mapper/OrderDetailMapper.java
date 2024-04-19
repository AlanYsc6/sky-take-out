package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/19 10:57
 * @Description
 */
@Mapper
public interface OrderDetailMapper {
    /**
     * 批量插入订单明细信息
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);
}
