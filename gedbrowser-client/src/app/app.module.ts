import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { PersonListModule } from './person-list/person-list.module';
import { PersonModule } from './person/person.module';
import { SourceListModule } from './source-list/source-list.module';
import { SourceModule } from './source/source.module';
import { SubmitterListModule } from './submitter-list/submitter-list.module';
import { SubmitterModule } from './submitter/submitter.module';
import {
  PersonService,
  SourceService,
  SubmitterService,
  SharedModule } from './shared';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    rootRouting,
    BrowserModule,
    HttpClientModule,
    SharedModule,
    PersonListModule,
    PersonModule,
    SourceListModule,
    SourceModule,
    SubmitterListModule,
    SubmitterModule
  ],
  providers: [
    PersonService,
    SourceService,
    SubmitterService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
