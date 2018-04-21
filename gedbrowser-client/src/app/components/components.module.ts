import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {OrderListModule} from 'primeng/orderlist';
import {ButtonModule} from 'primeng/button';
import {TooltipModule} from 'primeng/tooltip';
import {InputTextModule} from 'primeng/inputtext';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {DropdownModule} from 'primeng/dropdown';
import {DialogModule} from 'primeng/dialog';
import {PanelModule} from 'primeng/panel';

import {MatButtonModule, MatDialogModule, MatSelectModule} from '@angular/material';

import {
  AttributeListComponent,
  AttributeListItemComponent,
  AttributeListItemDetailListComponent,
  AttributeListItemDetailListItemComponent,
} from './attribute-list';

import {AttributeDialogComponent} from './attribute-dialog';

import {NewPersonDialogComponent} from './new-person-dialog';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    MatDialogModule,
    MatSelectModule,
    MatButtonModule,

    OrderListModule,
    ButtonModule,
    TooltipModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    DialogModule,
    PanelModule,
  ],
  declarations: [
    AttributeDialogComponent,
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    NewPersonDialogComponent,
  ],
  entryComponents: [
    AttributeDialogComponent,
    NewPersonDialogComponent,
  ],
  exports: [
    AttributeDialogComponent,
    AttributeListComponent,
    AttributeListItemComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
    NewPersonDialogComponent,
  ]
})

export class ComponentsModule {}
