import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFamilyComponent } from './person-family.component';

describe('PersonFamilyComponent', () => {
  let component: PersonFamilyComponent;
  let fixture: ComponentFixture<PersonFamilyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonFamilyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
