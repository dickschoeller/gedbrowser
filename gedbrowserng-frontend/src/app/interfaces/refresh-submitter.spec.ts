import { ApiSubmitter } from '../models';
import { vi } from 'vitest';

describe('RefreshSubmitter Interface', () => {
  it('should define refreshSubmitter method', () => {
    const impl: any = {
      refreshSubmitter: (submitter: ApiSubmitter) => {}
    };
    expect(typeof impl.refreshSubmitter).toBe('function');
  });

  it('should accept ApiSubmitter parameter', () => {
    const impl: any = {
      refreshSubmitter: vi.fn()
    };
    const submitter = new ApiSubmitter();
    submitter.string = 'SUB001';
    impl.refreshSubmitter(submitter);
    expect(impl.refreshSubmitter).toHaveBeenCalledWith(submitter);
  });

  it('should support implementations that update state', () => {
    const impl: any = {
      currentSubmitter: null,
      refreshSubmitter: function(submitter: ApiSubmitter) {
        this.currentSubmitter = submitter;
      }
    };
    const submitter = new ApiSubmitter();
    submitter.string = 'SUB001';
    impl.refreshSubmitter(submitter);
    expect(impl.currentSubmitter).toBe(submitter);
  });
});
