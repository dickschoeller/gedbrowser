import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {PanelModule} from 'primeng/panel';

import {ComponentsModule} from '../../components';

import {SubmitterComponent} from './submitter.component';
import {SubmitterResolverService} from './submitter-resolver.service';

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
    PanelModule,
  ],
  declarations: [
    SubmitterComponent,
  ],
  providers: [
    SubmitterResolverService
  ]
})

export class SubmitterModule {}
