import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { PersonComponent } from './person.component';
import { PersonResolver } from './person-resolver.service';
import { SharedModule } from '../shared';

const personRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'persons/:string',
    component: PersonComponent,
    resolve: {
      person: PersonResolver
    }
  }
]);

@NgModule({
  imports: [
    personRouting,
    SharedModule,
    CommonModule
  ],
  declarations: [
    PersonComponent,
  ],
  providers: [
    PersonResolver
  ]
})

export class PersonModule {}
