import { vi } from 'vitest';

describe('RefreshPerson Interface', () => {
  it('should define refreshPerson method', () => {
    const impl: any = {
      refreshPerson: () => {}
    };
    expect(typeof impl.refreshPerson).toBe('function');
  });

  it('should be callable without parameters', () => {
    const impl: any = {
      refreshPerson: vi.fn()
    };
    impl.refreshPerson();
    expect(impl.refreshPerson).toHaveBeenCalled();
  });

  it('should support implementations that update state', () => {
    const impl: any = {
      isRefreshed: false,
      refreshPerson: function() {
        this.isRefreshed = true;
      }
    };
    impl.refreshPerson();
    expect(impl.isRefreshed).toBe(true);
  });

  it('should be callable multiple times', () => {
    const impl: any = {
      refreshPerson: vi.fn()
    };
    impl.refreshPerson();
    impl.refreshPerson();
    impl.refreshPerson();
    expect(impl.refreshPerson).toHaveBeenCalledTimes(3);
  });
});
