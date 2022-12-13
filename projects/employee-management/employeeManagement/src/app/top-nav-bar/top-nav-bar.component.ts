import { Component } from '@angular/core';

@Component({
  selector: 'app-top-nav-bar',
  templateUrl: './top-nav-bar.component.html',
  styleUrls: ['./top-nav-bar.component.css']
})
export class TopNavBarComponent {

  private baseUrl : string = "http://localhost:4200";
  public showEmployees() {
    location.href = this.baseUrl + "/employees";
  }

  public showProjects() {
    location.href = this.baseUrl + "/projects";
  }

  public showDepartments() {
    location.href = this.baseUrl + "/departments";
    console.log(location.href);
  }
}
