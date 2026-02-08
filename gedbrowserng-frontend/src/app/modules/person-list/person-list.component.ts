import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, MatSortHeader } from '@angular/material/sort';
import { MatTableDataSource, MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow } from '@angular/material/table';

import { PersonCreator } from '../../bases';
import { NewPersonDialogComponent } from '../../components';
import { ApiPerson, NewPersonDialogData } from '../../models';
import { PersonService } from '../../services';
import { UrlBuilder, NewPersonHelper, ListPage, ListPageHelper } from '../../utils';
import { PersonListPageComponent } from './person-list-page.component';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardContent, MatCardHeader } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';

@Component({
    selector: 'app-person-list',
    standalone: true,
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <mat-card-title><div class="with-icon"><mat-icon matListIcon>people</mat-icon> Persons</div></mat-card-title>
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
                  <button (click)="openCreatePersonDialog()" mat-icon-button color="primary"
                      matTooltip="Add person"><mat-icon>person_add</mat-icon></button>
                </span>
              </mat-toolbar>
            </mat-card-header> 
            <mat-card-content>
               <mat-table #table [dataSource]="datasource" matSort>
                  <ng-container matColumnDef="indexName">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Name </mat-header-cell>
                    <mat-cell *matCellDef="let person" (click)="navigate(person.string)" (keydown.enter)="navigate(person.string)" (keydown.space)="onSpaceKey($event, person.string)" tabindex="0" role="button" style="cursor: pointer;">{{ person.indexName }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="string">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> ID </mat-header-cell>
                    <mat-cell *matCellDef="let person" (click)="navigate(person.string)" (keydown.enter)="navigate(person.string)" (keydown.space)="onSpaceKey($event, person.string)" tabindex="0" role="button" style="cursor: pointer;">[{{ person.string }}]</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="birthdate">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Born </mat-header-cell>
                    <mat-cell *matCellDef="let person" (click)="navigate(person.string)" (keydown.enter)="navigate(person.string)" (keydown.space)="onSpaceKey($event, person.string)" tabindex="0" role="button" style="cursor: pointer;">{{ person.lifespan?.birthDate }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="deathdate">
                    <mat-header-cell *matHeaderCellDef mat-sort-header> Died </mat-header-cell>
                    <mat-cell *matCellDef="let person" (click)="navigate(person.string)" (keydown.enter)="navigate(person.string)" (keydown.space)="onSpaceKey($event, person.string)" tabindex="0" role="button" style="cursor: pointer;">{{ person.lifespan?.deathDate }}</mat-cell>
                  </ng-container>
                  <ng-container matColumnDef="delete">
                    <mat-header-cell *matHeaderCellDef mat-sort-header></mat-header-cell>
                    <mat-cell *matCellDef="let person">
                      <span class="hidden">
                        <button mat-icon-button matTooltip="Delete person" color="warn" (click)="delete(person)">
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
    styles: [],
    imports: [MainLayoutComponent, MatCard, MatCardTitle, MatIcon, MatCardContent, MatCardHeader, MatToolbar, MatFormField, MatInput, MatIconButton, MatTooltip, MatTable, MatSort, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatSortHeader, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow, MatPaginator]
})
export class PersonListComponent extends PersonCreator implements AfterViewInit, OnChanges, OnInit, ListPage<ApiPerson> {
  @Input() p: PersonListPageComponent;
  @Input() dataset: string;
  @Input() persons: ApiPerson[];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  displayedColumns = ['indexName', 'birthdate', 'deathdate', 'string', 'delete'];
  // Initialize datasource early so constructor can safely configure it
  datasource: MatTableDataSource<ApiPerson> = new MatTableDataSource<ApiPerson>([]);

  constructor(
    @Inject(Router) private readonly router: Router,
    @Inject(PersonService) public readonly personService: PersonService,
    @Inject(MatDialog) public readonly dialog: MatDialog
  ) {
    super(personService);
    // Make accessor null-safe in case lifespan or indexName are missing
    this.datasource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'birthdate': return this.dateCleanup(item?.lifespan?.birthDate ?? '');
        case 'deathdate': return this.dateCleanup(item?.lifespan?.deathDate ?? '');
        case 'indexName': return item?.indexName?.replace('?', 'AAAAAAAAAAAAAAAAAAA').toLocaleUpperCase() ?? '';
        default: return item?.[property] ?? '';
      }
    };
  }

  private dateCleanup(date: string): string {
    if (!date || date === '') {
      return '';
    }
    const and = /AND.*/;
    const dash = /-.*/;
    const strip = date
      .replace('ABT', '')
      .replace('BEF', '')
      .replace('AFT', '')
      .replace('BETWEEN', '')
      .replace('BET', '')
      .replace(and, '')
      .replace(dash, '')
      .trim();
    try {
      return new Date(strip).toISOString();
    } catch (exception) {
      return strip;
    }
  }

  ngAfterViewInit() {
    ListPageHelper.init(this, this.persons);
  }

  ngOnInit() { 
    // Ensure datasource contains the persons provided by the resolver
    this.datasource = new MatTableDataSource<ApiPerson>(this.persons);
    ListPageHelper.init(this, this.persons);
  }

  ngOnChanges() {
    ListPageHelper.init(this, this.persons);
  }

  pagesizeoptions(): number[] {
    return ListPageHelper.pagesizeoptions(this.persons);
  }

  applyFilter(filterValue: string) {
    ListPageHelper.applyFilter(this, filterValue);
  }

  openCreatePersonDialog(): void {
    const dialogRef = this.dialog.open(
      NewPersonDialogComponent,
      {
        data: NewPersonHelper.initNew('M', '')
      });

    dialogRef.afterClosed().subscribe((result: NewPersonDialogData) => {
      if (result !== undefined) {
        this.createPerson(result);
      }
    });
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons');
  }

  personAnchor(): string {
    return undefined;
  }

  refreshPerson() {
    this.p.refreshPerson();
  }

  navigate(id: string) {
    this.router.navigate(['/' + this.dataset + '/persons/' + id]);
  }

  onSpaceKey(event: KeyboardEvent, id: string) {
    event.preventDefault();
    this.navigate(id);
  }

  delete(person: ApiPerson) {
    this.personService.delete(this.dataset, person).subscribe((p: ApiPerson) => {
      this.refreshPerson();
    });
  }
}