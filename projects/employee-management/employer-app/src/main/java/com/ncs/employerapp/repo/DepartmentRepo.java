package com.ncs.employerapp.repo;

import com.ncs.employerapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    public Optional<Department> findByDepartmentName(String departmentName);
}
