import { ApiTail } from './api-tail.model';

describe('ApiTail Model', () => {
  it('should create an ApiTail instance', () => {
    const tail = new ApiTail();
    expect(tail).toBeTruthy();
  });

  it('should initialize tail as empty string', () => {
    const tail = new ApiTail();
    expect(tail.tail).toBe('');
  });

  it('should allow setting tail', () => {
    const tail = new ApiTail();
    tail.tail = 'test tail';
    expect(tail.tail).toBe('test tail');
  });

  it('should inherit from ApiObject', () => {
    const tail = new ApiTail();
    tail.string = 'T001';
    tail.type = 'TEST';
    expect(tail.string).toBe('T001');
    expect(tail.type).toBe('TEST');
  });

  it('should have attributes array', () => {
    const tail = new ApiTail();
    expect(Array.isArray(tail.attributes)).toBe(true);
  });
});
