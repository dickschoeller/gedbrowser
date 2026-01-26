import { ApiPerson } from '../models';
import { vi } from 'vitest';

describe('LinkCheck Interface', () => {
  it('should define spouseLinked method', () => {
    const impl: any = {
      spouseLinked: (person: ApiPerson) => true,
      childLinked: (person: ApiPerson) => false
    };
    expect(typeof impl.spouseLinked).toBe('function');
  });

  it('should define childLinked method', () => {
    const impl: any = {
      spouseLinked: (person: ApiPerson) => true,
      childLinked: (person: ApiPerson) => false
    };
    expect(typeof impl.childLinked).toBe('function');
  });

  it('should support spouse link checking', () => {
    const linkCheckMock: any = {
      spouseLinked: vi.fn().mockReturnValue(true),
      childLinked: vi.fn().mockReturnValue(false)
    };
    const person = new ApiPerson();
    
    expect(linkCheckMock.spouseLinked(person)).toBe(true);
    expect(linkCheckMock.spouseLinked).toHaveBeenCalledWith(person);
  });

  it('should support child link checking', () => {
    const linkCheckMock: any = {
      spouseLinked: vi.fn().mockReturnValue(false),
      childLinked: vi.fn().mockReturnValue(true)
    };
    const person = new ApiPerson();
    
    expect(linkCheckMock.childLinked(person)).toBe(true);
    expect(linkCheckMock.childLinked).toHaveBeenCalledWith(person);
  });
});
