
import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../employee.service';
import { Department } from '../department';
import { Project } from '../project';
import { DepartmentService } from '../department.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ProjectService } from '../project.service';
import { Employee } from '../employee';
import { FormControl, NgForm } from '@angular/forms';

@Component({
  selector: 'app-employee-form',
  templateUrl: './employee-form.component.html',
  styleUrls: ['./employee-form.component.css']
})
export class EmployeeFormComponent implements OnInit{

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
  ) {
    let formMode: string = location.href.split("/").slice(-1)[0];
    this.formHeader = "Add Employee";
    this.submitBtnLabel = "Add";
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

  public submit(addForm: NgForm): void {
    if (this.employeeDept) {
      let id = Number(this.employeeDept.split(" ")[0]);
      addForm.value.department = new DeptModel(id, this.departmentMap.get(id)!);
      addForm.value.createdBy = this.creatorName;
      addForm.value.creatorId = this.creatorEmployeeId;
      console.log("Logging add employee " + addForm.value);
      this.employeeService.addEmployee(addForm.value).subscribe({
          next: (response) => {
            location.href = "http://localhost:4200/employees";
          },
          error: (error: HttpErrorResponse) => {
            console.log(error.message, error.headers);
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
