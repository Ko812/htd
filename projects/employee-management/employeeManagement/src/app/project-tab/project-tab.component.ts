import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Project } from '../project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-project-tab',
  templateUrl: './project-tab.component.html',
  styleUrls: ['./project-tab.component.css']
})
export class ProjectTabComponent implements OnInit{

  constructor(private projectService: ProjectService) { }

  projects: Project[] = [];

  ngOnInit(): void {
    this.getAllProjects();
  }


  public goToProjectForm(): void {
    location.href = "http://localhost:4200/projects/createProjectForm";
  }

  public editProject(projectCode: number): void {
    location.href = "http://localhost:4200/projects/updateProjectForm?projectCode="+projectCode;
  }

  public getAllProjects(): void {
    this.projectService.getAllProjects().subscribe({
      next: (response: Project[]) => {
        this.projects = response;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log("Employees data retrieved");
      }
    });
  }

  public deleteProject(id: number, title: String): void {
    console.log("deleting", id);
    if (confirm(`Confirm delete project ${title}?`)) {
      this.projectService.deleteProject(id).subscribe({
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
        complete: () => {
          console.log(`project ${title} deleted`);
          this.getAllProjects();
        }
      });
    }
  }
}
