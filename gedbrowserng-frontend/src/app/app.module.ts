import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CdkTableModule } from '@angular/cdk/table';

import { AppComponent } from './app.component';

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
  NewNoteLinkService,
  NewPersonLinkService,
  NewSourceLinkService,
  NewSubmitterLinkService,
  NoteService,
  PersonService,
  SourceService,
  SubmitterService,
  SaveService
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
  ],
  providers: [
    DatasetsService,
    HeadService,
    NewNoteLinkService,
    NewPersonLinkService,
    NewSourceLinkService,
    NewSubmitterLinkService,
    NoteService,
    FamilyService,
    PersonService,
    SourceService,
    SubmitterService,
    SaveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
