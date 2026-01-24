import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
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
  standalone: false,
  selector: 'app-note-list',
  template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon matListIcon>comment</mat-icon> Notes</div></mat-card-title>
    <mat-card-content>
      <div class="ui-g">
        <div class="ui-g-12">
          <mat-card class="inner-card">
            <mat-card-header>
              <mat-toolbar>
                <mat-form-field>
                  <input matInput (keyup)="applyFilter($event.target.value)" placeholder="Filter">
                </mat-form-field>
                <span class="example-fill-remaining-space"></span>
                <span>
                  <button (click)="openCreateNoteDialog()" mat-icon-button color="primary"
                      matTooltip="Add note"><mat-icon>add_comment</mat-icon></button>
                </span>
              </mat-toolbar>
            </mat-card-header>
            <mat-card-content>
                <mat-table #table [dataSource]="datasource" matSort>
                  <ng-container matColumnDef="tail">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Note </mat-header-cell>
                    <mat-cell *matCellDef="let note" (click)="navigate(note.string)" style="cursor: pointer;">{{ formatNoteTail(note.tail) }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="string">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> ID </mat-header-cell>
                    <mat-cell *matCellDef="let note" (click)="navigate(note.string)" style="cursor: pointer;">[{{ note.string }}]</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="delete">
                    <mat-header-cell *matHeaderCellDef mat-sort-header></mat-header-cell>
                    <mat-cell *matCellDef="let note">
                      <span class="hidden">
                        <button mat-icon-button matTooltip="Delete note" color="warn" (click)="delete(note)">
                        <mat-icon matListIcon>delete</mat-icon></button>
                      </span>
                    </mat-cell>
                  </ng-container>

                  <mat-header-row *matHeaderRowDef="displayedColumns"></mat-header-row>
                  <mat-row class="parent" *matRowDef="let row; columns: displayedColumns;"></mat-row>
                </mat-table>
                <mat-paginator #paginator [pageSize]="15" [pageSizeOptions]="pagesizeoptions()"
                    [showFirstLastButtons]="true">
                </mat-paginator>
            </mat-card-content>
          </mat-card>
        </div>
      </div>
    </mat-card-content>
  </mat-card>
</app-main-layout>`,
    styles: []
})
export class NoteListComponent extends NoteCreator implements AfterViewInit, OnChanges, OnInit, ListPage<ApiNote> {
  @Input() parent: NoteListPageComponent;
  @Input() dataset: string;
  @Input() notes: Array<ApiNote>;

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  displayedColumns = ['tail', 'string', 'delete'];
  // Initialize datasource early so lifecycle hooks can safely configure it
  datasource: MatTableDataSource<ApiNote> = new MatTableDataSource<ApiNote>([]);

  constructor(
    @Inject(Router) private router: Router,
    @Inject(NoteService) public noteService: NoteService,
    @Inject(MatDialog) public dialog: MatDialog,
  ) {
    super(noteService, dialog);
  }

  ngAfterViewInit() {
    ListPageHelper.init(this, this.notes);
  }

  ngOnInit() {
    this.datasource = new MatTableDataSource<ApiNote>(this.notes);
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

  formatNoteTail(tail: string): string {
    return tail.substr(0, 80).replace(/\n/g, ' ');
  }
}
