import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { PanelModule } from 'primeng/panel';

import { ComponentsModule } from '../../components';
import { SourceComponent } from './source.component';
import { SourceResolverService } from './source-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/sources/:string',
    component: SourceComponent,
    resolve: {
      source: SourceResolverService
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    CommonModule,

    PanelModule,

    ComponentsModule,
  ],
  declarations: [
    SourceComponent,
  ],
  providers: [
    SourceResolverService
  ]
})

export class SourceModule {}
