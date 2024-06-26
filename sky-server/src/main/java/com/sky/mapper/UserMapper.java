package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     *
     * @param openid 用户的openid
     * @return USer 用户对象
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 在数据库中插入用户信息
     *
     * @param user 用户对象
     */
    void insert(User user);

    //根据id查询员工信息
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    /**
     * 新增用户数统计
     * @param begin
     * @param end
     * @return
     */
    Integer sumByDate(LocalDateTime begin, LocalDateTime end);

    /**
     * 根据动态条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
