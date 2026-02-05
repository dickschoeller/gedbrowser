import { enableProdMode, APP_INITIALIZER, ModuleWithProviders, importProvidersFrom } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { initUserFactory } from './app/app.module';
import { environment } from './environments/environment';
import { AuthApiService, AuthService, ConfigService, DatasetsService, HeadService, NoteService, FamilyService, FooService, PersonService, SourceService, SubmitterService, SaveService, UploadService, UserService } from './app/services';
import { LoginGuard, GuestGuard, AdminGuard } from './app/guards';
import { RouterModule } from '@angular/router';
import { WelcomeComponent } from './app/welcome.component';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HeadModule, NoteModule, NoteListModule, PersonListModule, PersonModule, SourceListModule, SourceModule, SubmitterListModule, SubmitterModule } from './app/modules';
import { AppComponent } from './app/app.component';

const rootRouting: ModuleWithProviders<RouterModule> = RouterModule.forRoot([
  { path: '', component: WelcomeComponent },
], { useHash: true });



if (environment.production) {
//  enableProdMode();
}

bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(rootRouting, BrowserModule, BrowserAnimationsModule, CdkTableModule, FormsModule, ReactiveFormsModule, HttpClientModule, HeadModule, NoteModule, NoteListModule, PersonListModule, PersonModule, SourceListModule, SourceModule, SubmitterListModule, SubmitterModule),
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
    ]
})
  .catch(err => console.log(err));
