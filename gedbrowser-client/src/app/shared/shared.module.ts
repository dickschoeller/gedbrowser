import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { AttributeComponent } from './components/attribute.component';
import { AttributeListComponent } from './components/attribute-list.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
  ],
  declarations: [
    AttributeComponent,
    AttributeListComponent
  ],
  exports: [
    AttributeComponent,
    AttributeListComponent
  ]
})
export class SharedModule {}
