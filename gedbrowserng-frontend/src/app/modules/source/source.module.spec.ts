import { TestBed } from '@angular/core/testing';

import { SourceModule } from './source.module';
import { SourceComponent } from './source.component';

describe('SourceModule', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SourceModule]
    }).compileComponents();
  });

  it('should be defined', () => {
    expect(SourceModule).toBeDefined();
  });

  it('should create SourceComponent', () => {
    const fixture = TestBed.createComponent(SourceComponent);
    const component = fixture.componentInstance;

    expect(component).toBeTruthy();
  });
});
