import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { SourceCreator } from '../../bases/source-creator';
import { NewSourceHelper, UrlBuilder, ListPage, ListPageHelper } from '../../utils';
import { NewSourceDialogComponent } from '../../components';
import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { SourceListPageComponent } from './source-list-page.component';

@Component({
  standalone: false,
  selector: 'app-source-list',
  template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon matListIcon>collections_bookmark</mat-icon> Sources</div></mat-card-title>
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
                  <button (click)="openCreateSourceDialog()" mat-icon-button color="primary"
                      matTooltip="Add source"><mat-icon>add_comment</mat-icon></button>
                </span>
              </mat-toolbar>
            </mat-card-header>
            <mat-card-content>
                <mat-table #table [dataSource]="datasource" matSort>
                  <ng-container matColumnDef="title">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Source </mat-header-cell>
                    <mat-cell *matCellDef="let source" (click)="navigate(source.string)" (keydown.enter)="navigateOnKeyboard($event, source.string)" (keydown.space)="navigateOnKeyboard($event, source.string)" tabindex="0" role="button" style="cursor: pointer;">{{ source.title }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="string">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> ID </mat-header-cell>
                    <mat-cell *matCellDef="let source" (click)="navigate(source.string)" style="cursor: pointer;">[{{ source.string }}]</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="delete">
                    <mat-header-cell *matHeaderCellDef mat-sort-header></mat-header-cell>
                    <mat-cell *matCellDef="let source">
                      <span class="hidden">
                        <button mat-icon-button matTooltip="Delete source" color="warn" (click)="delete(source)">
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
export class SourceListComponent extends SourceCreator implements AfterViewInit, OnChanges, OnInit, ListPage<ApiSource> {
  @Input() parent: SourceListPageComponent;
  @Input() dataset: string;
  @Input() sources: ApiSource[];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  displayedColumns = ['title', 'string', 'delete'];
  // Initialize datasource early so lifecycle hooks can safely configure it
  datasource: MatTableDataSource<ApiSource> = new MatTableDataSource<ApiSource>([]);

  constructor(
    @Inject(Router) private router: Router,
    @Inject(SourceService) public sourceService: SourceService,
    @Inject(MatDialog) public dialog: MatDialog,
  ) { super(sourceService, dialog); }

  ngAfterViewInit() {
    ListPageHelper.init(this, this.sources);
  }

  ngOnInit() {
    this.datasource = new MatTableDataSource<ApiSource>(this.sources);
    ListPageHelper.init(this, this.sources);
  }

  ngOnChanges() {
    ListPageHelper.init(this, this.sources);
  }

  pagesizeoptions(): number[] {
    return ListPageHelper.pagesizeoptions(this.sources);
  }

  applyFilter(filterValue: string) {
    ListPageHelper.applyFilter(this, filterValue);
  }

  sourceUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'sources');
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource) {
    this.parent.refreshSource(source);
  }

  navigate(id: string) {
    this.router.navigate(['/' + this.dataset + '/sources/' + id]);
  }

  navigateOnKeyboard(event: KeyboardEvent, id: string) {
    if (event.key === ' ') {
      event.preventDefault();
    }
    this.navigate(id);
  }

  delete(source: ApiSource) {
    this.sourceService.delete(this.dataset, source).subscribe((s: ApiSource) => {
      this.refreshSource(s);
    });
  }
}
