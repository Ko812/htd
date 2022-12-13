package com.ncs.employerapp.service;

import com.ncs.employerapp.exception.UserNotFoundException;
import com.ncs.employerapp.model.Employee;
import com.ncs.employerapp.model.Employees;
import com.ncs.employerapp.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Employee addEmployee(Employee employee){
        LocalDateTime now = LocalDateTime.now();
        employee.setCreationDate(Date.valueOf(LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth())));
        return employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployees(){

        List<Employee> employees = employeeRepo.findAll();

        return employees;
    }

    public Employee updateEmployee(Employee employee){
        LocalDateTime now = LocalDateTime.now();
        employee.setDateOfUpdate(Date.valueOf(LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth())));
        System.out.println(employee.getDateOfUpdate());
        return employeeRepo.save(employee);
    }

    public List<Employee> updateEmployeesProject(List<Employee> employees){
        LocalDateTime now = LocalDateTime.now();
        for(Employee emp : employees){
            System.out.println(emp.getProject().getProjectCode() + " " + emp.getProject().getProjectTitle());
            emp.setDateOfUpdate(Date.valueOf(LocalDate.of(now.getYear(), now.getMonth(), now.getDayOfMonth())));
        }
        return employeeRepo.saveAll(employees);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User by id " + id + " was not found."));
    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }
}
