import { ApiSubmission } from './api-submission.model';

describe('ApiSubmission Model', () => {
  it('should create an ApiSubmission instance', () => {
    const submission = new ApiSubmission();
    expect(submission).toBeTruthy();
  });

  it('should inherit from ApiObject', () => {
    const submission = new ApiSubmission();
    expect(submission.string).toBeUndefined();
    submission.string = 'S001';
    expect(submission.string).toBe('S001');
  });

  it('should have tail property from ApiObject', () => {
    const submission = new ApiSubmission();
    submission.tail = 'test tail';
    expect(submission.tail).toBe('test tail');
  });

  it('should have attributes property from ApiObject', () => {
    const submission = new ApiSubmission();
    submission.attributes = [];
    expect(Array.isArray(submission.attributes)).toBe(true);
  });

  it('should support multiple instances', () => {
    const submission1 = new ApiSubmission();
    const submission2 = new ApiSubmission();
    submission1.string = 'S001';
    submission2.string = 'S002';
    expect(submission1.string).toBe('S001');
    expect(submission2.string).toBe('S002');
  });
});
