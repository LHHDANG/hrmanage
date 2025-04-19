package com.abc.hrmanage.controller;

import com.abc.hrmanage.model.Employee;
import com.abc.hrmanage.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    // Display all employees
    @GetMapping("/")
    public String viewHomePage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Employee> employeeList;
        
        if (keyword != null && !keyword.isEmpty()) {
            employeeList = employeeService.searchEmployees(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            employeeList = employeeService.getAllEmployees();
        }
        
        model.addAttribute("employees", employeeList);
        return "index";
    }
    
    // Show add employee form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }
    
    // Save employee
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee has been saved successfully!");
        return "redirect:/";
    }
    
    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        var employee = employeeService.getEmployeeById(id);
        
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "edit-employee";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found!");
            return "redirect:/";
        }
    }
    
    // Update employee
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "edit-employee";
        }
        
        employeeService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("successMessage", "Employee has been updated successfully!");
        return "redirect:/";
    }
    
    // Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteEmployee(id);
            redirectAttributes.addFlashAttribute("successMessage", "Employee has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete employee: " + e.getMessage());
        }
        return "redirect:/";
    }
    
    // Search employee
    @GetMapping("/search")
    public String searchEmployee(@RequestParam("keyword") String keyword, Model model) {
        List<Employee> results = employeeService.searchEmployees(keyword);
        model.addAttribute("employees", results);
        model.addAttribute("keyword", keyword);
        return "index";
    }
}