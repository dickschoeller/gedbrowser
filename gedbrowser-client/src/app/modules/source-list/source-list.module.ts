import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {PanelModule} from 'primeng/panel';
import {DataViewModule} from 'primeng/dataview';

import {ComponentsModule} from '../../components';

import {SourceListComponent} from './source-list.component';
import {SourceListItemComponent} from './source-list-item.component';
import {SourceListResolver} from './source-list-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/sources',
    component: SourceListComponent,
    resolve: {
      sources: SourceListResolver
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    CommonModule,
    ComponentsModule,
    PanelModule,
    DataViewModule,
  ],
  declarations: [
    SourceListComponent,
    SourceListItemComponent
  ],
  providers: [
    SourceListResolver
  ]
})

export class SourceListModule {}
