import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {PanelModule} from 'primeng/panel';
import {DataViewModule} from 'primeng/dataview';

import {ComponentsModule} from '../../components';

import {SubmitterListComponent} from './submitter-list.component';
import {SubmitterListItemComponent} from './submitter-list-item.component';
import {SubmitterListResolver} from './submitter-list-resolver.service';

const submitterRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/submitters',
    component: SubmitterListComponent,
    resolve: {
      submitters: SubmitterListResolver
    }
  }
]);

@NgModule({
  imports: [
    submitterRouting,
    CommonModule,
    ComponentsModule,
    PanelModule,
    DataViewModule,
  ],
  declarations: [
    SubmitterListComponent,
    SubmitterListItemComponent
  ],
  providers: [
    SubmitterListResolver
  ]
})
export class SubmitterListModule {}
