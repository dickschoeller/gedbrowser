import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import {SubmitterService} from '../../services';
import {SubmitterListComponent} from './submitter-list.component';

// Mock component to replace the child app-main-layout
@Component({
  selector: 'app-main-layout',
  template: '<ng-content></ng-content>',
  standalone: false
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

describe('SubmitterListComponent', () => {
  let component: SubmitterListComponent;
  let fixture: ComponentFixture<SubmitterListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        SubmitterListComponent,
        MockMainLayoutComponent
      ],
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        HttpClientTestingModule,
      ],
      providers: [
        SubmitterService,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.submitters = [];
    component.parent = {} as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
