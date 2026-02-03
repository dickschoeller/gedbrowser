import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { SubmitterCreator } from '../../bases/submitter-creator';
import { NewSubmitterDialogComponent } from '../../components/';
import { NewSubmitterDialogData } from '../../models';
import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { NewSubmitterHelper, UrlBuilder, ListPage, ListPageHelper } from '../../utils';
import { SubmitterListPageComponent } from './submitter-list-page.component';

@Component({
  standalone: false,
  selector: 'app-submitter-list',
  template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon matListIcon>contacts</mat-icon> Submitters</div></mat-card-title>
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
                  <button (click)="openCreateSubmitterDialog()" mat-icon-button color="primary"
                      matTooltip="Add submitter"><mat-icon>add_box</mat-icon></button>
                </span>
              </mat-toolbar>
            </mat-card-header>
            <mat-card-content>
                <mat-table #table [dataSource]="datasource" matSort>
                  <ng-container matColumnDef="name">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Submitter </mat-header-cell>
                    <mat-cell *matCellDef="let submitter" (click)="navigate(submitter.string)" (keydown.enter)="handleKeyboardNavigation($event, submitter.string)" (keydown.space)="handleKeyboardNavigation($event, submitter.string)" tabindex="0" style="cursor: pointer;">{{ submitter.name }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="string">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> ID </mat-header-cell>
                    <mat-cell *matCellDef="let submitter" (click)="navigate(submitter.string)" (keydown.enter)="handleKeyboardNavigation($event, submitter.string)" (keydown.space)="handleKeyboardNavigation($event, submitter.string)" tabindex="0" style="cursor: pointer;">[{{ submitter.string }}]</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="delete">
                    <mat-header-cell *matHeaderCellDef mat-sort-header></mat-header-cell>
                    <mat-cell *matCellDef="let submitter">
                      <span class="hidden">
                        <button mat-icon-button matTooltip="Delete submitter" color="warn" (click)="delete(submitter)">
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
export class SubmitterListComponent extends SubmitterCreator implements AfterViewInit, OnChanges, OnInit, ListPage<ApiSubmitter> {
  @Input() parent: SubmitterListPageComponent;
  @Input() dataset: string;
  @Input() submitters: ApiSubmitter[];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  displayedColumns = ['name', 'string', 'delete'];
  // Initialize datasource early so lifecycle hooks can safely configure it
  datasource: MatTableDataSource<ApiSubmitter> = new MatTableDataSource<ApiSubmitter>([]);

  constructor(
    @Inject(Router) private router: Router,
    @Inject(SubmitterService) public submitterService: SubmitterService,
    @Inject(MatDialog) public dialog: MatDialog,
  ) {
    super(submitterService, dialog);
  }

  ngAfterViewInit() {
    ListPageHelper.init(this, this.submitters);
  }

  ngOnInit() {
    this.datasource = new MatTableDataSource<ApiSubmitter>(this.submitters);
    ListPageHelper.init(this, this.submitters);
  }

  ngOnChanges() {
    ListPageHelper.init(this, this.submitters);
  }

  pagesizeoptions(): number[] {
    return ListPageHelper.pagesizeoptions(this.submitters);
  }

  applyFilter(filterValue: string) {
    ListPageHelper.applyFilter(this, filterValue);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  submitterAnchor(): string {
    return undefined;
  }

  refreshSubmitter(submitter: ApiSubmitter) {
    this.parent.refreshSubmitter(submitter);
  }

  navigate(id: string) {
    this.router.navigate(['/' + this.dataset + '/submitters/' + id]);
  }

  handleKeyboardNavigation(event: KeyboardEvent, id: string) {
    if (event.key === ' ') {
      event.preventDefault();
    }
    this.navigate(id);
  }

  delete(source: ApiSubmitter) {
    this.submitterService.delete(this.dataset, source).subscribe((s: ApiSubmitter) => {
      this.refreshSubmitter(s);
    });
  }
}
