import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {
  MatButtonModule,
  MatCardModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
} from '@angular/material';

import {PersonComponent} from './person.component';
import {PersonFamilyChildComponent} from './person-family-child.component';
import {PersonFamilyChildListComponent} from './person-family-child-list.component';
import {PersonFamilyComponent} from './person-family.component';
import {PersonFamilyListComponent} from './person-family-list.component';
import {PersonFamilySpouseComponent} from './person-family-spouse.component';
import {PersonParentComponent} from './person-parent.component';
import {PersonParentFamiliesComponent} from './person-parent-families.component';
import {PersonParentFamilyComponent} from './person-parent-family.component';
import {PersonResolver} from './person-resolver.service';
import {SharedModule} from '../shared';

const personRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'persons/:string',
    component: PersonComponent,
    resolve: {
      person: PersonResolver
    }
  }
]);

/**
 * The module for a person page routing.
 */
@NgModule({
  imports: [
    personRouting,
    CommonModule,
    SharedModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatListModule,
    MatMenuModule,
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
    PersonResolver,
  ]
})

export class PersonModule {}
