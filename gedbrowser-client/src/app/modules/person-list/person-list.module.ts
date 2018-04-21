import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {PanelModule} from 'primeng/panel';
import {DataViewModule} from 'primeng/dataview';

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
    ComponentsModule,
    PanelModule,
    DataViewModule,
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
