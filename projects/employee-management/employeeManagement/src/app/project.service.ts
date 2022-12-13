import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from './project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private apiServerUrl = "http://localhost:8080";
  constructor(private http: HttpClient) { }

  public getAllProjects(): Observable<Project[]> {
    var dpm = this.http.get<Project[]>(`${this.apiServerUrl}/project/findAll`);
    return dpm;
  }

  public getProjectById(id: number): Observable<Project> {
    return this.http.get<any>(`${this.apiServerUrl}/project/find/${id}`);
  }

  public addProject(dpm: Project): Observable<Project> {
    return this.http.post<any>(`${this.apiServerUrl}/project/add`, dpm);
  }

  public updateProject(dpm: Project): Observable<Project> {
    return this.http.put<any>(`${this.apiServerUrl}/project/update`, dpm);
  }

  public deleteProject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/project/delete/${id}`);
  }
}
