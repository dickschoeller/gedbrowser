import { TestBed } from '@angular/core/testing';

import { SourceListModule } from './source-list.module';
import { SourceListPageComponent } from './source-list-page.component';
import { SourceListComponent } from './source-list.component';

describe('SourceListModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SourceListModule]
    }).compileComponents();
  });

  it('should be defined', () => {
    expect(SourceListModule).toBeDefined();
  });

  it('should create SourceListPageComponent', () => {
    const fixture = TestBed.createComponent(SourceListPageComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });

  it('should create SourceListComponent', () => {
    const fixture = TestBed.createComponent(SourceListComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });
});
