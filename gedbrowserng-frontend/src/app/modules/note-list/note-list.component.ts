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
import { NoteService, NewNoteLinkService } from '../../services';
import { NewNoteHelper, UrlBuilder } from '../../utils';
import { NoteListPageComponent } from './note-list-page.component';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent extends NoteCreator implements AfterViewInit, OnChanges, OnInit {
  @Input() parent: NoteListPageComponent;
  @Input() dataset: string;
  @Input() notes: Array<ApiNote>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ['tail', 'string', 'delete'];
  datasource = new MatTableDataSource<ApiNote>(this.notes);

  constructor(
    private router: Router,
    private noteService: NoteService,
    public newNoteLinkService: NewNoteLinkService,
    public dialog: MatDialog,
  ) {
    super(newNoteLinkService, dialog);
  }

  ngAfterViewInit() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
    this.datasource.data = this.notes;
  }

  ngOnInit() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
    this.datasource.data = this.notes;
  }

  ngOnChanges() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
    this.datasource.data = this.notes;
  }

  pagesizeoptions(): number[] {
    return [15, 30, 100, 500, this.notes.length];
  }

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
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
