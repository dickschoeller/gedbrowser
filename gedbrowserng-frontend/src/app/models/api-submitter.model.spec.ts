import { ApiSubmitter } from './api-submitter.model';

describe('ApiSubmitter Model', () => {
  it('should create an ApiSubmitter instance', () => {
    const submitter = new ApiSubmitter();
    expect(submitter).toBeTruthy();
  });

  it('should initialize name as empty string', () => {
    const submitter = new ApiSubmitter();
    expect(submitter.name).toBe('');
  });

  it('should allow setting name', () => {
    const submitter = new ApiSubmitter();
    submitter.name = 'John Smith';
    expect(submitter.name).toBe('John Smith');
  });

  it('should inherit from ApiObject', () => {
    const submitter = new ApiSubmitter();
    submitter.string = 'SUB001';
    expect(submitter.string).toBe('SUB001');
  });

  it('should support multiple submitter instances', () => {
    const submitter1 = new ApiSubmitter();
    const submitter2 = new ApiSubmitter();
    submitter1.name = 'John Smith';
    submitter2.name = 'Jane Doe';
    expect(submitter1.name).toBe('John Smith');
    expect(submitter2.name).toBe('Jane Doe');
  });
});
