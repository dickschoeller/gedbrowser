import { Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterService } from '../../services';

// Mock component to replace the child app-submitter-list
@Component({
  selector: 'app-submitter-list',
  template: '',
  standalone: false
})
class MockSubmitterListComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() submitters: any[];
}

describe('SubmitterListPageComponent', () => {
  let component: SubmitterListPageComponent;
  let fixture: ComponentFixture<SubmitterListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitterListPageComponent, MockSubmitterListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        SubmitterService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', submitters: [] })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
