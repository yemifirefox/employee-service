package com.yemifirefox.mongodb.employeeservice.repository;

import com.yemifirefox.mongodb.employeeservice.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    @Override
    List<Employee> findAll();           // find all Employee

    @Query(value = "{ 'employeeName' : ?0 }")
    List<Employee> findByEmployeeName(String name);       // find employee by name

    Employee findByEmployeeId(String id);           // find

    /*@Override
    void delete(String s);*/              // delete by ID
}
