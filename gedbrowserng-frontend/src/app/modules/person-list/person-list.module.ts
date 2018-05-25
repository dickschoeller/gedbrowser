import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';

import {ButtonModule} from 'primeng/button';
import {DataViewModule} from 'primeng/dataview';
import {PanelModule} from 'primeng/panel';
import {TooltipModule} from 'primeng/tooltip';

import {ComponentsModule} from '../../components';

import {PersonListPageComponent} from './person-list-page.component';
import {PersonListComponent} from './person-list.component';
import {PersonListItemComponent} from './person-list-item.component';
import {PersonListResolverService} from './person-list-resolver.service';

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

    ButtonModule,
    DataViewModule,
    PanelModule,
    TooltipModule,

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
