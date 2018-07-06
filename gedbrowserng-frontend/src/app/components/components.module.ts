import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

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
import { TooltipModule } from 'primeng/tooltip';

import {
  AttributeListComponent,
  AttributeListItemComponent,
  AttributeListItemDetailListComponent,
  AttributeListItemDetailListItemComponent,
} from './attribute-list';
import { LinkDialogComponent } from './link-dialog';
import { LinkPersonDialogComponent } from './link-person-dialog';
import { NewAttributeDialogComponent } from './attribute-dialog';
import { NewPersonDialogComponent } from './new-person-dialog';
import { NewSourceDialogComponent } from './new-source-dialog';
import { NewSubmitterDialogComponent } from './new-submitter-dialog';
import { SourcesComponent } from './sources';
import { SubmittersComponent } from './submitters/submitters.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,

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
    TooltipModule,
  ],
  declarations: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    LinkDialogComponent,
    LinkPersonDialogComponent,
    NewAttributeDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
    NewSubmitterDialogComponent,
    SourcesComponent,
    SubmittersComponent,
  ],
  exports: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    LinkDialogComponent,
    LinkPersonDialogComponent,
    NewAttributeDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
    NewSubmitterDialogComponent,
    SourcesComponent,
    SubmittersComponent,
  ]
})

export class ComponentsModule {}
