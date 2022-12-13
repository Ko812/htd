package com.ncs.employerapp.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "departments")
public class Department implements Serializable{

    @Id
    @Column(name = "department_code", nullable = false, updatable = false)
    private Long departmentCode;

    @Column(name = "department_name")
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Department(Long id, String departmentName) {
        this.departmentCode = id;
        this.departmentName = departmentName;
        this.employees = new ArrayList<Employee>();
    }

    public Department() {
    }

    public Long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(Long id) {
        this.departmentCode = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @OneToMany(mappedBy = "department", cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentCode=" + departmentCode +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
