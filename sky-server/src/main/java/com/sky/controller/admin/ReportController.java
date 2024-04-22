package com.sky.controller.admin;

import com.sky.dto.DataOverViewQueryDTO;
import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Alan
 * @Date 2024/4/21 16:56
 * @Description 数据统计
 */
@Slf4j
@RestController
@Api(tags = "数据统计")
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     *
     * @param dataOverViewQueryDTO
     * @return
     */
    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(DataOverViewQueryDTO dataOverViewQueryDTO) {
        log.info("营业额统计：{}", dataOverViewQueryDTO);
        TurnoverReportVO turnoverReportVO = reportService.getTurnoverStatistics(dataOverViewQueryDTO.getBegin(), dataOverViewQueryDTO.getEnd());
        return Result.success(turnoverReportVO);
    }

    /**
     * 用户统计
     * @param dataOverViewQueryDTO
     * @return
     */
    @ApiOperation("用户统计")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(DataOverViewQueryDTO dataOverViewQueryDTO) {
        log.info("用户统计：{}",dataOverViewQueryDTO);
        UserReportVO userReportVO=reportService.getUserStatistics(dataOverViewQueryDTO.getBegin(), dataOverViewQueryDTO.getEnd());
        return Result.success(userReportVO);
    }

    /**
     * 订单统计
     * @param dataOverViewQueryDTO
     * @return
     */
    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(DataOverViewQueryDTO dataOverViewQueryDTO) {
        log.info("订单统计：{}",dataOverViewQueryDTO);
        OrderReportVO orderReportVO=reportService.getOrderReport(dataOverViewQueryDTO.getBegin(), dataOverViewQueryDTO.getEnd());
        return Result.success(orderReportVO);
    }
    /**
     * 销量排名
     * @param dataOverViewQueryDTO
     * @return
     */
    @ApiOperation("销量排名")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(DataOverViewQueryDTO dataOverViewQueryDTO) {
        log.info("销量排名：{}",dataOverViewQueryDTO);
        SalesTop10ReportVO salesTop10ReportVO=reportService.getSalesTop10(dataOverViewQueryDTO.getBegin(), dataOverViewQueryDTO.getEnd());
        return Result.success(salesTop10ReportVO);
    }
}
