package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    //批量插入菜品口味
    void insertBatch(List<DishFlavor> flavors);

    //根据菜品dishIds批量删除菜品口味

    void deleteByDishIds(List<Long> dishIds);

    //根据菜品id查询口味
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
    //根据菜品dishId删除菜品口味
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);
}
