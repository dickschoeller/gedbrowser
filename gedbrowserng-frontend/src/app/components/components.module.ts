import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';

import { MatButtonModule } from '@angular/material';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';

import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputTextModule } from 'primeng/inputtext';
import { ListboxModule } from 'primeng/listbox';
import { OrderListModule } from 'primeng/orderlist';

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
import { SideMenuComponent } from './side-menu';
import { MainLayoutComponent } from './main-layout/main-layout.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    BrowserModule,

    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTooltipModule,

    ButtonModule,
    DialogModule,
    DropdownModule,
    InputTextareaModule,
    InputTextModule,
    ListboxModule,
    OrderListModule,

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
    SideMenuComponent,
    MainLayoutComponent,
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
    SideMenuComponent,
    MainLayoutComponent,
  ]
})

export class ComponentsModule {}
