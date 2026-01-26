import { NewPersonDialogData } from './new-person-dialog-data';

describe('NewPersonDialogData Model', () => {
  it('should create an instance', () => {
    const data = new NewPersonDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting sex', () => {
    const data = new NewPersonDialogData();
    data.sex = 'M';
    expect(data.sex).toBe('M');
  });

  it('should allow setting name', () => {
    const data = new NewPersonDialogData();
    data.name = 'John Doe';
    expect(data.name).toBe('John Doe');
  });

  it('should allow setting birthDate', () => {
    const data = new NewPersonDialogData();
    data.birthDate = '1 JAN 1970';
    expect(data.birthDate).toBe('1 JAN 1970');
  });

  it('should allow setting birthPlace', () => {
    const data = new NewPersonDialogData();
    data.birthPlace = 'Boston, MA';
    expect(data.birthPlace).toBe('Boston, MA');
  });

  it('should allow setting deathDate', () => {
    const data = new NewPersonDialogData();
    data.deathDate = '1 JAN 2020';
    expect(data.deathDate).toBe('1 JAN 2020');
  });

  it('should allow setting deathPlace', () => {
    const data = new NewPersonDialogData();
    data.deathPlace = 'Los Angeles, CA';
    expect(data.deathPlace).toBe('Los Angeles, CA');
  });

  it('should allow setting all properties at once', () => {
    const data = new NewPersonDialogData();
    data.sex = 'F';
    data.name = 'Jane Smith';
    data.birthDate = '1 JAN 1975';
    data.birthPlace = 'New York, NY';
    data.deathDate = '1 JAN 2025';
    data.deathPlace = 'Boston, MA';

    expect(data.sex).toBe('F');
    expect(data.name).toBe('Jane Smith');
    expect(data.birthDate).toBe('1 JAN 1975');
    expect(data.birthPlace).toBe('New York, NY');
    expect(data.deathDate).toBe('1 JAN 2025');
    expect(data.deathPlace).toBe('Boston, MA');
  });
});
