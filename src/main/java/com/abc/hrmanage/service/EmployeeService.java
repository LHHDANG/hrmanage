package com.abc.hrmanage.service;

import com.abc.hrmanage.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    
    List<Employee> getAllEmployees();
    
    Employee saveEmployee(Employee employee);
    
    Optional<Employee> getEmployeeById(String id);
    
    void deleteEmployee(String id);
    
    List<Employee> searchEmployees(String keyword);
}