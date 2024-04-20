package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/20 21:28
 * @Description 订单定时任务类
 */
@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时未支付订单
     */
    @Scheduled(cron = "0 * * * * ?")//每分钟执行一次
    public void TimeOutTask(){
        log.info("定时检查支付超时订单:{}", new Date());
        LocalDateTime orderTime = LocalDateTime.now().plusMinutes(-15);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, orderTime);
        if(orders!=null&&orders.size()>0){
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**
     * 处理一直处于派送中订单
     */
    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点执行一次
    public void completeTask(){
        log.info("定时检查派送未完成订单:{}", new Date());
        LocalDateTime orderTime = LocalDateTime.now().plusHours(-1);
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS,orderTime);
        if(orders!=null&&orders.size()>0){
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
