import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ApiAttribute } from '../../models';
import { NoteService } from '../../services';

import { NoteListItemComponent } from './note-list-item.component';

describe('NoteListItemComponent', () => {
  let component: NoteListItemComponent;
  let fixture: ComponentFixture<NoteListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NoteListItemComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      providers: [NoteService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteListItemComponent);
    component = fixture.componentInstance;
    component.dataset = 'schoeller';
    component.note = {
      type: 'note',
      string: 'N2',
      tail: 'Schoeller, Melissa Robinson, birth certificate'
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
