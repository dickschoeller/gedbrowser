import { AfterViewInit, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { SourceCreator } from '../../bases';
import { NewSourceDialogComponent } from '../../components';
import { NewSourceDialogData } from '../../models';
import { ApiSource } from '../../models';
import { SourceService, NewSourceLinkService } from '../../services';
import { NewSourceHelper, UrlBuilder } from '../../utils';
import { SourceListPageComponent } from './source-list-page.component';

@Component({
  selector: 'app-source-list',
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
})
export class SourceListComponent extends SourceCreator implements AfterViewInit, OnChanges, OnInit {
  @Input() parent: SourceListPageComponent;
  @Input() dataset: string;
  @Input() sources: Array<ApiSource>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  displayedColumns = ['title', 'string', 'delete'];
  datasource = new MatTableDataSource<ApiSource>(this.sources);

  constructor(
    private router: Router,
    private sourceService: SourceService,
    public newSourceLinkService: NewSourceLinkService,
    public dialog: MatDialog,
  ) {
    super(newSourceLinkService, dialog);
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
    this.datasource.data = this.sources;
  }

  pagesizeoptions(): number[] {
    return [15, 30, 100, 500, this.sources.length];
  }

  applyFilter(filterValue: string) {
    this.datasource.filter = filterValue.trim().toLowerCase();
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
