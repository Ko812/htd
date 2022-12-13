package com.ncs.employerapp.service;

import com.ncs.employerapp.exception.DepartmentNotFoundException;
import com.ncs.employerapp.model.Department;
import com.ncs.employerapp.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public Department addDepartment(Department department){
        System.out.println(department.getDepartmentName() + " " + department.getDepartmentCode());
        return departmentRepo.save(department);
    }

    public List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }

    public Department updateDepartment(Department department){
        return departmentRepo.save(department);
    }

    public Department findDepartmentById(Long id) {
        return departmentRepo.findById(id).orElseThrow(()-> new DepartmentNotFoundException("Department by id " + id + " was not found."));
    }

    public Department findDepartmentByName(String name) {
        return departmentRepo.findByDepartmentName(name).orElseThrow(()-> new DepartmentNotFoundException("Department by name " + name + " was not found."));
    }

    public void deleteDepartment(Long id){
        departmentRepo.deleteById(id);
    }
}
