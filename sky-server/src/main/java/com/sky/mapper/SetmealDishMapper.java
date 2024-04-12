package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id查询套餐id
     * @param dishIds 菜品id
     * @return 套餐id
     */
    List<Long> getsetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐菜品关联数据
     * @param setmealDishes 套餐菜品关联数据
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐id删除套餐菜品关联数据
     * @param setmealId 套餐id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 批量删除套餐菜品关联数据
     * @param setmealIds   套餐ids
     */
    void deleteBysetmealIds(List<Long> setmealIds);
}
