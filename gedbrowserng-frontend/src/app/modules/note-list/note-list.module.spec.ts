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

import { NoteListModule } from './note-list.module';
import { NoteListPageComponent } from './note-list-page.component';
import { NoteListComponent } from './note-list.component';

describe('NoteListModule', () => {
  it('should be defined', () => {
    expect(NoteListModule).toBeDefined();
  });

  it('should import CommonModule', () => {
    const moduleMetadata = (NoteListModule as any).ɵmod;
    expect(moduleMetadata.imports).toContain(CommonModule);
  });

  it('should import all required Material modules', () => {
    const moduleMetadata = (NoteListModule as any).ɵmod;
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

  it('should declare NoteListPageComponent', () => {
    const moduleMetadata = (NoteListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(NoteListPageComponent);
  });

  it('should declare NoteListComponent', () => {
    const moduleMetadata = (NoteListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(NoteListComponent);
  });

  it('should have two declarations', () => {
    const moduleMetadata = (NoteListModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations.length).toBe(2);
  });
});
