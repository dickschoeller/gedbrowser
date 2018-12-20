import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatToolbarModule } from '@angular/material/toolbar';

import { ComponentsModule } from '../../components';

import { PersonListPageComponent } from './person-list-page.component';
import { PersonListComponent } from './person-list.component';
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
    MatCardModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatTableModule,
    MatTooltipModule,
    MatToolbarModule,

    ComponentsModule,
  ],
  declarations: [
    PersonListComponent,
    PersonListPageComponent
  ],
  providers: [
    PersonListResolverService
  ]
})
export class PersonListModule {}
