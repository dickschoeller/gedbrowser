import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { PanelModule } from 'primeng/panel';

import { ComponentsModule } from '../../components';

import { NoteComponent } from './note.component';
import { NoteResolverService } from './note-resolver.service';

const noteRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: ':dataset/notes/:string',
    component: NoteComponent,
    resolve: {
      note: NoteResolverService
    }
  }
]);

@NgModule({
  imports: [
    noteRouting,
    CommonModule,
    ComponentsModule,
    PanelModule,
  ],
  declarations: [
    NoteComponent,
  ],
  providers: [
    NoteResolverService
  ]
})

export class NoteModule {}
