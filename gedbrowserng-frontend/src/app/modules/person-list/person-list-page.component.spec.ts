import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PersonListPageComponent } from './person-list-page.component';
import { PersonService } from '../../services';

// Mock component to replace the child app-person-list
@Component({
  selector: 'app-person-list',
  template: '',
  standalone: false
})
class MockPersonListComponent {
  @Input() p: any;
  @Input() dataset: string;
  @Input() persons: any[];
}

describe('PersonListPageComponent', () => {
  let component: PersonListPageComponent;
  let fixture: ComponentFixture<PersonListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonListPageComponent, MockPersonListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        PersonService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', persons: [] })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
