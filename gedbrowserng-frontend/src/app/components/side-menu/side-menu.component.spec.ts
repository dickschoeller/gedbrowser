import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { SideMenuComponent } from './side-menu.component';
import { DatasetsService, SaveService, UploadService, UserService } from '../../services';

describe('SideMenuComponent', () => {
  let component: SideMenuComponent;
  let fixture: ComponentFixture<SideMenuComponent>;
  let mockDatasetsService: any;
  let mockSaveService: any;
  let mockUploadService: any;
  let mockUserService: any;

  beforeEach(() => {
    mockDatasetsService = { 
      get: () => of(['test']),
      getDbs: () => [] 
    };
    mockSaveService = { getTextFile: () => ({}) };
    mockUploadService = { upload: () => ({}) };
    mockUserService = { currentUser: null };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ SideMenuComponent ],
      imports: [ ReactiveFormsModule, FormsModule, HttpClientTestingModule, NoopAnimationsModule, MatListModule, MatMenuModule, MatIconModule, RouterTestingModule ],
      providers: [
        { provide: DatasetsService, useValue: mockDatasetsService },
        { provide: SaveService, useValue: mockSaveService },
        { provide: UploadService, useValue: mockUploadService },
        { provide: UserService, useValue: mockUserService }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
