package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId 分类id
     * @return 菜品数量
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品
     *
     * @param dish 菜品对象
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询条件
     * @return 分页对象
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //根据id查询菜品信息
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    //根据ids批量删除菜品信息
    void deleteByIds(List<Long> ids);

    //更新菜品信息
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    //查询分类菜品信息
    List<Dish> list(Dish dish);

    //根据套餐id查询菜品信息
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
