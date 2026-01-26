import { User } from './user.model';

describe('User Model', () => {
  it('should create a user instance', () => {
    const user = new User();
    expect(user).toBeTruthy();
  });

  it('should have email property', () => {
    const user = new User();
    user.email = 'test@example.com';
    expect(user.email).toBe('test@example.com');
  });

  it('should have username property', () => {
    const user = new User();
    user.username = 'testuser';
    expect(user.username).toBe('testuser');
  });

  it('should have firstName property', () => {
    const user = new User();
    user.firstName = 'John';
    expect(user.firstName).toBe('John');
  });

  it('should have lastName property', () => {
    const user = new User();
    user.lastName = 'Doe';
    expect(user.lastName).toBe('Doe');
  });

  it('should support setting all properties', () => {
    const user = new User();
    user.email = 'john@example.com';
    user.username = 'johndoe';
    user.firstName = 'John';
    user.lastName = 'Doe';

    expect(user.email).toBe('john@example.com');
    expect(user.username).toBe('johndoe');
    expect(user.firstName).toBe('John');
    expect(user.lastName).toBe('Doe');
  });
});
