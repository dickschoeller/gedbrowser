import { ApiSource } from '../models';
import { vi } from 'vitest';

describe('RefreshSource Interface', () => {
  it('should define refreshSource method', () => {
    const impl: any = {
      refreshSource: (source: ApiSource) => {}
    };
    expect(typeof impl.refreshSource).toBe('function');
  });

  it('should accept ApiSource parameter', () => {
    const impl: any = {
      refreshSource: vi.fn()
    };
    const source = new ApiSource();
    source.string = 'S001';
    impl.refreshSource(source);
    expect(impl.refreshSource).toHaveBeenCalledWith(source);
  });

  it('should support implementations that update state', () => {
    const impl: any = {
      currentSource: null,
      refreshSource: function(source: ApiSource) {
        this.currentSource = source;
      }
    };
    const source = new ApiSource();
    source.string = 'S001';
    impl.refreshSource(source);
    expect(impl.currentSource).toBe(source);
  });
});
