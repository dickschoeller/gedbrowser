import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog } from '@angular/material/dialog';

import { AttributeListItemComponent } from './attribute-list-item.component';
import { UserService } from '../../services';
import { ApiAttribute } from '../../models';

// Mock components
@Component({
  standalone: false,
  selector: 'app-attribute-list-item-detail-list',
  template: ''
})
class MockAttributeListItemDetailListComponent {
  @Input() dataset: string;
  @Input() attributes: Array<ApiAttribute>;
}

@Component({
  standalone: false,
  selector: 'app-multimedia-edit-button',
  template: ''
})
class MockMultimediaEditButtonComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index: number;
}

@Component({
  standalone: false,
  selector: 'app-source-button',
  template: ''
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
      open: () => ({
        afterClosed: () => ({ subscribe: () => {} })
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ 
        AttributeListItemComponent,
        MockAttributeListItemDetailListComponent,
        MockMultimediaEditButtonComponent,
        MockSourceButtonComponent
      ],
      imports: [ MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, FormsModule, NoopAnimationsModule ],
      providers: [
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
      save: () => {},
      attributes: [component.attribute]
    } as any;
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


