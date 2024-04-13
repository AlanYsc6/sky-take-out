package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @Author Alan
 * @Date 2024/4/13 16:50
 * @Description
 */
public interface UserService {
    /**
     * 微信登录
     * @param userLoginDTO 用户登录DTO
     * @return 用户实体
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
