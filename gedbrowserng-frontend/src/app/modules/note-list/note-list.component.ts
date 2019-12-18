import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { NoteCreator } from '../../bases';
import { NewNoteDialogComponent } from '../../components';
import { NewNoteDialogData } from '../../models';
import { ApiNote, NewPersonDialogData } from '../../models';
import { NoteService } from '../../services';
import { NewNoteHelper, UrlBuilder, ListPage, ListPageHelper } from '../../utils';
import { NoteListPageComponent } from './note-list-page.component';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent extends NoteCreator implements AfterViewInit, OnChanges, OnInit, ListPage<ApiNote> {
  @Input() parent: NoteListPageComponent;
  @Input() dataset: string;
  @Input() notes: Array<ApiNote>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  displayedColumns = ['tail', 'string', 'delete'];
  datasource = new MatTableDataSource<ApiNote>(this.notes);

  constructor(
    private router: Router,
    public noteService: NoteService,
    public dialog: MatDialog,
  ) {
    super(noteService, dialog);
  }

  ngAfterViewInit() {
    ListPageHelper.init(this, this.notes);
  }

  ngOnInit() {
    ListPageHelper.init(this, this.notes);
  }

  ngOnChanges() {
    ListPageHelper.init(this, this.notes);
  }

  pagesizeoptions(): number[] {
    return ListPageHelper.pagesizeoptions(this.notes);
  }

  applyFilter(filterValue: string) {
    ListPageHelper.applyFilter(this, filterValue);
  }

  noteUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'notes');
  }

  noteAnchor(): string {
    return undefined;
  }

  refreshNote(note: ApiNote) {
    this.parent.refreshNote(note);
  }

  navigate(id: string) {
    this.router.navigate(['/' + this.dataset + '/notes/' + id]);
  }

  delete(note: ApiNote) {
    this.noteService.delete(this.dataset, note).subscribe((n: ApiNote) => {
      this.refreshNote(n);
    });
  }
}
