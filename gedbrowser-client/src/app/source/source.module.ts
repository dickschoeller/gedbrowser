import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../shared';

import { SourceComponent } from './source.component';
import { SourceResolver } from './source-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'sources/:string',
    component: SourceComponent,
    resolve: {
      source: SourceResolver
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    SharedModule,
    CommonModule
  ],
  declarations: [
    SourceComponent,
  ],
  providers: [
    SourceResolver
  ]
})

export class SourceModule {}
