import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';
import { ApiAttribute } from '../../models';

describe('AttributeListItemDetailListItemComponent', () => {
  let component: AttributeListItemDetailListItemComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatButtonModule, AttributeListItemDetailListItemComponent],
    providers: [
        provideNoopAnimations()
    ],
    providers: []
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemDetailListItemComponent);
    component = fixture.componentInstance;
    
    // Set up required inputs
    component.attribute = new ApiAttribute();
    component.attribute.type = 'attribute';
    component.attribute.string = 'test string';
    component.attribute.tail = 'test tail';
    component.index = 0;
    component.length = 1;
    component.dataset = 'test';
    
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

