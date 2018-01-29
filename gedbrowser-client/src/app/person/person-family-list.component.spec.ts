import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonFamilyListComponent } from './person-family-list.component';

describe('PersonFamilyListComponent', () => {
  let component: PersonFamilyListComponent;
  let fixture: ComponentFixture<PersonFamilyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonFamilyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonFamilyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
