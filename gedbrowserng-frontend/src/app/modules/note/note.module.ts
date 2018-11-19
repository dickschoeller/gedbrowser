import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

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

    MatCardModule,
    MatIconModule,
  ],
  declarations: [
    NoteComponent,
  ],
  providers: [
    NoteResolverService
  ]
})

export class NoteModule {}
