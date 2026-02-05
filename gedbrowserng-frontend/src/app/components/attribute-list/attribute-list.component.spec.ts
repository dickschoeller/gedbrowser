import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { AttributeListComponent } from './attribute-list.component';
import { UserService, AuthApiService, ConfigService } from '../../services';
import { ApiAttribute, AttributeDialogData } from '../../models';

describe('AttributeListComponent', () => {
  let component: AttributeListComponent;
  let fixture: ComponentFixture<AttributeListComponent>;
  let mockUserService: any;
  let mockDialog: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };

    mockDialog = {
      open: vi.fn().mockReturnValue({
        afterClosed: () => of(undefined)
      })
    };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule,
        ReactiveFormsModule, FormsModule,
        DragDropModule, MatDialogModule, MatCardModule, MatToolbarModule, MatIconModule,
        MatTooltipModule,
        AttributeListComponent
    ],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideNoopAnimations()
    ],
    providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: MatDialog, useValue: mockDialog },
        AuthApiService,
        ConfigService
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListComponent);
    component = fixture.componentInstance;
    component.attributes = [];
    component.parent = {
      attributes: [],
      deleteAttribute: () => {},
      addAttribute: () => {},
      options: () => [],
      defaultData: () => ({}),
      save: vi.fn()
    } as any;
    component.dataset = 'test-dataset';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize index on ngOnInit', () => {
    component.attributes = [];
    component.ngOnInit();
    expect(component.index).toBeDefined();
  });

  it('should update index on ngOnChanges', () => {
    component.attributes = [{ type: 'test' } as ApiAttribute];
    component.ngOnChanges();
    expect(component.index).toBeDefined();
  });

  it('should return false for hasSignedIn when user not logged in', () => {
    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
  });

  it('should return true for hasSignedIn when user is logged in', () => {
    mockUserService.currentUser = { id: 'user123' };
    expect(component.hasSignedIn()).toBe(true);
  });

  it('should open create attribute dialog', () => {
    mockUserService.currentUser = { id: 'user123' };
    component.openCreateAttributeDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should return parent defaultData', () => {
    const expectedData = { attr: 'value' };
    component.parent.defaultData = () => expectedData;
    expect(component.defaultData()).toBe(expectedData);
  });

  it('should return parent options', () => {
    const expectedOptions = [{ label: 'Option 1', value: '1' }];
    component.parent.options = () => expectedOptions;
    expect(component.options()).toBe(expectedOptions);
  });

  it('should create new attribute when dialog returns data', () => {
    const dialogData: AttributeDialogData = {
      attribute: 'name',
      value: 'John Doe'
    } as any;

    vi.spyOn(component.attributeDialogHelper, 'populateNewAttribute').mockReturnValue({
      type: 'name',
      value: 'John Doe'
    } as ApiAttribute);

    component.createAttribute(dialogData);

    expect(component.attributes.length).toBe(1);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should not create attribute when dialog returns null', () => {
    component.createAttribute(null);
    expect(component.attributes.length).toBe(0);
    expect(component.parent.save).not.toHaveBeenCalled();
  });

  it('should add new attribute at beginning of array', () => {
    const existingAttribute = { type: 'existing' } as ApiAttribute;
    component.attributes = [existingAttribute];

    const dialogData: AttributeDialogData = { attribute: 'new' } as any;

    vi.spyOn(component.attributeDialogHelper, 'populateNewAttribute').mockReturnValue({
      type: 'new'
    } as ApiAttribute);

    component.createAttribute(dialogData);

    expect(component.attributes[0].type).toBe('new');
    expect(component.attributes[1].type).toBe('existing');
  });

  it('should move item in array on drop', () => {
    component.attributes = [
      { type: 'attr1' } as ApiAttribute,
      { type: 'attr2' } as ApiAttribute,
      { type: 'attr3' } as ApiAttribute
    ];

    const event = {
      previousIndex: 0,
      currentIndex: 2
    } as any;

    component.drop(event);

    expect(component.attributes[0].type).toBe('attr2');
    expect(component.attributes[1].type).toBe('attr3');
    expect(component.attributes[2].type).toBe('attr1');
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should call parent save on drop', () => {
    component.attributes = [{ type: 'attr' } as ApiAttribute];

    const event = {
      previousIndex: 0,
      currentIndex: 0
    } as any;

    component.drop(event);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should call parent save method', () => {
    component.save();
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should accept toggleable input', () => {
    component.toggleable = true;
    expect(component.toggleable).toBe(true);
  });

  it('should accept styleClass input', () => {
    component.styleClass = 'custom-class';
    expect(component.styleClass).toBe('custom-class');
  });

  it('should default showAdd to true', () => {
    expect(component.showAdd).toBe(true);
  });

  it('should default showNotes to true', () => {
    expect(component.showNotes).toBe(true);
  });

  it('should default showSources to true', () => {
    expect(component.showSources).toBe(true);
  });

  it('should default showSubmitters to true', () => {
    expect(component.showSubmitters).toBe(true);
  });

  it('should allow toggling showAdd', () => {
    component.showAdd = false;
    expect(component.showAdd).toBe(false);
  });

  it('should handle empty attributes array', () => {
    component.attributes = [];
    component.ngOnInit();
    expect(component.attributes.length).toBe(0);
  });

  it('should handle multiple attributes on drop', () => {
    component.attributes = [
      { type: 'attr1' } as ApiAttribute,
      { type: 'attr2' } as ApiAttribute
    ];

    const event = {
      previousIndex: 1,
      currentIndex: 0
    } as any;

    component.drop(event);

    expect(component.attributes[0].type).toBe('attr2');
    expect(component.attributes[1].type).toBe('attr1');
  });
});
