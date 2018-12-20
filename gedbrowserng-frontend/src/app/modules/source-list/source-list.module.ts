import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
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

import { ComponentsModule } from '../../components';

import { SourceListPageComponent } from './source-list-page.component';
import { SourceListComponent } from './source-list.component';
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
    MatCardModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatTableModule,
    MatTooltipModule,
    MatToolbarModule,

    ComponentsModule,
  ],
  declarations: [
    SourceListPageComponent,
    SourceListComponent,
  ],
  providers: [
    SourceListResolverService
  ]
})

export class SourceListModule {}
