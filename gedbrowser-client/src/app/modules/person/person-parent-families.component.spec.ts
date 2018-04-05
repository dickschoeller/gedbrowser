import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonParentFamiliesComponent } from './person-parent-families.component';

describe('PersonParentFamiliesComponent', () => {
  let component: PersonParentFamiliesComponent;
  let fixture: ComponentFixture<PersonParentFamiliesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonParentFamiliesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonParentFamiliesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
