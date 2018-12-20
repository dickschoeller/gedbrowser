import { MatSort, MatPaginator } from '@angular/material';
import { MatTableDataSource } from '@angular/material/table';
export class ListPageHelper {
  public static init<T>(page: ListPage<T>, data: T[]) {
    page.datasource.paginator = page.paginator;
    page.datasource.sort = page.sort;
    page.datasource.data = data;
  }


  public static pagesizeoptions<T>(data: T[]): number[] {
    return [15, 30, 100, 500, data.length];
  }

  public static applyFilter<T>(page: ListPage<T>, filterValue: string): void {
    page.datasource.filter = filterValue.trim().toLowerCase();
  }
}

export interface ListPage<T> {
  paginator: MatPaginator;
  sort: MatSort;

  displayedColumns: string[];
  datasource: MatTableDataSource<T>;
}
