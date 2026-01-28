import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatToolbarModule } from '@angular/material/toolbar';

import { SubmitterListModule } from './submitter-list.module';
import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterListComponent } from './submitter-list.component';

describe('SubmitterListModule', () => {
  it('should be defined', () => {
    expect(SubmitterListModule).toBeDefined();
  });

  it('should import CommonModule', () => {
    const moduleMetadata = (SubmitterListModule as any).ɵmod;
    expect(moduleMetadata.imports).toContain(CommonModule);
  });

  it('should import all required Material modules', () => {
    const moduleMetadata = (SubmitterListModule as any).ɵmod;
    const imports = moduleMetadata.imports;

    expect(imports.some((i: any) => i === MatButtonModule || i?.ɵmod?.name === 'MatButtonModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatCardModule || i?.ɵmod?.name === 'MatCardModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatIconModule || i?.ɵmod?.name === 'MatIconModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatInputModule || i?.ɵmod?.name === 'MatInputModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatPaginatorModule || i?.ɵmod?.name === 'MatPaginatorModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatProgressSpinnerModule || i?.ɵmod?.name === 'MatProgressSpinnerModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatSortModule || i?.ɵmod?.name === 'MatSortModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatTableModule || i?.ɵmod?.name === 'MatTableModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatTooltipModule || i?.ɵmod?.name === 'MatTooltipModule')).toBeTruthy();
    expect(imports.some((i: any) => i === MatToolbarModule || i?.ɵmod?.name === 'MatToolbarModule')).toBeTruthy();
  });

  it('should declare SubmitterListPageComponent', () => {
    const moduleMetadata = (SubmitterListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(SubmitterListPageComponent);
  });

  it('should declare SubmitterListComponent', () => {
    const moduleMetadata = (SubmitterListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(SubmitterListComponent);
  });

  it('should have two declarations', () => {
    const moduleMetadata = (SubmitterListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations.length).toBe(2);
  });
});
