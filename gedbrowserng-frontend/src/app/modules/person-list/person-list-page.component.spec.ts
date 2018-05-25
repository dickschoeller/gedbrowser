import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonListPageComponent } from './person-list-page.component';

describe('PersonListPageComponent', () => {
  let component: PersonListPageComponent;
  let fixture: ComponentFixture<PersonListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
