import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Department } from '../department';
import { DepartmentService } from '../department.service';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';
import { Project } from '../project';
import { ProjectService } from '../project.service';

@Component({
  selector: 'app-employee-update-form',
  templateUrl: './employee-update-form.component.html',
  styleUrls: ['./employee-update-form.component.css']
})
export class EmployeeUpdateFormComponent {
  formHeader: String;

  submitBtnLabel: String;

  employeeName: String = "";

  employeeRole: String = "";

  employeeDept: String | undefined;

  creatorName: string = "";

  creatorEmployeeId: number = 0;

  employeeNameOutline: String = "1px solid grey";

  employeeRoleOutline: String = "1px solid grey";

  employeeDeptOutline: String = "1px solid grey";

  employeeProjOutline: String = "1px solid grey";

  message: String = "";

  departmentIds: number[] = [];

  departmentMap: Map<Number, String> = new Map();

  pendingEmployee: Employee | undefined;

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService,
    private projectService: ProjectService
  ) {
    let formMode: string = location.href.split("/").slice(-1)[0];
    this.formHeader = "Update Employee";
    this.submitBtnLabel = "Update";
    let employeeId: number = Number.parseInt( location.href.split("?employeeId=")[1]);
    this.getEmployeeById(employeeId);
    this.creatorName = "Aaron Koh";
    this.creatorEmployeeId = 123;

  }
  ngOnInit(): void {
    this.getAllDepartments();
  }

  onNameKeyup(event: any): void {
    this.employeeName = event.target.value;
    let valid = this.validate(this.employeeName, "Name");
    if (valid) {
        this.employeeNameOutline = "1px solid greenyellow"
    } else {
        this.employeeNameOutline = "1px solid red";
    }
  }

  onRoleKeyup(event: any): void {
    this.employeeRole = event.target.value;
    let valid = this.validate(this.employeeRole, "Role");
    if (valid) {
        this.employeeRoleOutline = "1px solid greenyellow"
    } else {
        this.employeeRoleOutline = "1px solid red";
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

  proper = (s: String): String => {
    let c = String(s.charAt(0));
    return  c.toUpperCase() + s.substring(1);
  }

  onEscPressed = (e: Event): void => {
    this.message = "";
    this.employeeNameOutline = "1px solid grey";
    this.employeeRoleOutline = "1px solid grey";
    this.employeeDeptOutline = "1px solid grey";
    this.employeeProjOutline = "1px solid grey";
  }

  public getAllDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (response: Department[]) => {
        response.forEach((dept) => {
          this.departmentMap.set(dept.departmentCode, dept.departmentName);
        });
        this.departmentIds = response.map((dept) => dept.departmentCode);
        this.employeeDept = String(this.pendingEmployee?.department?.departmentCode);
        if (!this.employeeDept) {
          this.employeeDept = String(this.departmentIds[0]);
        }

      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log("Departments data retrieved");
      }
    });
  }

  public getEmployeeById(id: number): void {
    this.employeeService.getEmployeeById(id).subscribe({
      next: (response: Employee) => {

        this.pendingEmployee = response;
        if (this.pendingEmployee) {
          this.employeeName = this.pendingEmployee.name;
          this.employeeRole = this.pendingEmployee.role;
          this.employeeDept = String(this.pendingEmployee.department?.departmentCode);
          console.log("Employee project ... " + this.pendingEmployee.project);
          if (this.pendingEmployee.project) {
            this.getProjectById(this.pendingEmployee.project.projectCode);
          }
          else {
            this.assignedProjectTitle = "N. A.";
          }
          console.log("employee dept " + this.employeeDept);
          this.creatorName = this.pendingEmployee.createdBy;
          this.creatorEmployeeId = this.pendingEmployee.creatorId;
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

  assignedProjectTitle: String | undefined;

  public getProjectById(id: number): void {
    this.projectService.getProjectById(id).subscribe({
      next: (response: Project) => {
        let employeeProject : Project = response;
        if (employeeProject) {
          console.log("Loaded assigned project ...");
          this.assignedProjectTitle = employeeProject.projectCode + " - " + employeeProject.projectTitle;
        }
        else {
          this.assignedProjectTitle = "N. A.";
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

  public submit(addForm: NgForm): void {
    if (this.employeeDept) {
      let id = Number(this.employeeDept.split(" - ")[0]);
      addForm.value.department = new DeptModel(id, this.departmentMap.get(id)!);
      addForm.value.id = this.pendingEmployee?.id;
      addForm.value.createdBy = this.pendingEmployee?.createdBy;
      addForm.value.creatorId = this.pendingEmployee?.creatorId;
      addForm.value.creationDate = this.pendingEmployee?.creationDate;
      addForm.value.updatedBy = this.creatorName;
      addForm.value.updaterId = this.creatorEmployeeId;
      if (!addForm.value.name) {
        addForm.value.name = this.employeeName;
      }
      if (!addForm.value.role) {
        addForm.value.role = this.employeeRole;
      }
      console.log("Checking project ... ", );

      this.employeeService.updateEmployee(addForm.value).subscribe({
        next: (response) => {
          location.href = "http://localhost:4200/employees";
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
    }
  }

  public buildProjectInputTag(): void {
    let tag = document.getElementById("employee-form-table");

    let projectString: String = "";
    if (this.submitBtnLabel === "Update" && this.pendingEmployee?.project) {
      projectString = this.pendingEmployee.project.projectCode + " " + this.pendingEmployee.project.projectTitle;
    }
    let html = `<tr class="form-table-row">
                    <td style="width: 16rem;">
                        <label style="width: 80px" for="employeeProject">Project: </label>
                    </td>
                    <td style="vertical-align: center" class="input-table-cell">
                        <div style="width: 20rem; border-radius: 4px; height: 20px; padding: 0; border: none;" id="employee-proj">
                            ${projectString}
                        </select>
                    </td>
                </tr>`;
    tag?.insertAdjacentHTML('beforeend', html);

  }
}

class DeptModel {
  departmentCode: Number;
  departmentName: String;
  constructor(dc: Number, dn: String) {
    this.departmentCode = dc;
    this.departmentName = dn;
  }
}

class ProjModel {
  projectCode: Number;
  projectTitle: String;
  constructor(pc: Number, pt: String) {
    this.projectCode = pc;
    this.projectTitle = pt;
  }
}
