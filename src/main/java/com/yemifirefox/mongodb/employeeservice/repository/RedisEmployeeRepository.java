package com.yemifirefox.mongodb.employeeservice.repository;

import com.yemifirefox.mongodb.employeeservice.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class RedisEmployeeRepository {

    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public RedisEmployeeRepository(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(Employee employee){
        hashOperations.put("EMPLOYEE", employee.getEmployeeId(), employee);
    }
    public List findAll(){
        return hashOperations.values("EMPLOYEE");
    }

    public Employee findById(String id){
        return (Employee) hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee){
        save(employee);
    }

    public void delete(String id){
        hashOperations.delete("USER", id);
    }

}
