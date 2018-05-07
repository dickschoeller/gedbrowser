import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {NgxGalleryModule} from 'ngx-gallery';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import {PanelModule} from 'primeng/panel';

import {ComponentsModule} from '../../components';

import {SourceComponent} from './source.component';
import {SourceResolverService} from './source-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/sources/:string',
    component: SourceComponent,
    resolve: {
      source: SourceResolverService
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    CommonModule,
    NgxGalleryModule,
    Ng2PageScrollModule,

    PanelModule,

    ComponentsModule,
  ],
  declarations: [
    SourceComponent,
  ],
  providers: [
    SourceResolverService
  ]
})

export class SourceModule {}