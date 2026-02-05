import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideAnimations } from '@angular/platform-browser/animations';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { AttributeListItemComponent } from './attribute-list-item.component';
import { UserService } from '../../services';
import { ApiAttribute, AttributeDialogData } from '../../models';

// Mock components
@Component({
    selector: 'app-attribute-list-item-detail-list',
    template: '',
    imports: [MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule,
      ReactiveFormsModule, FormsModule, MatDialogModule,
      MatIconModule, MatTooltipModule]
})
class MockAttributeListItemDetailListComponent {
  @Input() dataset: string;
  @Input() attributes: Array<ApiAttribute>;
}

@Component({
    selector: 'app-multimedia-edit-button',
    template: '',
    imports: [MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule,
      ReactiveFormsModule, FormsModule, MatDialogModule,
      MatIconModule, MatTooltipModule]
})
class MockMultimediaEditButtonComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index: number;
}

@Component({
    selector: 'app-source-button',
    template: '',
    imports: [MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule,
      ReactiveFormsModule, FormsModule, MatDialogModule,
      MatIconModule, MatTooltipModule]
})
class MockSourceButtonComponent {
  @Input() parent: any;
  @Input() dataset: string;
}

describe('AttributeListItemComponent', () => {
  let component: AttributeListItemComponent;
  let fixture: ComponentFixture<AttributeListItemComponent>;
  let mockUserService: any;
  let mockMatDialog: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };
    
    mockMatDialog = {
      open: vi.fn().mockReturnValue({
        afterClosed: () => of(undefined)
      })
    };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [
        MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule,
        ReactiveFormsModule, FormsModule, MatDialogModule,
        MatIconModule, MatTooltipModule,
        AttributeListItemComponent,
        MockAttributeListItemDetailListComponent,
        MockMultimediaEditButtonComponent,
        MockSourceButtonComponent
    ],
    providers: [
        provideAnimations(),
        { provide: UserService, useValue: mockUserService },
        { provide: MatDialog, useValue: mockMatDialog }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemComponent);
    component = fixture.componentInstance;
    
    // Set up required inputs
    component.attribute = new ApiAttribute();
    component.attribute.type = 'attribute';
    component.attribute.string = 'Name';
    component.attribute.tail = 'John Doe';
    component.attribute.attributes = [];
    component.attributeList = [component.attribute];
    component.index = 0;
    component.dataset = 'test';
    component.parent = {
      options: () => [],
      defaultData: () => ({}),
      save: vi.fn(),
      attributes: [component.attribute]
    } as any;
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return false for hasSignedIn when user not logged in', () => {
    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
  });

  it('should return true for hasSignedIn when user is logged in', () => {
    mockUserService.currentUser = { id: 'user123' };
    expect(component.hasSignedIn()).toBe(true);
  });

  it('should open edit dialog', () => {
    mockUserService.currentUser = { id: 'user123' };
    component.edit();
    expect(mockMatDialog.open).toHaveBeenCalled();
  });

  it('should delete attribute from list', () => {
    const attr1 = new ApiAttribute();
    attr1.type = 'attr1';
    const attr2 = new ApiAttribute();
    attr2.type = 'attr2';
    
    component.attributeList = [attr1, attr2];
    component.attribute = attr1;

    component.delete();

    expect(component.attributeList.length).toBe(1);
    expect(component.attributeList[0]).toBe(attr2);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should call parent save after modifying attribute', () => {
    const dialogData: AttributeDialogData = { attribute: 'name', value: 'Jane Doe' } as any;
    
    // Ensure helper returns a valid attribute to trigger save path
    vi.spyOn(component.attributeDialogHelper, 'populateParentAttribute').mockReturnValue(new ApiAttribute());
    
    component.modifyAttribute(dialogData);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should return parent options', () => {
    const expectedOptions = [{ label: 'Option 1', value: '1' }];
    component.parent.options = () => expectedOptions;
    expect(component.options()).toBe(expectedOptions);
  });

  it('should call parent save method', () => {
    component.save();
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should return href from attributeUtil', () => {
    // Provide a stub for attributeUtil if not initialized
    if (!component.attributeUtil) {
      (component as any).attributeUtil = { href: vi.fn() };
    }
    vi.spyOn(component.attributeUtil, 'href').mockReturnValue('http://example.com');
    expect(component.href()).toBe('http://example.com');
  });

  it('should return attributes from component attribute', () => {
    const subAttribute = new ApiAttribute();
    subAttribute.type = 'sub';
    component.attribute.attributes = [subAttribute];

    expect(component.attributes).toEqual([subAttribute]);
  });

  it('should return defaultData', () => {
    const data = component.defaultData();
    expect(data).toBeDefined();
  });

  it('should handle attribute with nested attributes', () => {
    const nestedAttr = new ApiAttribute();
    nestedAttr.type = 'nested';
    component.attribute.attributes = [nestedAttr];

    expect(component.attribute.attributes.length).toBe(1);
  });

  it('should accept attribute input', () => {
    const newAttr = new ApiAttribute();
    newAttr.type = 'new';
    component.attribute = newAttr;

    expect(component.attribute.type).toBe('new');
  });

  it('should accept attributeList input', () => {
    const newList = [new ApiAttribute()];
    component.attributeList = newList;

    expect(component.attributeList).toBe(newList);
  });

  it('should accept index input', () => {
    component.index = 5;
    expect(component.index).toBe(5);
  });

  it('should accept parent input', () => {
    const newParent = { save: () => {} };
    component.parent = newParent as any;

    expect(component.parent).toBe(newParent);
  });

  it('should accept dataset input', () => {
    component.dataset = 'new-dataset';
    expect(component.dataset).toBe('new-dataset');
  });

  it('should remove attribute at correct index', () => {
    const attr1 = new ApiAttribute();
    attr1.type = 'attr1';
    const attr2 = new ApiAttribute();
    attr2.type = 'attr2';
    const attr3 = new ApiAttribute();
    attr3.type = 'attr3';

    component.attributeList = [attr1, attr2, attr3];
    component.attribute = attr2;

    component.delete();

    expect(component.attributeList.length).toBe(2);
    expect(component.attributeList[0]).toBe(attr1);
    expect(component.attributeList[1]).toBe(attr3);
  });
});


