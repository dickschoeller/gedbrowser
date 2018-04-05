import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatListModule} from '@angular/material';

import {ComponentsModule} from '../../components';

import {SubmitterListComponent} from './submitter-list.component';
import {SubmitterListItemComponent} from './submitter-list-item.component';
import {SubmitterListResolver} from './submitter-list-resolver.service';

const submitterRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'submitters',
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
    MatCardModule,
    MatDividerModule,
    MatListModule,
    ComponentsModule
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
