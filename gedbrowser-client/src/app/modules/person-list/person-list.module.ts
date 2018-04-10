import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatListModule} from '@angular/material';

import {ComponentsModule} from '../../components';

import {PersonListComponent} from './person-list.component';
import {PersonListItemComponent} from './person-list-item.component';
import {PersonListResolver} from './person-list-resolver.service';

const personRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'persons',
    component: PersonListComponent,
    resolve: {
      persons: PersonListResolver
    }
  }
]);

@NgModule({
  imports: [
    personRouting,
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
    ComponentsModule,
  ],
  declarations: [
    PersonListComponent,
    PersonListItemComponent
  ],
  providers: [
    PersonListResolver
  ]
})
export class PersonListModule {}
