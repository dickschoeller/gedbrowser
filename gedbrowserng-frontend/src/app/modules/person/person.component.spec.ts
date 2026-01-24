import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonComponent } from './person.component';
import { PersonService } from '../../services';
import { ApiPerson, ApiAttribute, ApiLifespan } from '../../models';

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
  @Input() toggleable: boolean;
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

// Mock person-family-list component
@Component({
  selector: 'app-person-family-list',
  template: '',
  standalone: false
})
class MockPersonFamilyListComponent {
  @Input() dataset: string;
  @Input() person: any;
  @Input() parent: any;
}

// Mock person-parent-families component
@Component({
  selector: 'app-person-parent-families',
  template: '',
  standalone: false
})
class MockPersonParentFamiliesComponent {
  @Input() dataset: string;
  @Input() person: any;
  @Input() parent: any;
}

describe('PersonComponent', () => {
  let component: PersonComponent;
  let fixture: ComponentFixture<PersonComponent>;

  const mockPerson: ApiPerson = {
    indexName: 'John Doe',
    surname: 'Doe',
    string: 'John Doe',
    attributes: [],
    lifespan: {} as ApiLifespan,
    famss: [],
    famcs: [],
    refns: [{ string: 'REFN', tail: '12345' } as ApiAttribute],
    changes: [{ string: 'CHAN', attributes: [{ string: '1 JAN 2000' } as ApiAttribute] } as ApiAttribute],
    images: []
  } as ApiPerson;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ 
        PersonComponent, 
        MockMainLayoutComponent,
        MockAttributeListComponent,
        MockMultimediaGalleryComponent,
        MockPersonFamilyListComponent,
        MockPersonParentFamiliesComponent
      ],
      imports: [ 
        RouterTestingModule.withRoutes([]),
        HttpClientTestingModule, 
        NoopAnimationsModule 
      ],
      providers: [ 
        PersonService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', person: mockPerson })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
