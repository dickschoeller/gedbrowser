import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {ApiAttribute} from '../../models';
import {SourceService} from '../../services';

import {SourceListItemComponent} from './source-list-item.component';

describe('SourceListItemComponent', () => {
  let component: SourceListItemComponent;
  let fixture: ComponentFixture<SourceListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SourceListItemComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      providers: [SourceService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListItemComponent);
    component = fixture.componentInstance;
    component.dataset = 'schoeller';
    component.source = {
      type: 'source',
      string: 'S2',
      attributes: new Array<ApiAttribute>(),
      images: new Array<ApiAttribute>(),
      title: 'Schoeller, Melissa Robinson, birth certificate'
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
