import { TestBed } from '@angular/core/testing';

import { PersonModule } from './person.module';

describe('PersonModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [PersonModule]
    }).inject(PersonModule);
    expect(module).toBeTruthy();
  });
});
