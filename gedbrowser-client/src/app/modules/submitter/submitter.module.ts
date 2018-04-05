import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule} from '@angular/material';

import {ComponentsModule} from '../../components';

import {SubmitterComponent} from './submitter.component';
import {SubmitterResolver} from './submitter-resolver.service';

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
    CommonModule,
    MatCardModule,
    MatDividerModule,
    ComponentsModule,
  ],
  declarations: [
    SubmitterComponent,
  ],
  providers: [
    SubmitterResolver
  ]
})

export class SubmitterModule {}
