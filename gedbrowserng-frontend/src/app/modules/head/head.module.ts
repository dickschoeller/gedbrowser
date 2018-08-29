import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { PanelModule } from 'primeng/panel';

import { ComponentsModule } from '../../components';

import { HeadComponent } from './head.component';
import { HeadResolverService } from './head-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/head',
    component: HeadComponent,
    resolve: {
      head: HeadResolverService
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
    HeadComponent,
  ],
  providers: [
    HeadResolverService
  ]
})

export class HeadModule {}
