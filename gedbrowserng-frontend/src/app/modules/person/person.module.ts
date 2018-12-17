import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';

import { DataViewModule } from 'primeng/dataview';
import { OrderListModule } from 'primeng/orderlist';

import { ComponentsModule } from '../../components';

import { LinkPersonComponent } from './link-person.component';
import { NewPersonComponent } from './new-person.component';
import { PersonComponent } from './person.component';
import { PersonFamilyChildComponent } from './person-family-child.component';
import { PersonFamilyChildListComponent } from './person-family-child-list.component';
import { PersonFamilyComponent } from './person-family.component';
import { PersonFamilyListComponent } from './person-family-list.component';
import { PersonFamilySpouseComponent } from './person-family-spouse.component';
import { PersonParentComponent } from './person-parent.component';
import { PersonParentFamiliesComponent } from './person-parent-families.component';
import { PersonParentFamilyComponent } from './person-parent-family.component';
import { PersonResolverService } from './person-resolver.service';

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

    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatToolbarModule,
    MatTooltipModule,

    DataViewModule,
    OrderListModule,

    ComponentsModule,
  ],
  declarations: [
    LinkPersonComponent,
    NewPersonComponent,
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
