import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CdkTableModule } from '@angular/cdk/table';

import { AppComponent } from './app.component';
import {
  LinkDialogComponent,
  LinkPersonDialogComponent,
  MultimediaDialogComponent,
  NewAttributeDialogComponent,
  NewNoteDialogComponent,
  NewPersonDialogComponent,
  NewSourceDialogComponent,
  NewSubmitterDialogComponent,
} from './components';

import {
  HeadModule,
  NoteModule,
  NoteListModule,
  PersonListModule,
  PersonModule,
  SourceListModule,
  SourceModule,
  SubmitterListModule,
  SubmitterModule
} from './modules';

import {
  DatasetsService,
  HeadService,
  ServiceBase,
  FamilyService,
  NoteService,
  PersonService,
  SourceService,
  SubmitterService,
  SaveService,
  UploadService,
} from './services';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });

@NgModule({
  imports: [
    rootRouting,

    BrowserModule,
    BrowserAnimationsModule,
    CdkTableModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

    HeadModule,
    NoteModule,
    NoteListModule,
    PersonListModule,
    PersonModule,
    SourceListModule,
    SourceModule,
    SubmitterListModule,
    SubmitterModule,
  ],
  declarations: [
    AppComponent,
  ],
  entryComponents: [
    LinkDialogComponent,
    LinkPersonDialogComponent,
    MultimediaDialogComponent,
    NewAttributeDialogComponent,
    NewNoteDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
    NewSubmitterDialogComponent,
  ],
  providers: [
    DatasetsService,
    HeadService,
    NoteService,
    FamilyService,
    PersonService,
    SourceService,
    SubmitterService,
    SaveService,
    UploadService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
