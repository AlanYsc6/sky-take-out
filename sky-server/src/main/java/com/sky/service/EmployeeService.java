package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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
}
