package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Author Alan
 * @Date 2020/6/19 16:37
 * @Description 菜品管理
 */
@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品DTO
     * @return 返回成功结果
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        String key = "dish_" + dishDTO.getCategoryId();
        //清空redis缓存
        cleanCache(key);
        return Result.success();
    }

    /**
     * 获取菜品分页信息
     *
     * @param dishPageQueryDTO 菜品分页查询DTO
     * @return 包含菜品分页信息的Result对象
     */
    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     *
     * @param ids 菜品id集合
     * @return 返回成功结果
     */
    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.deleteBatch(ids);
        //清空redis缓存
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return 包含菜品信息的Result对象
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getByDishId(@PathVariable Long id) {
        log.info("查询菜品：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId 菜品类型id
     * @return 包含菜品信息的Result对象
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("分类查询菜品：id={}", categoryId);
        List<Dish> dishes = dishService.list(categoryId);
        return Result.success(dishes);
    }

    /**
     * 修改菜品
     *
     * @param dishDTO 菜品DTO
     * @return 返回成功结果
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        //清空redis缓存
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 菜品状态修改
     *
     * @param id     菜品id
     * @param status 状态
     * @return 返回成功结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改菜品状态：id={}, status={}", id, status);
        dishService.startOrStop(id, status);
        //清空redis缓存
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * 清理redis缓存接口
     *
     * @param pattern 清理模式
     */
    private void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
