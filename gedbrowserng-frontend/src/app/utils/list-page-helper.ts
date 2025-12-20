// Use lightweight local interfaces instead of importing Angular Material types
// This avoids hard dependency on Angular Material typings during compilation
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

// Minimal, local abstractions for paginator/sort/dataSource to decouple from
// Angular Material. If you prefer stronger typing, replace these with the
// actual Material types in files that already import Material.
export interface PaginatorLike {
  pageIndex?: number;
  pageSize?: number;
  length?: number;
}

export interface SortLike {
  active?: string;
  direction?: 'asc' | 'desc' | '';
}

export interface TableDataSourceLike<T> {
  paginator?: PaginatorLike | null;
  sort?: SortLike | null;
  data: T[];
  filter?: string;
}

export interface ListPage<T> {
  paginator: PaginatorLike | null;
  sort: SortLike | null;

  displayedColumns: string[];
  datasource: TableDataSourceLike<T>;
}
