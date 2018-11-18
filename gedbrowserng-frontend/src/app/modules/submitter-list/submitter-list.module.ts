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
import { PanelModule } from 'primeng/panel';

import { ComponentsModule } from '../../components';

import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterListComponent } from './submitter-list.component';
import { SubmitterListItemComponent } from './submitter-list-item.component';
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
    MatIconModule,
    MatTooltipModule,

    DataViewModule,
    PanelModule,

    ComponentsModule,
  ],
  declarations: [
    SubmitterListComponent,
    SubmitterListItemComponent,
    SubmitterListPageComponent
  ],
  providers: [
    SubmitterListResolverService
  ]
})
export class SubmitterListModule {}
