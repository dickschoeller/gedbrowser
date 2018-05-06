import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {ApiAttribute} from '../../models';
import {PersonService} from '../../services';

import {PersonListItemComponent} from './person-list-item.component';

describe('PersonListItemComponent', () => {
  let component: PersonListItemComponent;
  let fixture: ComponentFixture<PersonListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PersonListItemComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      providers: [PersonService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListItemComponent);
    component = fixture.componentInstance;
    component.dataset = 'schoeller';
    component.person = {
      type: 'person',
      string: 'I2',
      indexName: 'Schoeller, Richard John', surname: 'Schoeller',
      attributes: new Array<ApiAttribute>(),
      fams: new Array<ApiAttribute>(),
      famc: new Array<ApiAttribute>(),
      images: new Array<ApiAttribute>(),
      refn: new Array<ApiAttribute>(),
      changed: new Array<ApiAttribute>(),
      lifespan: {birthDate: '14 DEC 1958', birthYear: '1958', deathDate: '', deathYear: ''}
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
