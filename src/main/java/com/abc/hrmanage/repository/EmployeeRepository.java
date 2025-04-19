package com.abc.hrmanage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.hrmanage.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    
    @Query("SELECT e FROM Employee e WHERE e.employeeId LIKE %:keyword% OR e.employeeName LIKE %:keyword%")
    List<Employee> searchByIdOrName(@Param("keyword") String keyword);
}