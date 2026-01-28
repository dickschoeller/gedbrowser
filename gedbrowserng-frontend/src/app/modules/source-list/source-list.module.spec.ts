import { TestBed } from '@angular/core/testing';

import { SourceListModule } from './source-list.module';

describe('SourceListModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [SourceListModule]
    }).inject(SourceListModule);
    expect(module).toBeTruthy();
  });
});
