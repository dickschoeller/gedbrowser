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

import { NoteListPageComponent } from './note-list-page.component';
import { NoteListComponent } from './note-list.component';
import { NoteListResolverService } from './note-list-resolver.service';

const noteRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/notes',
    component: NoteListPageComponent,
    resolve: {
      notes: NoteListResolverService
    }
  }
]);

@NgModule({
  imports: [
    noteRouting,
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
    NoteListPageComponent,
    NoteListComponent,
  ],
  providers: [
    NoteListResolverService
  ]
})

export class NoteListModule {}
