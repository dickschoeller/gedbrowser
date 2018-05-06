import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {NgxGalleryModule} from 'ngx-gallery';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import {DialogModule} from 'primeng/dialog';
import {PanelModule} from 'primeng/panel';
import {AccordionModule} from 'primeng/accordion';
import {DataViewModule} from 'primeng/dataview';
import {ButtonModule} from 'primeng/button';
import {TooltipModule} from 'primeng/tooltip';
import {OrderListModule} from 'primeng/orderlist';
import {MenuModule} from 'primeng/menu';

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
    ComponentsModule,
    PanelModule,
    AccordionModule,
    DataViewModule,
    ButtonModule,
    TooltipModule,
    OrderListModule,
    MenuModule,
    DialogModule,
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
