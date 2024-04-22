package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author Alan
 * @Date 2024/4/19 10:56
 * @Description
 */
@Mapper
public interface OrderMapper {
    /**
     * 插入订单信息
     *
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 分页查询历史订单
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     *
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据订单状态和下单时间查询订单信息
     *
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select *from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 营业额统计
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 订单总数
     * @param begin
     * @param end
     * @return
     */
    Integer getOrderCount(LocalDateTime begin, LocalDateTime end);

    /**
     * 有效订单数
     * @param begin
     * @param end
     * @param status
     * @return
     */
    Integer getValidOrder(LocalDateTime begin, LocalDateTime end,Integer status);

    /**
     * 销量排名前10
     * @param begin
     * @param end
     * @param status
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end,Integer status);
    /**
     * 根据动态条件统计订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
