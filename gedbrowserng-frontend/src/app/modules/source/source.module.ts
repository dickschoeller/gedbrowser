import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { ComponentsModule } from '../../components';
import { SourceComponent } from './source.component';
import { SourceResolverService } from './source-resolver.service';

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

    MatCardModule,
    MatIconModule,

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
