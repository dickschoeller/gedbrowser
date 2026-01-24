import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import {SourceService} from '../../services';
import {SourceListComponent} from './source-list.component';

// Mock component to replace the child app-main-layout
@Component({
  selector: 'app-main-layout',
  template: '<ng-content></ng-content>',
  standalone: false
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

describe('SourceListComponent', () => {
  let component: SourceListComponent;
  let fixture: ComponentFixture<SourceListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        SourceListComponent,
        MockMainLayoutComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
      ],
      providers: [
        SourceService,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.sources = [];
    component.parent = {} as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
