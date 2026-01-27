import { describe, it, expect } from 'vitest';
import { NewSubmitterHelper } from './new-submitter-helper';

describe('NewSubmitterHelper', () => {
  it('buildSubmitter creates submitter with name attribute', () => {
    const data = { name: 'John Doe' };
    const submitter = NewSubmitterHelper.buildSubmitter(data);
    expect(submitter.attributes.length).toBeGreaterThan(0);
    expect(submitter.attributes[0].type).toBe('name');
    expect(submitter.attributes[0].string).toBe('John Doe');
  });

  it('buildSubmitter defaults empty name to Unknown', () => {
    const data = { name: '' };
    NewSubmitterHelper.buildSubmitter(data);
    expect(data.name).toBe('Unknown'); // buildSubmitter modifies data.name
  });

  it('buildSubmitter handles whitespace name as not empty', () => {
    const data = { name: '   ' };
    NewSubmitterHelper.buildSubmitter(data);
    expect(data.name).toBe('   '); // isEmpty doesn't treat whitespace as empty
  });

  it('initNew creates dialog data with name', () => {
    const data = NewSubmitterHelper.initNew('Test Name');
    expect(data.name).toBe('Test Name');
  });

  it('initNew creates dialog data with empty name', () => {
    const data = NewSubmitterHelper.initNew('');
    expect(data.name).toBe('');
  });
});
