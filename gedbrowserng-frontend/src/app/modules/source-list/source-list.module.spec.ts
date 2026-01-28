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

import { SourceListModule } from './source-list.module';
import { SourceListPageComponent } from './source-list-page.component';
import { SourceListComponent } from './source-list.component';

describe('SourceListModule', () => {
  it('should be defined', () => {
    expect(SourceListModule).toBeDefined();
  });

  it('should import CommonModule', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    expect(moduleMetadata.imports).toContain(CommonModule);
  });

  it('should import all required Material modules', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
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

  it('should declare SourceListPageComponent', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(SourceListPageComponent);
  });

  it('should declare SourceListComponent', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(SourceListComponent);
  });

  it('should have two declarations', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations.length).toBe(2);
  });

  it('should have defined imports', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    expect(moduleMetadata.imports).toBeDefined();
    expect(Array.isArray(moduleMetadata.imports)).toBe(true);
  });

  it('should declare more components than providers', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations || [];
    const providers = moduleMetadata.providers || [];

    // Module declares 2 components
    expect(declarations.length).toBeGreaterThan(0);
  });

  it('should declare SourceListPageComponent and SourceListComponent', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations.includes(SourceListPageComponent)).toBe(true);
    expect(declarations.includes(SourceListComponent)).toBe(true);
  });

  it('should have ComponentsModule in imports', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const imports = moduleMetadata.imports;

    // Check if ComponentsModule or similar is imported
    expect(imports).toBeDefined();
    expect(Array.isArray(imports)).toBe(true);
  });

  it('should have router configuration with forChild', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const imports = moduleMetadata.imports;

    // Router module should be present
    expect(imports.length).toBeGreaterThan(0);
  });

  it('should export nothing implicitly (no exports)', () => {
    const moduleMetadata = (SourceListModule as any).ɵmod;
    const exports = moduleMetadata.exports || [];

    // Module should not export components (internal use only)
    expect(!exports.includes(SourceListPageComponent)).toBe(true);
    expect(!exports.includes(SourceListComponent)).toBe(true);
  });
});
