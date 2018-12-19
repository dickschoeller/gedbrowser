import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { SubmitterCreator } from '../../bases/submitter-creator';
import { NewSubmitterDialogComponent } from '../../components/';
import { NewSubmitterDialogData } from '../../models';
import { ApiSubmitter } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { NewSubmitterHelper, UrlBuilder } from '../../utils';
import { SubmitterListPageComponent } from './submitter-list-page.component';

@Component({
  selector: 'app-submitter-list',
  templateUrl: './submitter-list.component.html',
  styleUrls: ['./submitter-list.component.css']
})
export class SubmitterListComponent extends SubmitterCreator implements AfterViewInit, OnChanges, OnInit {
  @Input() parent: SubmitterListPageComponent;
  @Input() dataset: string;
  @Input() submitters: ApiSubmitter[];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ['name', 'string', 'delete'];
  datasource = new MatTableDataSource<ApiSubmitter>(this.submitters);

  constructor(
    private router: Router,
    private submitterService: SubmitterService,
    public newSubmitterLinkService: NewSubmitterLinkService,
    public dialog: MatDialog,
  ) {
    super(newSubmitterLinkService, dialog);
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
    this.datasource.data = this.submitters;
  }

  pagesizeoptions(): number[] {
    return [15, 30, 100, 500, this.submitters.length];
  }

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
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

  delete(source: ApiSubmitter) {
    this.submitterService.delete(this.dataset, source).subscribe((s: ApiSubmitter) => {
      this.refreshSubmitter(s);
    });
  }
}
