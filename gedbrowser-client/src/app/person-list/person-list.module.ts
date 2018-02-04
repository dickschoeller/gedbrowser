import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatCardModule, MatDividerModule, MatListModule} from '@angular/material';

import {PersonListComponent} from './person-list.component';
import {PersonListItemComponent} from './person-list-item.component';
import {PersonListResolver} from './person-list-resolver.service';
import {SharedModule} from '../shared';

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
    SharedModule,
    CommonModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
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
