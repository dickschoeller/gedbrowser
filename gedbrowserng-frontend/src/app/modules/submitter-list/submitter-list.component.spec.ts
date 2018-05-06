import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {DataViewModule} from 'primeng/dataview';

import {SubmitterService} from '../../services';
import {SubmitterListComponent} from './submitter-list.component';
import {SubmitterListItemComponent} from './submitter-list-item.component';

describe('SubmitterListComponent', () => {
  let component: SubmitterListComponent;
  let fixture: ComponentFixture<SubmitterListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        SubmitterListComponent,
        SubmitterListItemComponent,
      ],
      imports: [
        RouterTestingModule,
        DataViewModule,
        HttpClientTestingModule,
      ],
      providers: [
        SubmitterService,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
