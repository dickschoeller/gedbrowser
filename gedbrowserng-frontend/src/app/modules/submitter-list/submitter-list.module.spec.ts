import { TestBed } from '@angular/core/testing';

import { SubmitterListModule } from './submitter-list.module';

describe('SubmitterListModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [SubmitterListModule]
    }).inject(SubmitterListModule);
    expect(module).toBeTruthy();
  });
});
