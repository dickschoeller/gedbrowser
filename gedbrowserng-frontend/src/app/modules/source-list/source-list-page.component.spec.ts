import { NO_ERRORS_SCHEMA, Component, Input } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SourceListPageComponent } from './source-list-page.component';
import { SourceService } from '../../services';

// Mock component to replace the child app-source-list
@Component({
  selector: 'app-source-list',
  template: '',
  standalone: false
})
class MockSourceListComponent {
  @Input() parent: any;
  @Input() dataset: string;
  @Input() sources: any[];
}

describe('SourceListPageComponent', () => {
  let component: SourceListPageComponent;
  let fixture: ComponentFixture<SourceListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ SourceListPageComponent, MockSourceListComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        SourceService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({ dataset: 'testDataset' }),
            data: of({ dataset: 'testDataset', sources: [] })
          }
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
