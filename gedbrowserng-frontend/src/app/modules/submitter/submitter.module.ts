import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { ComponentsModule } from '../../components';

import { SubmitterComponent } from './submitter.component';
import { SubmitterResolverService } from './submitter-resolver.service';

const submitterRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/submitters/:string',
    component: SubmitterComponent,
    resolve: {
      submitter: SubmitterResolverService
    }
  }
]);

@NgModule({
  imports: [
    submitterRouting,
    CommonModule,
    ComponentsModule,

    MatCardModule,
    MatIconModule,
  ],
  declarations: [
    SubmitterComponent,
  ],
  providers: [
    SubmitterResolverService
  ]
})

export class SubmitterModule {}
