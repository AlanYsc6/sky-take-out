package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录方法，根据提供的员工登录数据进行登录
     * @param employeeLoginDTO 员工登录数据传输对象
     * @return 登录成功后返回员工对象，登录失败返回null
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    /**
     * 保存员工信息到数据库
     * @param employeeDTO 员工数据传输对象
     */
    void save(EmployeeDTO employeeDTO);
    /**
     * 获取员工分页信息
     * @param employeePageQueryDTO 员工分页查询DTO
     * @return 包含员工分页信息的Result对象
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    /**
     * 修改员工状态
     * @param status 员工状态
     * @param id 员工ID
     */
    void updateStatus(Integer status, Long id);

    Employee getEmployeeById(Long id);

    void update(EmployeeDTO employeeDTO);
}
