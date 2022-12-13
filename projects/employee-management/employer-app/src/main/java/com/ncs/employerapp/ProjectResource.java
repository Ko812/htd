package com.ncs.employerapp;

import com.ncs.employerapp.model.Employee;
import com.ncs.employerapp.model.Project;
import com.ncs.employerapp.service.EmployeeService;
import com.ncs.employerapp.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectResource {
    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ProjectResource(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Project>> getAllProjects(){
        List<Project> projects = projectService.getAllProjects();
        System.out.println("Getting all projects ... " + projects);
        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id){
        Project project = projectService.findProjectById(id);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Project> addProject(@RequestBody Project project){
        ArrayList<Employee> members = new ArrayList<Employee>();
        for(int i =0; i < project.getEmployees().size(); i++){
            Employee e = employeeService.findEmployeeById(project.getEmployees().get(i).getId());
            members.add(e);
        }
        project.setEmployees(members);
        Project newProject = projectService.addProject(project);
        return new ResponseEntity<Project>(newProject, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Project> updateProject(@RequestBody Project project){
        ArrayList<Employee> members = new ArrayList<Employee>();
        for(int i =0; i < project.getEmployees().size(); i++){
            Employee e = employeeService.findEmployeeById(project.getEmployees().get(i).getId());
            members.add(e);
        }
        project.setEmployees(members);
        Project updatedProject = projectService.updateProject(project);
        return new ResponseEntity<Project>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> updateProject(@PathVariable("id") Long id){
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
