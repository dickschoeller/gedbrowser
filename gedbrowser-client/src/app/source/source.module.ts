import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatExpansionModule, MatListModule} from '@angular/material';
import {NgxGalleryModule} from 'ngx-gallery';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import {SharedModule} from '../shared';
import {SourceComponent} from './source.component';
import {SourceResolver} from './source-resolver.service';

const sourceRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'sources/:string',
    component: SourceComponent,
    resolve: {
      source: SourceResolver
    }
  }
]);

@NgModule({
  imports: [
    sourceRouting,
    SharedModule,
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatExpansionModule,
    MatListModule,
    NgxGalleryModule,
    Ng2PageScrollModule.forRoot(),
  ],
  declarations: [
    SourceComponent,
  ],
  providers: [
    SourceResolver
  ]
})

export class SourceModule {}
