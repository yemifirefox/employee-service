package com.yemifirefox.mongodb.employeeservice.service;

import com.yemifirefox.mongodb.employeeservice.model.Employee;

import java.util.List;

public interface EmployeeService {

        List<Employee> findAll();
        Employee save(Employee employee);
        void delete(String employeeId);
        Employee update(Employee employee);

        Employee find(String employeeId);
}
