package com.yemifirefox.mongodb.employeeservice.controller;

import com.yemifirefox.mongodb.employeeservice.model.Employee;
import com.yemifirefox.mongodb.employeeservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.function.Consumer;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // to get employee by ID
    @Cacheable(value = "employee", key = "#employeeId")//unless = "#result.followers < 12000")
    @RequestMapping(value = "employee/{employeeId}",method = RequestMethod.GET)     // or user @GetMapping
    public Employee findUser(@PathVariable String employeeId){
        return employeeService.find(employeeId);
    }

    // to add new employee
    @RequestMapping(value = "save",method = RequestMethod.POST)     // or user @GetMapping
    public Employee save(@RequestBody Employee employee){
        return employeeService.save(employee);
    }

    // to update employee
    @CachePut(value = "employee", key = "#employee.employeeId")//redis cache is here
    @RequestMapping(value = "update",method = RequestMethod.POST)     // or user @GetMapping
    public Employee update(@RequestBody Employee employee){
        return employeeService.update(employee);
    }

    // list of all employee
    @RequestMapping(value = "list",method = RequestMethod.GET)   // or use @GetMapping
    public java.util.List<Employee> listEmployee() {
        Consumer<String> c1 = s -> System.out.println("c1 " +s);
        return employeeService.findAll();
    }

    // delete specific employee using employee id
    @CacheEvict(value = "employee",key = "#employee.employeeId")//allEntries=true)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)        // or use @DeleteMapping
    public void delete(@RequestParam("id")String id){
        employeeService.delete(id);
    }
}
