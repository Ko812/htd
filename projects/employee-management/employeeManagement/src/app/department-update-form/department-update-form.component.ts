import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Department } from '../department';
import { DepartmentService } from '../department.service';
import { Employee } from '../employee';

@Component({
  selector: 'app-department-update-form',
  templateUrl: './department-update-form.component.html',
  styleUrls: ['./department-update-form.component.css']
})
export class DepartmentUpdateFormComponent {
  formHeader: String;

  submitBtnLabel: String;

  name: String = "";

  code: String = "";

  departmentNameOutline: String = "1px solid grey";

  departmentCodeOutline: String = "1px solid grey";

  creatorName: string = "";

  creatorEmployeeId: number = 0;

  message: String = "";

  departments: Department[] = [];

  departmentMembers: Employee[] = [];

  pendingDepartment: Department | undefined;

  constructor(
    private departmentService: DepartmentService
  ) {
    let formMode: string = location.href.split("/").slice(-1)[0];
    this.formHeader = "Update Department";
    this.submitBtnLabel = "Update";
    let departmentCode: number = Number.parseInt( location.href.split("?departmentCode=")[1]);
    this.getDepartmentById(departmentCode);
    this.creatorName = "Aaron Koh";
    this.creatorEmployeeId = 123;
  }


  onNameKeyup(event: any): void {
    this.name = event.target.value;
    let valid = this.validate(this.name, "Name");
    if (valid) {
        this.departmentNameOutline = "1px solid greenyellow"
    } else {
        this.departmentNameOutline = "1px solid red";
    }
  }

  onCodeKeyup(event: any): void {
    this.code = event.target.value;
    let valid = this.isNumeric(this.code, "Code");
    if (valid) {
        this.departmentCodeOutline = "1px solid greenyellow"
    } else {
        this.departmentCodeOutline = "1px solid red";
    }
  }

  validate = (val : String, tagName: String): boolean => {
    if (val.length === 0) {
        this.message = `${tagName} should not be empty.`;
        return false;
    }
    else if (val.length > 50) {
        this.message = `${tagName} should not contain more than 50 characters.`;
        return false;
    }
    else {
      return true;
    }
  }

  isNumeric = (val : String, tagName: String): boolean => {
    if (val.length === 0) {
        this.message = `${tagName} should not be empty.`;
        return false;
    }
    else if (val.length != 3) {
        this.message = `${tagName} should be 3 digits in length.`;
        return false;
    }
    else {
      const numeric = "0123456789";
      for (let c of val) {
        if (!numeric.includes(c)) {
          this.message = `${tagName} should only contain digits.`;
          return false;
        }
      }

      return true;
    }
  }

  public removeEmployee(id: number, name: string): void {
    let index = this.departmentMembers.findIndex((employee) => employee.id == id);
    this.departmentMembers.splice(index, 1);
  }

  public getDepartmentById(id: number): void {
    this.departmentService.getDepartmentById(id).subscribe({
      next: (response: Department) => {

        this.pendingDepartment = response;
        if (this.pendingDepartment) {
          this.name = this.pendingDepartment.departmentName;
          this.code = String(this.pendingDepartment.departmentCode);
          // this.creatorName = this.pendingProject.createdBy;
          // this.creatorEmployeeId = this.pendingProject.creatorId;
          console.log("pending department", this.pendingDepartment);
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
      addForm.value.departmentCode = this.pendingDepartment?.departmentCode;
      if (!addForm.value.departmentName) {
        addForm.value.departmentName = this.pendingDepartment?.departmentName;
      }
      this.departmentService.updateDepartment(addForm.value).subscribe({
        next: (response) => {
          location.href = "http://localhost:4200/departments";
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
  }

  onEscPressed = (e: Event): void => {
    this.message = "";
    this.departmentNameOutline = "1px solid grey";
  }
}
