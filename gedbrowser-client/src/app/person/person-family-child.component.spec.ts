import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFamilyChildComponent } from './person-family-child.component';

describe('PersonFamilyChildComponent', () => {
  let component: PersonFamilyChildComponent;
  let fixture: ComponentFixture<PersonFamilyChildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonFamilyChildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyChildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
