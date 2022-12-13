import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopNavBarComponent } from './top-nav-bar/top-nav-bar.component';
import { DepartmentTabComponent } from './department-tab/department-tab.component';
import { ProjectTabComponent } from './project-tab/project-tab.component';
import { EmployeeTabComponent } from './employee-tab/employee-tab.component';
import { EmployeeFormComponent } from './employee-form/employee-form.component';
import { DepartmentFormComponent } from './department-form/department-form.component';
import { ProjectFormComponent } from './project-form/project-form.component';
import { EmployeeService } from './employee.service';
import { DepartmentService } from './department.service';
import { ProjectService } from './project.service';
import { ProjectUpdateFormComponent } from './project-update-form/project-update-form.component';
import { DepartmentUpdateFormComponent } from './department-update-form/department-update-form.component';
import { LandingHeroComponent } from './landing-hero/landing-hero.component';
import { EmployeeUpdateFormComponent } from './employee-update-form/employee-update-form.component';

@NgModule({
  declarations: [
    AppComponent,
    TopNavBarComponent,
    DepartmentTabComponent,
    ProjectTabComponent,
    EmployeeTabComponent,
    EmployeeFormComponent,
    DepartmentFormComponent,
    ProjectFormComponent,
    ProjectUpdateFormComponent,
    DepartmentUpdateFormComponent,
    LandingHeroComponent,
    EmployeeUpdateFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
    {path: '', component: LandingHeroComponent},
    {path: 'departments', component: DepartmentTabComponent },
    {path: 'departments/createNewDepartment', component: DepartmentFormComponent },
    {path: 'departments/updateDepartment', component: DepartmentUpdateFormComponent},
    {path: 'projects', component: ProjectTabComponent },
    {path: 'projects/createProjectForm', component: ProjectFormComponent },
    {path: 'projects/updateProjectForm', component: ProjectUpdateFormComponent },
    {path: 'employees', component: EmployeeTabComponent },
    {path: 'employees/addEmployeeForm', component: EmployeeFormComponent },
    {path: 'employees/updateEmployeeForm', component: EmployeeUpdateFormComponent},
  ]),
  ],
  providers: [
    EmployeeService,
    DepartmentService,
    ProjectService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
