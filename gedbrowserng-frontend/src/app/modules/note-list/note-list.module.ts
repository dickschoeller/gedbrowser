import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

// import { MatCardModule } from '@angular/material/card';
// import { MatGridListModule } from '@angular/material/grid-list';
// import { MatToolbarModule } from '@angular/material/toolbar';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

import { DataViewModule } from 'primeng/dataview';

import { ComponentsModule } from '../../components';

import { NoteListPageComponent } from './note-list-page.component';
import { NoteListComponent } from './note-list.component';
import { NoteListItemComponent } from './note-list-item.component';
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
    MatIconModule,
    MatTooltipModule,

    DataViewModule,

    ComponentsModule,
  ],
  declarations: [
    NoteListPageComponent,
    NoteListComponent,
    NoteListItemComponent,
  ],
  providers: [
    NoteListResolverService
  ]
})

export class NoteListModule {}
