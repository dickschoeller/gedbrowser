import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubmitterComponent } from './submitter.component';
import { SubmitterService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';
import { ApiSubmitter } from '../../models';

// Mock component to replace the child app-main-layout
@Component({
    selector: 'app-main-layout',
    template: '<ng-content></ng-content>',
    imports: [MatButtonModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule]
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

// Mock attribute-list component
@Component({
    selector: 'app-attribute-list',
    template: '',
    imports: [MatButtonModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule]
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
    imports: [
        MatButtonModule,
        MatSelectModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        FormsModule,
        ,
        SubmitterComponent,
        MockMainLayoutComponent,
        MockAttributeListComponent
    ],
    providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        provideNoopAnimations()
    ],
    providers: [
      SubmitterService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
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
