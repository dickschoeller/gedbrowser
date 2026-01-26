import { ApiPerson } from '../models';

describe('HasPerson Interface', () => {
  it('should define person property', () => {
    const impl: any = {
      person: new ApiPerson()
    };
    expect(impl.person).toBeDefined();
  });

  it('should allow setting person property', () => {
    const impl: any = {
      person: null
    };
    const person = new ApiPerson();
    person.string = 'P001';
    impl.person = person;
    expect(impl.person).toBe(person);
    expect(impl.person.string).toBe('P001');
  });

  it('should support person object with properties', () => {
    const impl: any = {
      person: new ApiPerson()
    };
    impl.person.string = 'P001';
    impl.person.tail = 'John Doe';
    expect(impl.person.string).toBe('P001');
    expect(impl.person.tail).toBe('John Doe');
  });
});
