package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

/**
 * @Author Alan
 * @Date 2024/4/18 15:04
 * @Description
 */
public interface ShoppingCartService {
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
