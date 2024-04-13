package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Alan
 * @Date 2024/4/12 11:07
 * @Description 套餐管理
 */
@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 修改套餐
     *
     * @param setmealDTO    套餐信息
     * @return Result
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐：{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
    /**
     * 根据id查询套餐，用于修改页面回显数据
     *
     * @param id 套餐id
     * @return 套餐信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getBySetmealId(@PathVariable Long id) {
        log.info("根据id查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDish(id);
        return Result.success(setmealVO);
    }
    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐信息
     * @return Result
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐：{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 套餐分页查询条件
     * @return PageResult
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 删除套餐
     *
     * @param ids 套餐id集合
     * @return 返回成功结果
     */
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        setmealService.deleteBatch(ids);
        return Result.success();
    }
    /**
     * 套餐状态修改
     * @param id 套餐id
     * @param status 状态
     * @return 返回成功结果
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐状态")
    public Result startOrStop(@PathVariable Integer status,Long id) {
        log.info("修改套餐状态：id={}, status={}", id, status);
        setmealService.startOrStop(id, status);
        return Result.success();
    }
}
