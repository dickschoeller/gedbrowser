import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FooService } from './foo.service';

describe('FooService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [ HttpClientTestingModule ],
    providers: [ FooService ]
  }));

  it('should be created', () => {
    const service: FooService = TestBed.inject(FooService);
    expect(service).toBeTruthy();
  });
});
