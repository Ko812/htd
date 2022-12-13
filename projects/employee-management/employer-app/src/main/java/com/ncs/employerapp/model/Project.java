package com.ncs.employerapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_code", nullable = false, updatable = false)
    private Long projectCode;

    @Column(name = "project_title")
    private String projectTitle;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Employee> employees;

    public Project(Long id, String projectTitle) {
        this.projectCode = id;
        this.projectTitle = projectTitle;
    }

    public Project() {
    }

    public Long getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Long id) {
        this.projectCode = id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectTitle='" + projectTitle + '\'' +
                '}';
    }
}
