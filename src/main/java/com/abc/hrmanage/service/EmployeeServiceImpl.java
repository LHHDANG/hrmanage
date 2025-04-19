package com.abc.hrmanage.service;

import com.abc.hrmanage.model.Employee;
import com.abc.hrmanage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }
    
    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
    
    @Override
    public List<Employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.searchByIdOrName(keyword);
    }
}