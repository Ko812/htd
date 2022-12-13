import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { EmployeeService } from '../employee.service';

@Component({
  selector: 'app-employee-tab',
  templateUrl: './employee-tab.component.html',
  styleUrls: ['./employee-tab.component.css']
})
export class EmployeeTabComponent implements OnInit{
  public employees: Employee[] = [];

  constructor(private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.getAllEmployees();
  }

  public goToEmployeeForm(): void {
    location.href = "http://localhost:4200/employees/addEmployeeForm";
  }

  public editEmployee(employee: Employee): void {
    location.href = "http://localhost:4200/employees/updateEmployeeForm?employeeId="+employee.id;
  }

  public deleteEmployee(id: number, name: String): void {
    console.log("deleting", id);
    if (confirm(`Confirm delete employee ${name}?`)) {
      this.employeeService.deleteEmployee(id).subscribe({
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        },
        complete: () => {
          console.log(`employee ${name} deleted`);
          this.getAllEmployees();
        }
      });
    }
  }

  public getAllEmployees(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (response: Employee[]) => {
        console.log("Response received", response);
        this.employees = response;
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log("Employees data retrieved");
      }
    });
  }
}
