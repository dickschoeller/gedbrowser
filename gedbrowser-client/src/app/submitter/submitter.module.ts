import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SharedModule } from '../shared';

import { SubmitterComponent } from './submitter.component';
import { SubmitterResolver } from './submitter-resolver.service';

const submitterRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'submitters/:string',
    component: SubmitterComponent,
    resolve: {
      submitter: SubmitterResolver
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
    SubmitterComponent,
  ],
  providers: [
    SubmitterResolver
  ]
})

export class SubmitterModule { }
