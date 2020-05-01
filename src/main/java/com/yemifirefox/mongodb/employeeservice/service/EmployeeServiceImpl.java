package com.yemifirefox.mongodb.employeeservice.service;

import com.yemifirefox.mongodb.employeeservice.model.Employee;
import com.yemifirefox.mongodb.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    @Override
    public void delete(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employeeRepository.delete(employee);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee find(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }


}
