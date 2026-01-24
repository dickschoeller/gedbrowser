import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubmitterComponent } from './submitter.component';
import { SubmitterService } from '../../services';
import { ApiSubmitter } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
  selector: 'app-main-layout',
  template: '<ng-content></ng-content>',
  standalone: false
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

// Mock attribute-list component
@Component({
  selector: 'app-attribute-list',
  template: '',
  standalone: false
})
class MockAttributeListComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() attributes: any[];
  @Input() showSources: boolean;
  @Input() showSubmitters: boolean;
}

describe('SubmitterComponent', () => {
  let component: SubmitterComponent;
  let fixture: ComponentFixture<SubmitterComponent>;

  const mockSubmitter: ApiSubmitter = {
    name: 'Test Submitter',
    string: 'S123',
    type: 'submitter',
    attributes: []
  } as ApiSubmitter;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ 
        SubmitterComponent, 
        MockMainLayoutComponent,
        MockAttributeListComponent
      ],
      imports: [ 
        MatButtonModule, 
        MatSelectModule, 
        MatFormFieldModule, 
        MatInputModule, 
        ReactiveFormsModule, 
        FormsModule, 
        HttpClientTestingModule, 
        NoopAnimationsModule,
        RouterTestingModule.withRoutes([]) 
      ],
      providers: [ 
        SubmitterService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', submitter: mockSubmitter })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
