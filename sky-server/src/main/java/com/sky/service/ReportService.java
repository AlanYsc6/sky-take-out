package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

/**
 * @Author Alan
 * @Date 2024/4/21 17:03
 * @Description
 */
public interface ReportService {
    /**
     * 营业额数据统计
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);
}
