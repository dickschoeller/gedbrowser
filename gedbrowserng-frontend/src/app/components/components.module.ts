import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';

import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputTextModule } from 'primeng/inputtext';
import { ListboxModule } from 'primeng/listbox';
import { MenuModule } from 'primeng/menu';
import { OrderListModule } from 'primeng/orderlist';
import { PanelModule } from 'primeng/panel';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { SplitButtonModule } from 'primeng/splitbutton';
import { TieredMenuModule } from 'primeng/tieredmenu';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';


import { NgxGalleryModule } from 'ngx-gallery';

import {
  AttributeListComponent,
  AttributeListItemComponent,
  AttributeListItemDetailListComponent,
  AttributeListItemDetailListItemComponent,
} from './attribute-list';
import { LinkDialogComponent } from './link-dialog';
import { LinkPersonDialogComponent } from './link-person-dialog';
import { MainMenuComponent } from './main-menu';
import { MultimediaAddButtonComponent } from './multimedia-add-button';
import { MultimediaDialogComponent } from './multimedia-dialog';
import { MultimediaEditButtonComponent } from './multimedia-edit-button';
import { MultimediaGalleryComponent } from './multimedia-gallery';
import { NewAttributeDialogComponent } from './attribute-dialog';
import { NewNoteDialogComponent } from './new-note-dialog';
import { NewPersonDialogComponent } from './new-person-dialog';
import { NewSourceDialogComponent } from './new-source-dialog';
import { NewSubmitterDialogComponent } from './new-submitter-dialog';
import { NoteButtonComponent } from './note-button';
import { SourceButtonComponent } from './source-button';
import { SubmitterButtonComponent } from './submitter-button';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    BrowserModule,

    AccordionModule,
    ButtonModule,
    DialogModule,
    DropdownModule,
    InputTextareaModule,
    InputTextModule,
    ListboxModule,
    MenuModule,
    OrderListModule,
    PanelModule,
    ScrollPanelModule,
    SplitButtonModule,
    TieredMenuModule,
    ToolbarModule,
    TooltipModule,

    NgxGalleryModule
  ],
  declarations: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    LinkDialogComponent,
    LinkPersonDialogComponent,
    MainMenuComponent,
    MultimediaAddButtonComponent,
    MultimediaDialogComponent,
    MultimediaEditButtonComponent,
    MultimediaGalleryComponent,
    NewAttributeDialogComponent,
    NewNoteDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
    NewSubmitterDialogComponent,
    NoteButtonComponent,
    SourceButtonComponent,
    SubmitterButtonComponent,
  ],
  exports: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    LinkDialogComponent,
    LinkPersonDialogComponent,
    MainMenuComponent,
    MultimediaAddButtonComponent,
    MultimediaDialogComponent,
    MultimediaEditButtonComponent,
    MultimediaGalleryComponent,
    NewAttributeDialogComponent,
    NewNoteDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
    NewSubmitterDialogComponent,
    NoteButtonComponent,
    SourceButtonComponent,
    SubmitterButtonComponent,
  ]
})

export class ComponentsModule {}
