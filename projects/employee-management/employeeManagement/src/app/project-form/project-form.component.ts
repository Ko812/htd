import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Project } from '../project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-project-form',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.css']
})
export class ProjectFormComponent implements OnInit {
  formHeader: String;

  submitBtnLabel: String;

  projTitle: String = "";

  projectTitleOutline: String = "1px solid grey";

  creatorName: string = "";

  creatorEmployeeId: number = 0;

  message: String = "";

  employeesMap: Map<String, Number> = new Map();

  allEmployees: Employee[] = [];

  projectMembers: Employee[] = [];

  pendingProject: Project | undefined;

  constructor(
    private projectService: ProjectService,
    private employeeService: EmployeeService
  ) {
    let formMode: string = location.href.split("/").slice(-1)[0];
    this.formHeader = "Create New Project";
    this.submitBtnLabel = "Create";
    this.creatorName = "Aaron Koh";
    this.creatorEmployeeId = 123;
  }

  ngOnInit(): void {
    this.getAllEmployees();
  }

  onTitleKeyup(event: any): void {
    this.projTitle = event.target.value;
    let valid = this.validate(this.projTitle, "Name");
    if (valid) {
        this.projectTitleOutline = "1px solid greenyellow"
    } else {
        this.projectTitleOutline = "1px solid red";
    }
  }

  validate = (val : String, tagName: String): boolean => {
    if (val.length === 0) {
        this.message = `${tagName} should not be empty.`;
        return false;
    }
    else if (val.length < 7) {
        this.message = `${tagName} should not be less than 8.`;
        return false;
    } else {
        this.message = "";
        return true;
    }
  }

  public removeEmployee(id: number, name: string): void {
    let index = this.projectMembers.findIndex((employee) => employee.id == id);
    this.projectMembers.splice(index, 1);
  }

  public getProjectById(id: number): void {
    this.projectService.getProjectById(id).subscribe({
      next: (response: Project) => {
        this.pendingProject = response;
        if (this.pendingProject) {
          this.projTitle = this.pendingProject.projectTitle;
          this.projectMembers = this.pendingProject.employees;
          this.creatorName = this.pendingProject.createdBy;
          this.creatorEmployeeId = this.pendingProject.creatorId;
          console.log("pending project", this.pendingProject);
        }
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log("Employee data retrieved");
      }
    });
  }

  public getAllEmployees(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (response) => {
        this.allEmployees = response;
        let empMap = new Map<String, Number>();
        response.forEach((emp) => {
          empMap.set(emp.name, emp.id);
        });
        this.employeesMap = empMap;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public getRole(mem: any): String {
    return this.allEmployees.find((val) => val.name === mem)!.role;
  }

  public getDepartment(mem: any): String {
    return this.allEmployees.find((val) => val.name === mem)!.department!.departmentName;
  }

  public getId(mem: any): Number {
     return this.allEmployees.find((val) => val.name === mem)!.id;
  }


  public submit(createProjectForm: NgForm): void {
    let empArray: Array<EmployeeCode> = new Array<EmployeeCode>();
      for (let mem of this.projectMembers) {
        empArray.push(new EmployeeCode(this.getId(mem)));
      }
      createProjectForm.value.employees = empArray;
      this.projectService.addProject(createProjectForm.value).subscribe({
        next: (response: Project) => {
          let persistedProject: Project = response;
          console.log("project added...");
          console.dir(response);
          if (persistedProject) {
            this.employeeService.updateEmployeesProject(persistedProject).subscribe(
            {
              error: (error: HttpErrorResponse) => {
                console.log("Error updating employees");
                alert(error.message);
                },
              next: (response) => {
                location.href = "http://localhost:4200/projects";
                console.log("employees updated");
              }
            }
          );
          }
        },
        error: (error: HttpErrorResponse) => {
              alert(error.message);
        },
        complete: () => {

        }
        });
  }

  onEscPressed = (e: Event): void => {
    this.message = "";
    this.projectTitleOutline = "1px solid grey";
  }
}

class EmployeeCode {
  id: Number;
  constructor(id: Number) {
    this.id = id;
  }
}
