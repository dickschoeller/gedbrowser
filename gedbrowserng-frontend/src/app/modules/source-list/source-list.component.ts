import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
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
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
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
    private router: Router,
    public sourceService: SourceService,
    public dialog: MatDialog,
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

  delete(source: ApiSource) {
    this.sourceService.delete(this.dataset, source).subscribe((s: ApiSource) => {
      this.refreshSource(s);
    });
  }
}
