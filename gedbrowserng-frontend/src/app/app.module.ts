import { ModuleWithProviders, NgModule, APP_INITIALIZER } from '@angular/core';
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
    LoginModule,
    NoteModule,
    NoteListModule,
    PersonListModule,
    PersonModule,
    SignupModule,
    SourceListModule,
    SourceModule,
    SubmitterListModule,
    SubmitterModule
} from './modules';

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

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });

export function initUserFactory(userService: UserService) {
    return () => userService.initUser();
}

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
        LoginModule,
        NoteModule,
        NoteListModule,
        PersonListModule,
        PersonModule,
        SignupModule,
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
        AuthApiService,
        AuthService,
        ConfigService,
        DatasetsService,
        HeadService,
        NoteService,
        FamilyService,
        FooService,
        PersonService,
        SourceService,
        SubmitterService,
        SaveService,
        UploadService,
        UserService,
        LoginGuard,
        GuestGuard,
        AdminGuard,
        {
            'provide': APP_INITIALIZER,
            'useFactory': initUserFactory,
            'deps': [UserService],
            'multi': true
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
