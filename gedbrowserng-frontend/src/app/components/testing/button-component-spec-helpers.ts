import { TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { vi } from 'vitest';
import { of } from 'rxjs';
import { UrlBuilder } from '../../utils';

/**
 * Configuration for resource button component tests (Note, Source, Submitter buttons)
 */
export interface ResourceButtonTestConfig<TComponent, TService> {
  componentClass: new (...args: any[]) => TComponent;
  serviceClass: new (...args: any[]) => TService;
  resourceName: string; // e.g., 'note', 'source', 'submitter'
}

/**
 * Setup function for resource button components
 */
export function setupResourceButtonTest<TComponent, TService>(
  config: ResourceButtonTestConfig<TComponent, TService>
) {
  const mockService: any = {
    getUrls: () => ({}),
    getAll: vi.fn().mockReturnValue(of([]))
  };

  const mockDialog: any = {
    open: vi.fn().mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    })
  };

  TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, config.componentClass],
    providers: [
        provideNoopAnimations(),
        { provide: config.serviceClass, useValue: mockService },
        { provide: MatDialog, useValue: mockDialog }
    ]
}).compileComponents();

  return { mockService, mockDialog };
}

/**
 * Create component with standard test inputs
 */
export function createResourceButtonComponent<TComponent>(
  componentClass: new (...args: any[]) => TComponent
): TComponent {
  const fixture = TestBed.createComponent(componentClass);
  const component = fixture.componentInstance as any;
  component.dataset = 'test-dataset';
  component.parent = {
    attributes: [],
    refresh: vi.fn(),
    save: vi.fn()
  };
  fixture.detectChanges();
  return component as TComponent;
}

/**
 * Generic test suite for resource button components
 */
export function describeResourceButtonComponent<TComponent, TService>(
  config: ResourceButtonTestConfig<TComponent, TService>,
  describe: (name: string, fn: () => void) => void,
  it: (name: string, fn: () => void) => void,
  expect: any
) {
  const { resourceName } = config;
  const capitalizedResource = resourceName.charAt(0).toUpperCase() + resourceName.slice(1);

  it('should create', () => {
    const component = createResourceButtonComponent(config.componentClass);
    expect(component).toBeTruthy();
  });

  it(`should return UrlBuilder with correct dataset and ${resourceName} resource`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const methodName = `${resourceName}UB`;
    const ub = component[methodName]();
    expect(ub).toBeDefined();
    expect(ub instanceof UrlBuilder).toBe(true);
  });

  it(`should return undefined for ${resourceName}Anchor`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const methodName = `${resourceName}Anchor`;
    const anchor = component[methodName]();
    expect(anchor).toBeUndefined();
  });

  it(`should call parent refresh with correct ${resourceName}`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    component.parent = {
      refresh: vi.fn(),
      attributes: [],
      save: vi.fn()
    };

    const mockResource = { string: `Test ${capitalizedResource}` } as any;
    const methodName = `refresh${capitalizedResource}`;
    component[methodName](mockResource);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it(`should open link ${resourceName} dialog`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const methodName = `openLink${capitalizedResource}Dialog`;
    component[methodName]();
    expect(component.dialog.open).toHaveBeenCalled();
  });

  it(`should open unlink ${resourceName} dialog`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const methodName = `openUnlink${capitalizedResource}Dialog`;
    component[methodName]();
    expect(component.dialog.open).toHaveBeenCalled();
  });

  it('should have dialog property from parent class', () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    expect(component.dialog).toBeDefined();
  });

  it('should have service property from parent class', () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    expect(component.service).toBeDefined();
  });

  it('should accept parent input', () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const mockParent = { attributes: [], refresh: () => {} };
    component.parent = mockParent;
    expect(component.parent).toBe(mockParent);
  });

  it('should accept dataset input', () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    component.dataset = 'another-dataset';
    expect(component.dataset).toBe('another-dataset');
  });

  it(`should open create ${resourceName} dialog`, () => {
    const component = createResourceButtonComponent(config.componentClass) as any;
    const methodName = `openCreate${capitalizedResource}Dialog`;
    component[methodName]();
    expect(component.dialog).toBeDefined();
  });
}
