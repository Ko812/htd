import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormControl, NgForm } from '@angular/forms';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Project } from '../project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-project-update-form',
  templateUrl: './project-update-form.component.html',
  styleUrls: ['./project-update-form.component.css']
})
export class ProjectUpdateFormComponent implements OnInit {
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

  projMembers: FormControl = new FormControl('');

  pendingProject: Project | undefined;

  constructor(
    private projectService: ProjectService,
    private employeeService: EmployeeService
  ) {
    this.formHeader = "Update Project";
    this.submitBtnLabel = "Update";
    let projectId: number = Number.parseInt( location.href.split("?projectCode=")[1]);
    this.getProjectById(projectId);
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
          console.log("loading project " + this.pendingProject.projectCode);
          this.projTitle = this.pendingProject.projectTitle;
          this.projectMembers = this.pendingProject.employees;
          this.projMembers.setValue(this.pendingProject.employees);
          console.dir(this.projectMembers);
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
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
    });
  }

  public getName(mem: any): String {
    if (typeof (mem) == "string") {
      return mem;
    }
    if (typeof (mem) == "object") {
      return mem.name;
    }
    return "";
    // console.log("loading mem " + mem);
    // console.dir(mem);
    // console.log(typeof mem);
    // return this.allEmployees.find((val) => val.name == mem)!.name;
  }

  public getRole(mem: any): String {
    if (typeof (mem) == "string") {
      return this.allEmployees.find((val) => val.name === mem)!.role;
    }
    if (typeof (mem) == "object") {
      return mem.role;
    }
    return "";

  }

  public getDepartment(mem: any): String {
    if (typeof (mem) == "string") {
      return this.allEmployees.find((val) => val.name === mem)!.department!.departmentName;
    }
    if (typeof (mem) == "object") {
      return mem.department!.departmentName;
    }
    return "";
  }

  public getId(mem: any): Number {
     return this.allEmployees.find((val) => val.name === mem)!.id;
  }

  public submit(updateProjectForm: NgForm): void {
    updateProjectForm.value.projectCode = this.pendingProject?.projectCode;
    let empArray: Array<EmployeeCode> = new Array<EmployeeCode>();
    for (let mem of this.projectMembers) {
        empArray.push(new EmployeeCode(this.getId(mem)));
    }
    updateProjectForm.value.employees = empArray;
    this.projectService.updateProject(updateProjectForm.value).subscribe({
        next: (response) => {
          let persistedProject: Project = response;
          console.log("project updated...");

          if (persistedProject) {
            this.employeeService.updateEmployeesProject(persistedProject).subscribe(
              {
                error: (error: HttpErrorResponse) => {
                  console.log("Error updating employees");
                  alert(error.message);
                },
                next: (response) => {
                  console.log("employees updated");
                  location.href = "http://localhost:4200/projects";
                }
              }
            );
          }
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
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
