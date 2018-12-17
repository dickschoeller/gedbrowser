import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

import { DataViewModule } from 'primeng/dataview';

import { ComponentsModule } from '../../components';

import { PersonListPageComponent } from './person-list-page.component';
import { PersonListComponent } from './person-list.component';
import { PersonListItemComponent } from './person-list-item.component';
import { PersonListResolverService } from './person-list-resolver.service';

const personRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/persons',
    component: PersonListPageComponent,
    resolve: {
      persons: PersonListResolverService
    }
  }
]);

@NgModule({
  imports: [
    personRouting,
    CommonModule,

    MatButtonModule,
    MatIconModule,
    MatTooltipModule,

    DataViewModule,

    ComponentsModule,
  ],
  declarations: [
    PersonListComponent,
    PersonListItemComponent,
    PersonListPageComponent
  ],
  providers: [
    PersonListResolverService
  ]
})
export class PersonListModule {}
