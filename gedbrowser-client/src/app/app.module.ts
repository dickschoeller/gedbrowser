import {ModuleWithProviders, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatStepperModule,
} from '@angular/material';
import {CdkTableModule} from '@angular/cdk/table';

import {AppComponent} from './app.component';
import {NewPersonDialogComponent} from './new-person-dialog';
import {PersonListModule} from './person-list/person-list.module';
import {PersonModule} from './person/person.module';
import {SourceListModule} from './source-list/source-list.module';
import {SourceModule} from './source/source.module';
import {SubmitterListModule} from './submitter-list/submitter-list.module';
import {SubmitterModule} from './submitter/submitter.module';
import {
  ChildService,
  FamilyService,
  ParentService,
  PersonService,
  SourceService,
  SpouseService,
  SubmitterService,
  SaveService,
  SharedModule
} from './shared';

@NgModule({
  exports: [
    CdkTableModule,
    MatAutocompleteModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatStepperModule,
    MatDatepickerModule,
    MatDialogModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
  ],
})
export class AppMaterialModule {}

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });

@NgModule({
  imports: [
    AppMaterialModule,
    rootRouting,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    SharedModule,
    PersonListModule,
    PersonModule,
    SourceListModule,
    SourceModule,
    SubmitterListModule,
    SubmitterModule
  ],
  declarations: [
    AppComponent,
    NewPersonDialogComponent,
  ],
  entryComponents: [
    NewPersonDialogComponent,
  ],
  providers: [
    ChildService,
    FamilyService,
    ParentService,
    PersonService,
    SpouseService,
    SourceService,
    SubmitterService,
    SaveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
