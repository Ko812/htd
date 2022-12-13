import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Department } from '../department';
import { DepartmentService } from '../department.service';

@Component({
  selector: 'app-department-tab',
  templateUrl: './department-tab.component.html',
  styleUrls: ['./department-tab.component.css']
})
export class DepartmentTabComponent {
  public departments: Department[] = [];

  currentShowingDepartment: Number[] = [];

  constructor(private departmentService: DepartmentService) { }

  ngOnInit(): void {
    this.getAllDepartments();
  }

  public goToDepartmentForm(): void {
    location.href = "http://localhost:4200/departments/createNewDepartment";
  }

  public editDepartment(department: Department): void {
    location.href = "http://localhost:4200/departments/updateDepartment?departmentCode="+department.departmentCode;
  }

  public deleteDepartment(id: number, name: String): void {
    console.log("deleting", id);
    if (confirm(`Confirm delete department ${name}?`)) {
      this.departmentService.deleteDepartment(id).subscribe({
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
        complete: () => {
          console.log(`department ${name} deleted`);
          this.getAllDepartments();
        }
      });
    }
  }

  public getAllDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (response: Department[]) => {
        console.log("Response received", response);
        this.departments = response;
        
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log("Employees data retrieved");
      }
    });
  }

  public showDepartmentEmployees(departmentCode: number) {
    let d = this.departments.find((dp) => dp.departmentCode == departmentCode);
    if (this.currentShowingDepartment.includes(departmentCode)) {
      let index = this.currentShowingDepartment.findIndex((num) => num === departmentCode);
      this.currentShowingDepartment.splice(index, 1);
    }
    else {
      this.currentShowingDepartment.push(departmentCode);
    }
  }
}
