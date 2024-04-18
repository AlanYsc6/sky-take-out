package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/18 15:09
 * @Description
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 动态查询购物车菜品
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新菜品份数
     * @param shoppingCart
     */
    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumber(ShoppingCart shoppingCart);

    /**
     * 添加购物车
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "VALUES (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 删除购物车中的一个商品
     * @param shoppingCart
     */
    @Delete("delete from shopping_cart where id=#{id}")
    void delete(ShoppingCart shoppingCart);

    /**
     * 根据用户id清空购物车
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteAllByUserId(Long userId);
}
