import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {ButtonModule} from 'primeng/button';
import {DataViewModule} from 'primeng/dataview';
import {PanelModule} from 'primeng/panel';
import {TooltipModule} from 'primeng/tooltip';

import {SourceListPageComponent} from './source-list-page.component';
import {SourceListComponent} from './source-list.component';
import {SourceListItemComponent} from './source-list-item.component';
import {SourceListResolverService} from './source-list-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/sources',
    component: SourceListPageComponent,
    resolve: {
      sources: SourceListResolverService
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    CommonModule,

    ButtonModule,
    DataViewModule,
    PanelModule,
    TooltipModule,
  ],
  declarations: [
    SourceListPageComponent,
    SourceListComponent,
    SourceListItemComponent,
  ],
  providers: [
    SourceListResolverService
  ]
})

export class SourceListModule {}
