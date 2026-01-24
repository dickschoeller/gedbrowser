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

import { SourceComponent } from './source.component';
import { SourceService } from '../../services';
import { ApiSource } from '../../models';

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

// Mock multimedia-gallery component
@Component({
  selector: 'app-multimedia-gallery',
  template: '',
  standalone: false
})
class MockMultimediaGalleryComponent {
  @Input() dataset: string;
  @Input() parent: any;
  @Input() multimedia: any[];
}

describe('SourceComponent', () => {
  let component: SourceComponent;
  let fixture: ComponentFixture<SourceComponent>;

  const mockSource: ApiSource = {
    title: 'Test Source',
    string: 'S456',
    type: 'source',
    attributes: [],
    images: []
  } as ApiSource;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ 
        SourceComponent, 
        MockMainLayoutComponent,
        MockAttributeListComponent,
        MockMultimediaGalleryComponent
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
        SourceService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', source: mockSource })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
