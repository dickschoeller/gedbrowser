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
import {OrderListModule} from 'primeng/orderlist';
import {PanelModule} from 'primeng/panel';
import {TooltipModule} from 'primeng/tooltip';

import {
  AttributeListComponent,
  AttributeListItemComponent,
  AttributeListItemDetailListComponent,
  AttributeListItemDetailListItemComponent,
} from './attribute-list';
import {BaseDialogComponent} from './base-dialog/base-dialog.component';
import {NewPersonDialog2Component} from './new-person-dialog';
import {NewAttributeDialogComponent} from './attribute-dialog';
import {NewSourceDialogComponent} from './new-source-dialog/new-source-dialog.component';

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
    OrderListModule,
    PanelModule,
    TooltipModule,
  ],
  declarations: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    NewAttributeDialogComponent,
    NewPersonDialog2Component,
    NewSourceDialogComponent,
    BaseDialogComponent,
  ],
  exports: [
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    NewAttributeDialogComponent,
    NewPersonDialog2Component,
    NewSourceDialogComponent,
  ]
})

export class ComponentsModule {}
