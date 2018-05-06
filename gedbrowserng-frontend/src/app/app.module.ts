import {ModuleWithProviders, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CdkTableModule} from '@angular/cdk/table';

import {ToolbarModule} from 'primeng/toolbar';
import {ButtonModule} from 'primeng/button';
import {SplitButtonModule} from 'primeng/splitbutton';
import {PanelModule} from 'primeng/panel';
import {AccordionModule} from 'primeng/accordion';
import {MenuModule} from 'primeng/menu';
import {TieredMenuModule} from 'primeng/tieredmenu';
import {OrderListModule} from 'primeng/orderlist';
import {TooltipModule} from 'primeng/tooltip';

import {NgxGalleryModule} from 'ngx-gallery';
import {Ng2PageScrollModule} from 'ng2-page-scroll';

import {AppComponent} from './app.component';

import {
  PersonListModule,
//   PersonModule,
  SourceListModule,
  SourceModule,
  SubmitterListModule,
//   SubmitterModule
} from './modules';

// import {ComponentsModule} from './components';

import {
  ServiceBase,
  FamilyService,
  NewPersonLinkService,
  PersonService,
  SourceService,
  SubmitterService,
  SaveService
} from './services';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([], { useHash: true });

@NgModule({
  imports: [
    rootRouting,

    Ng2PageScrollModule,
    NgxGalleryModule,

    BrowserModule,
    BrowserAnimationsModule,
    CdkTableModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,

    ToolbarModule,
    ButtonModule,
    SplitButtonModule,
    PanelModule,
    AccordionModule,
    MenuModule,
    OrderListModule,
    TieredMenuModule,
    TooltipModule,

    PersonListModule,
//     PersonModule,
    SourceListModule,
    SourceModule,
    SubmitterListModule,
//     SubmitterModule,
  ],
  declarations: [
    AppComponent,
  ],
  entryComponents: [
  ],
  providers: [
    NewPersonLinkService,
    FamilyService,
    PersonService,
    SourceService,
    SubmitterService,
    SaveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
