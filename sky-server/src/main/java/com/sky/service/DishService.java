package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/11 14:38
 * @Description
 */
public interface DishService {
    /**
     * 新增菜品和口味
     *
     * @param dishDTO 菜品信息
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询菜品信息
     *
     * @param dishPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品信息
     *
     * @param ids 菜品id列表
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品信息
     *
     * @param id 菜品id
     * @return 菜品信息
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息
     *
     * @param dishDTO 菜品信息
     */
    void updateWithFlavor(DishDTO dishDTO);
}
