package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/12 13:47
 * @Description
 */
public interface SetmealService {
    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐信息
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐和关联的菜品数据
     *
     * @param id 套餐id
     * @return 套餐及菜品信息
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * 新增套餐及菜品信息
     *
     * @param setmealDTO 套餐及菜品信息
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 分页查询套餐及菜品信息
     *
     * @param setmealPageQueryDTO 套餐及菜品分页查询条件
     * @return 分页查询结果
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 删除套餐
     *
     * @param ids 套餐id集合
     */
    void deleteBatch(List<Long> ids);

    /**
     * 启用或停用套餐
     *
     * @param id     套餐id
     * @param status 状态 0-停用 1-启用
     */
    void startOrStop(Long id, Integer status);
}
