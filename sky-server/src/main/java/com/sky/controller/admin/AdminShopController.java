package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Alan
 * @Date 2024/4/13 10:17
 * @Description 管理端店铺相关接口
 */
@RestController
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "管理端店铺相关接口")
public class AdminShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺状态：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(StatusConstant.REDIS_SHOP_STATUS_KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(StatusConstant.REDIS_SHOP_STATUS_KEY);
        log.info("获取店铺状态{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
