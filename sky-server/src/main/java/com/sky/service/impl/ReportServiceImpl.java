package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Alan
 * @Date 2024/4/21 17:03
 * @Description
 */
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    //获取从begin到end的日期
    public List<LocalDate> getDateList(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }
        return dateList;
    }

    /**
     * 营业额数据统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //获取从begin到end的日期
        List<LocalDate> dateList = getDateList(begin, end);
        //存放每天营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询date这天，订单状态为“已完成”的订单总金额
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        //获取从begin到end的日期
        List<LocalDate> dateList = getDateList(begin, end);
        //存放每天新增用户数
        List<Integer> newUserList = new ArrayList<>();
        //存放每天总用户数量
        List<Integer> totalUserList = new ArrayList<>();
        Integer totalUser = 0;
        for (LocalDate date : dateList) {
            //查询date这天，create_time=date的用户数
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUser = userMapper.sumByDate(beginTime, endTime);
            newUser = newUser == null ? 0 : newUser;
            totalUser += newUser;
            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO getOrderReport(LocalDate begin, LocalDate end) {
        //获取从begin到end的日期
        List<LocalDate> dateList = getDateList(begin, end);
        //订单数:所有订单
        List<Integer> orderCountList = new ArrayList<>();
        //订单总数:totalOrderCount+=orderCountList
        Integer totalOrderCount = 0;
        //有效订单数列表:status 5
        List<Integer> validOrderCountList = new ArrayList<>();
        //有效订单数:validOrderCount+=validOrderCountList
        Integer validOrderCount = 0;
        //订单完成率: 有效 validOrderCount/所有订单 totalOrderCount
        Double orderCompletionRate = 0.0;
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //查询所有订单
            Integer orderCount = orderMapper.getOrderCount(beginTime, endTime);
            orderCount = orderCount == null ? 0 : orderCount;
            totalOrderCount += orderCount;
            orderCountList.add(orderCount);
            //有效订单
            Integer validOrder = orderMapper.getValidOrder(beginTime, endTime,Orders.COMPLETED);
            validOrder = validOrder == null ? 0 : validOrder;
            validOrderCount+=validOrder;
            validOrderCountList.add(validOrder);
        }
        orderCompletionRate=1.0*validOrderCount/totalOrderCount;
        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 销量排名前10
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(beginTime, endTime,Orders.COMPLETED);
        List<String> nameList = salesTop10.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        List<Integer> numberList = salesTop10.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }
}
