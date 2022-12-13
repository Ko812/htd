import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartmentUpdateFormComponent } from './department-update-form.component';

describe('DepartmentUpdateFormComponent', () => {
  let component: DepartmentUpdateFormComponent;
  let fixture: ComponentFixture<DepartmentUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DepartmentUpdateFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DepartmentUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
