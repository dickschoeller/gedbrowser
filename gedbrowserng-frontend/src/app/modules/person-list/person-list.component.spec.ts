import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import {PersonService} from '../../services';
import {PersonListComponent} from './person-list.component';
import {PersonListResolverService} from './person-list-resolver.service';

// Mock component to replace the child app-main-layout
@Component({
  selector: 'app-main-layout',
  template: '<ng-content></ng-content>',
  standalone: false
})
class MockMainLayoutComponent {
  @Input() dataset: string;
}

describe('PersonListComponent', () => {
  let component: PersonListComponent;
  let fixture: ComponentFixture<PersonListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        PersonListComponent,
        MockMainLayoutComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
      ],
      providers: [
        PersonService,
        PersonListResolverService,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.persons = [];
    component.parent = {} as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
