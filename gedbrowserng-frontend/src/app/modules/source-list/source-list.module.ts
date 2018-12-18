import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

// import { MatCardModule } from '@angular/material/card';
// import { MatGridListModule } from '@angular/material/grid-list';
// import { MatToolbarModule } from '@angular/material/toolbar';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

import { DataViewModule } from 'primeng/dataview';

import { ComponentsModule } from '../../components';

import { SourceListPageComponent } from './source-list-page.component';
import { SourceListComponent } from './source-list.component';
import { SourceListItemComponent } from './source-list-item.component';
import { SourceListResolverService } from './source-list-resolver.service';

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

    MatButtonModule,
    MatIconModule,
    MatTooltipModule,

    DataViewModule,

    ComponentsModule,
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
