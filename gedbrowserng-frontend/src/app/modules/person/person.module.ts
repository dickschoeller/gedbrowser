import {CommonModule} from '@angular/common';
import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {NgxGalleryModule} from 'ngx-gallery';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import {AccordionModule} from 'primeng/accordion';
import {ButtonModule} from 'primeng/button';
import {DataViewModule} from 'primeng/dataview';
import {DialogModule} from 'primeng/dialog';
import {MenuModule} from 'primeng/menu';
import {OrderListModule} from 'primeng/orderlist';
import {PanelModule} from 'primeng/panel';
import {TooltipModule} from 'primeng/tooltip';

import {ComponentsModule} from '../../components';

import {PersonComponent} from './person.component';
import {PersonFamilyChildComponent} from './person-family-child.component';
import {PersonFamilyChildListComponent} from './person-family-child-list.component';
import {PersonFamilyComponent} from './person-family.component';
import {PersonFamilyListComponent} from './person-family-list.component';
import {PersonFamilySpouseComponent} from './person-family-spouse.component';
import {PersonParentComponent} from './person-parent.component';
import {PersonParentFamiliesComponent} from './person-parent-families.component';
import {PersonParentFamilyComponent} from './person-parent-family.component';
import {PersonResolverService} from './person-resolver.service';

const personRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/persons/:string',
    component: PersonComponent,
    resolve: {
      person: PersonResolverService
    }
  }
]);

/**
 * The module for a person page routing.
 */
@NgModule( {
  imports: [
    personRouting,
    CommonModule,

    NgxGalleryModule,
    Ng2PageScrollModule,

    AccordionModule,
    ButtonModule,
    DataViewModule,
    DialogModule,
    MenuModule,
    OrderListModule,
    PanelModule,
    TooltipModule,

    ComponentsModule,
  ],
  declarations: [
    PersonComponent,
    PersonFamilyChildComponent,
    PersonFamilyChildListComponent,
    PersonFamilyComponent,
    PersonFamilyListComponent,
    PersonFamilySpouseComponent,
    PersonParentFamiliesComponent,
    PersonParentFamilyComponent,
    PersonParentComponent,
  ],
  providers: [
    PersonResolverService,
  ]
} )

export class PersonModule {}
