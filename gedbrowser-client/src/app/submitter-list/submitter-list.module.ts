import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SubmitterListComponent } from './submitter-list.component';
import { SubmitterListItemComponent } from './submitter-list-item.component';
import { SubmitterListResolver } from './submitter-list-resolver.service';
import { SharedModule } from '../shared';

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
    SharedModule,
    CommonModule
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
