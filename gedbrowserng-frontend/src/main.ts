import { APP_INITIALIZER, ModuleWithProviders, importProvidersFrom } from '@angular/core';

import { initUserFactory } from './app/app.module';
import { environment } from './environments/environment';
import { AuthApiService, AuthService, ConfigService, DatasetsService, HeadService, NoteService, FamilyService, FooService, PersonService, SourceService, SubmitterService, SaveService, UploadService, UserService } from './app/services';
import { LoginGuard, GuestGuard, AdminGuard } from './app/guards';
import { RouterModule } from '@angular/router';
import { WelcomeComponent } from './app/welcome.component';
import { LoginComponent } from './app/modules/login/login.component';
import { SignupComponent } from './app/modules/signup/signup.component';
import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeadModule, NoteModule, NoteListModule, PersonListModule, PersonModule, SourceListModule, SourceModule, SubmitterListModule, SubmitterModule } from './app/modules';
import { AppComponent } from './app/app.component';

const rootRouting: ModuleWithProviders<RouterModule> = RouterModule.forRoot([
  { path: '', component: WelcomeComponent },
  { path: 'login', component: LoginComponent, canActivate: [GuestGuard] },
  { path: 'signup', component: SignupComponent, canActivate: [GuestGuard] },
], { useHash: true });


if (environment.production) {
//  Production mode is disabled for now
}

async function bootstrap() {
  try {
    await bootstrapApplication(AppComponent, {
      providers: [
        provideHttpClient(),
        importProvidersFrom(rootRouting, BrowserModule, CdkTableModule, FormsModule, ReactiveFormsModule, HeadModule, NoteModule, NoteListModule, PersonListModule, PersonModule, SourceListModule, SourceModule, SubmitterListModule, SubmitterModule),
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
    });
  } catch (err) {
    console.log(err);
  }
}

void bootstrap();
