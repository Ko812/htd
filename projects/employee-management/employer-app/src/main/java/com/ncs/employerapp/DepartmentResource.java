package com.ncs.employerapp;

import com.ncs.employerapp.model.Department;
import com.ncs.employerapp.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentResource {

    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService service) {
        this.departmentService = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Department>> getAllDepartments(){
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id){
        Department department = departmentService.findDepartmentById(id);

        return new ResponseEntity<Department>(department, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable("name") String name){
        Department department = departmentService.findDepartmentByName(name);
        return new ResponseEntity<Department>(department, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        Department newDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department){
        return new ResponseEntity<>(departmentService.updateDepartment(department), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long id){
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
