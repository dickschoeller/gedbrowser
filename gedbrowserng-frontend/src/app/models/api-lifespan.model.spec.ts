import { ApiLifespan } from './api-lifespan.model';

describe('ApiLifespan Model', () => {
  it('should create an ApiLifespan instance', () => {
    const lifespan = new ApiLifespan();
    expect(lifespan).toBeTruthy();
  });

  it('should initialize birthDate as empty string', () => {
    const lifespan = new ApiLifespan();
    expect(lifespan.birthDate).toBe('');
  });

  it('should initialize deathDate as empty string', () => {
    const lifespan = new ApiLifespan();
    expect(lifespan.deathDate).toBe('');
  });

  it('should initialize birthYear as empty string', () => {
    const lifespan = new ApiLifespan();
    expect(lifespan.birthYear).toBe('');
  });

  it('should initialize deathYear as empty string', () => {
    const lifespan = new ApiLifespan();
    expect(lifespan.deathYear).toBe('');
  });

  it('should allow setting dates', () => {
    const lifespan = new ApiLifespan();
    lifespan.birthDate = '1 JAN 2000';
    lifespan.deathDate = '1 JAN 2020';
    expect(lifespan.birthDate).toBe('1 JAN 2000');
    expect(lifespan.deathDate).toBe('1 JAN 2020');
  });

  it('should allow setting years', () => {
    const lifespan = new ApiLifespan();
    lifespan.birthYear = '2000';
    lifespan.deathYear = '2020';
    expect(lifespan.birthYear).toBe('2000');
    expect(lifespan.deathYear).toBe('2020');
  });
});
