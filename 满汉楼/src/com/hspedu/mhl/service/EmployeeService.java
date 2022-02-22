package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.EmployeeDAO;
import com.hspedu.mhl.domain.Employee;

import java.sql.SQLException;

/**
 * @author 郑道炫
 * @version 1.0
 * 该类完成对employee表的各种操作（通过调用EmployeeDAO对象完成）
 */
public class EmployeeService {
    private EmployeeDAO employeeDAO =new EmployeeDAO();
    //定义一个Employee 属性
    public Employee getEmployeeByIdAndPwd(String empid,String pwd) throws SQLException {
        return employeeDAO.querySingle("select * from employee where empid = ? and pwd=md5(?)", Employee.class, empid,pwd);


    }



}

