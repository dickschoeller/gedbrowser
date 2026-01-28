import { TestBed } from '@angular/core/testing';

import { PersonListModule } from './person-list.module';

describe('PersonListModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [PersonListModule]
    }).inject(PersonListModule);
    expect(module).toBeTruthy();
  });
});
