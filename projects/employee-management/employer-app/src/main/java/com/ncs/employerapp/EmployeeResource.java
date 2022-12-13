package com.ncs.employerapp;

import com.ncs.employerapp.model.Employee;
import com.ncs.employerapp.model.Employees;
import com.ncs.employerapp.model.Project;
import com.ncs.employerapp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private final EmployeeService employerService;

    public EmployeeResource(EmployeeService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employees = employerService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
        Employee employee = employerService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        Employee newEmployee = employerService.addEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employerService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @PutMapping("/updateEmployeesProject")
    public ResponseEntity<List<Employee>> updateEmployeesProject(@RequestBody Project persistedProject){
        Project dummyProj = new Project();
        dummyProj.setProjectCode(persistedProject.getProjectCode());
        for(Employee e : persistedProject.getEmployees()){
            e.setProject(dummyProj);
        }
        List<Employee> updatedEmployees = employerService.updateEmployeesProject(persistedProject.getEmployees());
        return new ResponseEntity<>(updatedEmployees, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id){
        employerService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
