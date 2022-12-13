package com.ncs.employerapp.service;

import com.ncs.employerapp.exception.ProjectNotFoundException;
import com.ncs.employerapp.model.Project;
import com.ncs.employerapp.repo.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public Project addProject(Project project){
        return projectRepo.save(project);
    }

    public List<Project> getAllProjects(){
        return projectRepo.findAll();
    }

    public Project updateProject(Project project){

        return projectRepo.save(project);
    }

    public Project findProjectById(Long id) {
        return projectRepo.findById(id).orElseThrow(()-> new ProjectNotFoundException("Project by id " + id + " was not found."));
    }

    public void deleteProject(Long id){
        projectRepo.deleteById(id);
    }
}
