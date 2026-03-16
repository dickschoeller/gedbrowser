import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';
import { MatTableDataSource, MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow } from '@angular/material/table';

import { SubmitterCreator } from '../../bases/submitter-creator';
import { NewSubmitterDialogComponent, ConfirmDialogComponent } from '../../components/';
import { NewSubmitterDialogData } from '../../models';
import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { NewSubmitterHelper, UrlBuilder, ListPage, ListPageHelper } from '../../utils';
import { SubmitterListPageComponent } from './submitter-list-page.component';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardContent, MatCardHeader } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
    selector: 'app-submitter-list',
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon inline=true matListIcon>contacts</mat-icon> Submitters</div></mat-card-title>
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
    @Inject(Router) private readonly router: Router,
    @Inject(SubmitterService) public readonly submitterService: SubmitterService,
    @Inject(MatDialog) public readonly dialog: MatDialog,
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
    event.preventDefault();
    this.navigate(id);
  }

  delete(submitter: ApiSubmitter) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { message: 'Are you sure you want to delete this submitter?' }
    });
    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.submitterService.delete(this.dataset, submitter).subscribe((s: ApiSubmitter) => {
          this.refreshSubmitter(s);
        });
      }
    });
  }
}
