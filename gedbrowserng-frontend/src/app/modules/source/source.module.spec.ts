import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { SourceModule } from './source.module';
import { SourceComponent } from './source.component';

describe('SourceModule', () => {
  it('should be defined', () => {
    expect(SourceModule).toBeDefined();
  });

  it('should import CommonModule', () => {
    const moduleMetadata = (SourceModule as any).ɵmod;
    expect(moduleMetadata.imports).toContain(CommonModule);
  });

  it('should import MatCardModule', () => {
    const moduleMetadata = (SourceModule as any).ɵmod;
    const imports = moduleMetadata.imports;

    expect(imports.some((i: any) => i === MatCardModule || i?.ɵmod?.name === 'MatCardModule')).toBeTruthy();
  });

  it('should import MatIconModule', () => {
    const moduleMetadata = (SourceModule as any).ɵmod;
    const imports = moduleMetadata.imports;

    expect(imports.some((i: any) => i === MatIconModule || i?.ɵmod?.name === 'MatIconModule')).toBeTruthy();
  });

  it('should declare SourceComponent', () => {
    const moduleMetadata = (SourceModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations).toContain(SourceComponent);
  });

  it('should have a single declaration', () => {
    const moduleMetadata = (SourceModule as any).ɵmod;
    const declarations = moduleMetadata.declarations;

    expect(declarations.length).toBe(1);
    expect(declarations[0]).toBe(SourceComponent);
  });
});
