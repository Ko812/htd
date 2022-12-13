import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Department } from './department';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private apiServerUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  public getAllDepartments(): Observable<Department[]> {
    var dpm = this.http.get<Department[]>(`${this.apiServerUrl}/department/findAll`);
    return dpm;
  }

  public getDepartmentById(id: number): Observable<Department> {
    return this.http.get<any>(`${this.apiServerUrl}/department/findById/${id}`);
  }

  public getDepartmentByName(name: string): Observable<Department> {
    return this.http.get<any>(`${this.apiServerUrl}/department/findByName/${name}`);
  }

  public addDepartment(dpm: Department): Observable<ArrayBuffer> {
    return this.http.post<any>(`${this.apiServerUrl}/department/add`, dpm);
  }

  public updateDepartment(dpm: Department): Observable<Department> {
    return this.http.put<any>(`${this.apiServerUrl}/department/update`, dpm);
  }

  public deleteDepartment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/department/delete/${id}`);
  }

}
