import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';
import { DataViewModule } from 'primeng/dataview';
import { PanelModule } from 'primeng/panel';
import { TooltipModule } from 'primeng/tooltip';

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

    ButtonModule,
    DataViewModule,
    PanelModule,
    TooltipModule,

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
