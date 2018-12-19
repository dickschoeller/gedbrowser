import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { PersonCreator } from '../../bases';
import { NewPersonDialogComponent } from '../../components';
import { ApiPerson, NewPersonDialogData } from '../../models';
import { PersonService, NewPersonLinkService } from '../../services';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { PersonListPageComponent } from './person-list-page.component';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent extends PersonCreator implements AfterViewInit, OnChanges, OnInit {
  @Input() p: PersonListPageComponent;
  @Input() dataset: string;
  @Input() persons: ApiPerson[];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ['indexName', 'birthdate', 'deathdate', 'string', 'delete'];
  datasource = new MatTableDataSource<ApiPerson>(this.persons);

  constructor(
    private router: Router,
    private personService: PersonService,
    public newPersonLinkService: NewPersonLinkService,
    public dialog: MatDialog
  ) {
    super(newPersonLinkService);
    this.datasource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'birthdate': return this.dateCleanup(item.lifespan.birthDate);
        case 'deathdate': return this.dateCleanup(item.lifespan.deathDate);
        case 'indexName': return item.indexName.replace('?', 'AAAAAAAAAAAAAAAAAAA').toLocaleUpperCase();
        default: return item[property];
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
    this.init();
  }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  init() {
    this.datasource.paginator = this.paginator;
    this.datasource.sort = this.sort;
    this.datasource.data = this.persons;
  }

  pagesizeoptions(): number[] {
    return [15, 30, 100, 500, this.persons.length];
  }

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
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

  delete(person: ApiPerson) {
    this.personService.delete(this.dataset, person).subscribe((p: ApiPerson) => {
      this.refreshPerson();
    });
  }
}
