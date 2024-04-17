package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录方法，根据提供的员工登录数据进行登录
     *
     * @param employeeLoginDTO 员工登录数据传输对象
     * @return 登录成功后返回员工对象，登录失败返回null
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 保存员工信息到数据库
     *
     * @param employeeDTO 员工数据传输对象
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStatus(StatusConstant.ENABLE);
        //密码加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employeeMapper.insert(employee);
    }

    /**
     * 获取员工分页信息
     *
     * @param employeePageQueryDTO 员工分页查询DTO
     * @return 包含员工分页信息的Result对象
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //1、分页查询员工数据
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> employeePage = employeeMapper.pageQuery(employeePageQueryDTO);
        //2、封装PageResult对象并返回
        return new PageResult(employeePage.getTotal(), employeePage.getResult());
    }

    /**
     * 修改员工状态
     *
     * @param status 员工状态
     * @param id     员工ID
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据ID获取员工信息
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword(null);
        return employee;
    }

    /**
     * 更新员工信息
     *
     * @param employeeDTO 员工数据传输对象
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.update(employee);
    }

    /**
     * 修改密码
     *
     * @param passwordEditDTO 密码信息
     */
    @Override
    public void updatePassword(PasswordEditDTO passwordEditDTO) {
        //获取当前登录用户的id
        Long empId= BaseContext.getCurrentId();
        String oldPassword = passwordEditDTO.getOldPassword();
        String newPassword = passwordEditDTO.getNewPassword();

        //1、根据用户id数据库中的数据
        Employee employee = employeeMapper.getById(empId);

        //2、处理异常情况（旧密码不对）
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        if (!oldPassword.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_EDIT_FAILED);
        }
        //旧密码正确，根据员工id修改密码
        //新密码加密
        employee.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
        employeeMapper.update(employee);
    }
}
