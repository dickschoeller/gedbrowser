import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonParentFamilyComponent } from './person-parent-family.component';

describe('PersonParentFamilyComponent', () => {
  let component: PersonParentFamilyComponent;
  let fixture: ComponentFixture<PersonParentFamilyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonParentFamilyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonParentFamilyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
