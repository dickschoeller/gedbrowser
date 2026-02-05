import { ModuleWithProviders, NgModule, APP_INITIALIZER } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CdkTableModule } from '@angular/cdk/table';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome.component';

const rootRouting: ModuleWithProviders<RouterModule> = RouterModule.forRoot([
  { path: '', component: WelcomeComponent },
], { useHash: true });

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
    AuthApiService,
    AuthService,
    ConfigService,
    DatasetsService,
    HeadService,
    FamilyService,
    FooService,
    NoteService,
    PersonService,
    SourceService,
    SubmitterService,
    SaveService,
    UploadService,
    UserService,
} from './services';

import { LoginGuard, GuestGuard, AdminGuard } from './guards';

export function initUserFactory(userService: UserService) {
    return () => userService.initUser();
}


