import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFamilyChildListComponent } from './person-family-child-list.component';

describe('PersonFamilyChildListComponent', () => {
  let component: PersonFamilyChildListComponent;
  let fixture: ComponentFixture<PersonFamilyChildListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonFamilyChildListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyChildListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
