import { TestBed } from '@angular/core/testing';

import { SourceModule } from './source.module';

describe('SourceModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [SourceModule]
    }).inject(SourceModule);
    expect(module).toBeTruthy();
  });
});
