import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';

import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {InputTextModule} from 'primeng/inputtext';
import {ListboxModule} from 'primeng/listbox';
import {MenuModule} from 'primeng/menu';
import {OrderListModule} from 'primeng/orderlist';
import {PanelModule} from 'primeng/panel';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {SplitButtonModule} from 'primeng/splitbutton';
import {TooltipModule} from 'primeng/tooltip';

import {
  AttributeListComponent,
  AttributeListItemComponent,
  AttributeListItemDetailListComponent,
  AttributeListItemDetailListItemComponent,
} from './attribute-list';
import {NewPersonDialogComponent} from './new-person-dialog';
import {NewAttributeDialogComponent} from './attribute-dialog';
import {NewSourceDialogComponent} from './new-source-dialog';
import {LinkSourceDialogComponent} from './link-source-dialog';

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
    LinkSourceDialogComponent,
    NewAttributeDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
  ],
  exports: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    LinkSourceDialogComponent,
    NewAttributeDialogComponent,
    NewPersonDialogComponent,
    NewSourceDialogComponent,
  ]
})

export class ComponentsModule {}
