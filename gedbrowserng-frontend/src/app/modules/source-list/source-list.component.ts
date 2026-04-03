import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';
import { MatTableDataSource, MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow } from '@angular/material/table';

import { SourceCreator } from '../../bases/source-creator';
import { UrlBuilder, ListPage, ListPageHelper } from '../../utils';
import { ConfirmDialogComponent } from '../../components';
import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { SourceListPageComponent } from './source-list-page.component';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardContent, MatCardHeader } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
    selector: 'app-source-list',
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon inline=true matListIcon>collections_bookmark</mat-icon> Sources</div></mat-card-title>
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
                    <mat-cell *matCellDef="let source" (click)="navigate(source.string)" (keydown.enter)="navigate(source.string)" (keydown.space)="onSpaceKey($event, source.string)" tabindex="0" role="button" style="cursor: pointer;">{{ source.title }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="string">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> ID </mat-header-cell>
                    <mat-cell *matCellDef="let source" (click)="navigate(source.string)" (keydown.enter)="navigate(source.string)" (keydown.space)="onSpaceKey($event, source.string)" tabindex="0" role="button" style="cursor: pointer;">[{{ source.string }}]</mat-cell>
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
    styles: [`
.inner-card {
  display: flex;
  flex-direction: column;
}

mat-card-title {
  padding-left: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
}

mat-card-title mat-icon {
    margin-left: 10px;
    margin-right: 10px;
}

.inner-card mat-card-header {
  flex-shrink: 0;
  background-color: transparent;
}

.inner-card mat-card-header mat-toolbar {
  padding-left: 24px;
  padding-right: 24px;
  padding-top: 24px;
  padding-bottom: 24px;
  margin: 0;
}

.inner-card mat-card-content {
  flex: 1;
  overflow: auto;
}

mat-form-field {
  margin-top: 20px;
}

.mat-icon {
    vertical-align: top;
    font-size: 1.25em;
}
    `],
    imports: [MainLayoutComponent, MatCard, MatCardTitle, MatIcon, MatCardContent, MatCardHeader, MatToolbar, MatFormField, MatInput, MatIconButton, MatTooltip, MatTable, MatSort, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatSortHeader, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow, MatPaginator]
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
    @Inject(Router) private readonly router: Router,
    @Inject(SourceService) public readonly sourceService: SourceService,
    @Inject(MatDialog) public readonly dialog: MatDialog,
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

  onSpaceKey(event: KeyboardEvent, id: string) {
    event.preventDefault();
    this.navigate(id);
  }

  delete(source: ApiSource) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Are you sure you want to delete this source?' }
    });
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.sourceService.delete(this.dataset, source).subscribe((s: ApiSource) => {
          this.refreshSource(s);
        });
      }
    });
  }
}
