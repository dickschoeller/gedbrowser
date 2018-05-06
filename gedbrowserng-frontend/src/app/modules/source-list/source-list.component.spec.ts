import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {DataViewModule} from 'primeng/dataview';

import {SourceService} from '../../services';
import {SourceListComponent} from './source-list.component';
import {SourceListItemComponent} from './source-list-item.component';

describe('SourceListComponent', () => {
  let component: SourceListComponent;
  let fixture: ComponentFixture<SourceListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SourceListComponent,
        SourceListItemComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        DataViewModule,
      ],
      providers: [
        SourceService,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
