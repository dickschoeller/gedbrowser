import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { DataViewModule } from 'primeng/dataview';

import { NoteService } from '../../services';
import { NoteListComponent } from './note-list.component';
import { NoteListItemComponent } from './note-list-item.component';

describe('NoteListComponent', () => {
  let component: NoteListComponent;
  let fixture: ComponentFixture<NoteListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        NoteListComponent,
        NoteListItemComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        DataViewModule,
      ],
      providers: [
        NoteService,
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
