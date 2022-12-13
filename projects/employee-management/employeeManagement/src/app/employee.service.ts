import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from './employee';
import { Observable } from 'rxjs';
import { Project } from './project';

@Injectable({providedIn: 'root'})
export class EmployeeService {
  private apiServerUrl = "http://localhost:8080";

  private pendingEmployee: Employee | undefined;

  constructor(private http: HttpClient) { }

  public getAllEmployees(): Observable<Employee[]> {
    var emp = this.http.get<Employee[]>(`${this.apiServerUrl}/employee/findAll`);
    return emp;
  }

  public getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<any>(`${this.apiServerUrl}/employee/find/${id}`);
  }

  public addEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<any>(`${this.apiServerUrl}/employee/add`, employee);
  }

  public updateEmployee(employee: Employee): Observable<Employee> {
    return this.http.put<any>(`${this.apiServerUrl}/employee/update`, employee);
  }

  public updateEmployeesProject(project: Project): Observable<void> {
    return this.http.put<any>(`${this.apiServerUrl}/employee/updateEmployeesProject`, project);
  }

  public deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/employee/delete/${id}`);
  }

  public setPendingEmployee(emp: Employee):void {
    this.pendingEmployee = emp;
  }

  public getPendingEmployee(): Employee {
    if (this.pendingEmployee) {
      return this.pendingEmployee;
    }
    else {
      throw "pending employee is undefined";
    }

  }

}

class Employees {
  employees: Employee[];
  constructor(employees: Employee[]) {
    this.employees = employees;
  }
}
