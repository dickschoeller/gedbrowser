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

import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterListComponent } from './submitter-list.component';
import { SubmitterListResolverService } from './submitter-list-resolver.service';

const submitterRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/submitters',
    component: SubmitterListPageComponent,
    resolve: {
      submitters: SubmitterListResolverService
    }
  }
]);

@NgModule({
  imports: [
    submitterRouting,
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
    SubmitterListComponent,
    SubmitterListPageComponent
  ],
  providers: [
    SubmitterListResolverService
  ]
})
export class SubmitterListModule {}
