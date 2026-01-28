import { TestBed } from '@angular/core/testing';

import { SubmitterListModule } from './submitter-list.module';
import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterListComponent } from './submitter-list.component';

describe('SubmitterListModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubmitterListModule]
    }).compileComponents();
  });

  it('should be defined', () => {
    expect(SubmitterListModule).toBeDefined();
  });

  it('should create SubmitterListPageComponent', () => {
    const fixture = TestBed.createComponent(SubmitterListPageComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });

  it('should create SubmitterListComponent', () => {
    const fixture = TestBed.createComponent(SubmitterListComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });
});
