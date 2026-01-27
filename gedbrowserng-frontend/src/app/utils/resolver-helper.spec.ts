import { describe, it, expect, beforeEach, vi } from 'vitest';
import { ResolverHelper } from './resolver-helper';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { of } from 'rxjs';

describe('ResolverHelper', () => {
  let resolverHelper: ResolverHelper<any>;
  let mockRoute: any;
  let mockState: any;
  let mockService: any;

  beforeEach(() => {
    resolverHelper = new ResolverHelper();

    mockRoute = {
      params: {
        dataset: 'test-dataset',
        string: 'test-id'
      }
    } as ActivatedRouteSnapshot;

    mockState = {} as RouterStateSnapshot;

    mockService = {
      getOne: vi.fn().mockReturnValue(of({ id: 'test-id', name: 'Test Item' })),
      getAll: vi.fn().mockReturnValue(of([{ id: '1', name: 'Item 1' }]))
    };
  });

  describe('resolve()', () => {
    it('calls service.getOne with correct parameters', () => {
      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledWith('test-dataset', 'test-id');
    });

    it('returns observable from service', async () => {
      const result = resolverHelper.resolve(mockRoute, mockState, mockService);
      const data = await result.toPromise();
      expect(data).toEqual({ id: 'test-id', name: 'Test Item' });
    });

    it('extracts dataset from route params', () => {
      mockRoute.params.dataset = 'custom-dataset';

      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledWith('custom-dataset', 'test-id');
    });

    it('extracts string from route params', () => {
      mockRoute.params.string = 'custom-id';

      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledWith('test-dataset', 'custom-id');
    });

    it('works with generic type T', async () => {
      interface TestType {
        id: string;
        name: string;
        age?: number;
      }

      const typedResolver = new ResolverHelper<TestType>();
      mockService.getOne.mockReturnValue(of({ id: '1', name: 'John', age: 30 }));

      const result = typedResolver.resolve(mockRoute, mockState, mockService);
      const data = await result.toPromise();

      expect(data.id).toBe('1');
      expect(data.name).toBe('John');
      expect(data.age).toBe(30);
    });

    it('handles multiple resolves independently', () => {
      mockService.getOne.mockReturnValue(of({ id: 'id1' }));
      resolverHelper.resolve(mockRoute, mockState, mockService);

      mockRoute.params.string = 'id2';
      mockService.getOne.mockReturnValue(of({ id: 'id2' }));
      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledTimes(2);
    });
  });

  describe('resolveAll()', () => {
    it('calls service.getAll with dataset parameter', () => {
      resolverHelper.resolveAll(mockRoute, mockState, mockService);

      expect(mockService.getAll).toHaveBeenCalledWith('test-dataset');
    });

    it('returns observable array from service', async () => {
      const mockItems = [
        { id: '1', name: 'Item 1' },
        { id: '2', name: 'Item 2' }
      ];
      mockService.getAll.mockReturnValue(of(mockItems));

      const result = resolverHelper.resolveAll(mockRoute, mockState, mockService);
      const data = await result.toPromise();

      expect(Array.isArray(data)).toBe(true);
      expect(data.length).toBe(2);
      expect(data[0].id).toBe('1');
    });

    it('extracts dataset from route params', () => {
      mockRoute.params.dataset = 'another-dataset';

      resolverHelper.resolveAll(mockRoute, mockState, mockService);

      expect(mockService.getAll).toHaveBeenCalledWith('another-dataset');
    });

    it('ignores string param for getAll', () => {
      mockRoute.params.string = 'some-id';

      resolverHelper.resolveAll(mockRoute, mockState, mockService);

      expect(mockService.getAll).toHaveBeenCalledWith('test-dataset');
      expect(mockService.getAll).not.toHaveBeenCalledWith('some-id');
    });

    it('works with generic array type', async () => {
      interface ItemType {
        id: string;
        value: number;
      }

      const typedResolver = new ResolverHelper<ItemType>();
      const items: ItemType[] = [
        { id: '1', value: 10 },
        { id: '2', value: 20 }
      ];
      mockService.getAll.mockReturnValue(of(items));

      const result = typedResolver.resolveAll(mockRoute, mockState, mockService);
      const data = await result.toPromise();

      expect(data.length).toBe(2);
      expect(data[0].value).toBe(10);
    });

    it('handles empty array result', () => {
      mockService.getAll.mockReturnValue(of([]));  
      const result = resolverHelper.resolveAll(mockRoute, mockState, mockService);
      // Observable created successfully
      expect(result).toBeDefined();
    });

    it('returns observable that can be subscribed', () => {
      mockService.getAll.mockReturnValue(of([{ id: '1' }]));
      const result = resolverHelper.resolveAll(mockRoute, mockState, mockService);
      // Ensure observable is created and can be subscribed
      expect(result).toBeDefined();
    });
  });

  describe('edge cases', () => {
    it('handles undefined dataset param gracefully', () => {
      mockRoute.params.dataset = undefined;

      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledWith(undefined, 'test-id');
    });

    it('handles undefined string param gracefully', () => {
      mockRoute.params.string = undefined;

      resolverHelper.resolve(mockRoute, mockState, mockService);

      expect(mockService.getOne).toHaveBeenCalledWith('test-dataset', undefined);
    });

    it('handles empty dataset string', () => {
      mockRoute.params.dataset = '';

      resolverHelper.resolveAll(mockRoute, mockState, mockService);

      expect(mockService.getAll).toHaveBeenCalledWith('');
    });

    it('works with different service implementations', () => {
      const customService = {
        getOne: vi.fn().mockReturnValue(of({ custom: true })),
        getAll: vi.fn().mockReturnValue(of([]))
      };

      resolverHelper.resolve(mockRoute, mockState, customService);

      expect(customService.getOne).toHaveBeenCalled();
    });
  });
});
