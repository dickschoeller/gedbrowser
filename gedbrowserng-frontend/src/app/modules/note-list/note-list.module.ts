import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { ButtonModule } from 'primeng/button';
import { DataViewModule } from 'primeng/dataview';
import { PanelModule } from 'primeng/panel';
import { TooltipModule } from 'primeng/tooltip';

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

    ButtonModule,
    DataViewModule,
    PanelModule,
    TooltipModule,

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
