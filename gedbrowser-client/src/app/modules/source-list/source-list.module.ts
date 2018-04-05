import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatListModule} from '@angular/material';

import {ComponentsModule} from '../../components';

import {SourceListComponent} from './source-list.component';
import {SourceListItemComponent} from './source-list-item.component';
import {SourceListResolver} from './source-list-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'sources',
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
    MatCardModule,
    MatDividerModule,
    MatListModule,
    ComponentsModule
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
