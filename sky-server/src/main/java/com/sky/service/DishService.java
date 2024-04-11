package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @Author Alan
 * @Date 2024/4/11 14:38
 * @Description
 */
public interface DishService {
    /**
     * 新增菜品和口味
     * @param dishDTO 菜品信息
     */
    void saveWithFlavor(DishDTO dishDTO);
}
