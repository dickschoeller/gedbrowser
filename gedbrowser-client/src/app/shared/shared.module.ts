import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import {
  AttributeListItemComponent,
  AttributeListComponent,
  AttributeListItemDetailListComponent,
} from './components';
import { AttributeListItemDetailListItemComponent } from './components/attribute-list-item-detail-list-item.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
  ],
  declarations: [
    AttributeListItemComponent,
    AttributeListComponent,
    AttributeListItemDetailListComponent,
    AttributeListItemDetailListItemComponent,
  ],
  exports: [
    AttributeListItemComponent,
    AttributeListComponent,
    AttributeListItemDetailListComponent,
  ]
})
export class SharedModule {}
