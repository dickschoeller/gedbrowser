import { TestBed } from '@angular/core/testing';

import { PersonListModule } from './person-list.module';
import { PersonListPageComponent } from './person-list-page.component';
import { PersonListComponent } from './person-list.component';

describe('PersonListModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonListModule]
    }).compileComponents();
  });

  it('should be defined', () => {
    expect(PersonListModule).toBeDefined();
  });

  it('should create PersonListPageComponent', () => {
    const fixture = TestBed.createComponent(PersonListPageComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });

  it('should create PersonListComponent', () => {
    const fixture = TestBed.createComponent(PersonListComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });
});
