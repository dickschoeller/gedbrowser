import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatListModule} from '@angular/material';

import {SourceListComponent} from './source-list.component';
import {SourceListItemComponent} from './source-list-item.component';
import {SourceListResolver} from './source-list-resolver.service';
import {SharedModule} from '../shared';

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
    SharedModule,
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
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
