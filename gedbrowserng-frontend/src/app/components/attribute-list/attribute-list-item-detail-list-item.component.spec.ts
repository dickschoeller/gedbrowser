import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { vi } from 'vitest';

import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';
import { ApiAttribute } from '../../models';

describe('AttributeListItemDetailListItemComponent', () => {
  let component: AttributeListItemDetailListItemComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListItemComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatButtonModule, AttributeListItemDetailListItemComponent],
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

  it('should evaluate last based on index and length', () => {
    component.index = 0;
    component.length = 2;
    expect(component.last()).toBe(false);

    component.index = 1;
    expect(component.last()).toBe(true);
  });

  it('should proxy href to attribute analyzer', () => {
    const hrefSpy = vi.spyOn(component.attributeUtil, 'href').mockReturnValue('/abc');
    expect(component.href()).toBe('/abc');
    expect(hrefSpy).toHaveBeenCalled();
  });

  it('should render bracketed link string for link attributes', () => {
    component.attribute.type = 'sourcelink';
    component.attribute.string = 'S1';
    expect(component.displayString()).toBe('[S1]');
  });

  it('should render string only for date/place attributes', () => {
    component.attribute.type = 'date';
    component.attribute.string = '1900-01-01';
    component.attribute.tail = 'unused';
    expect(component.displayString()).toBe('1900-01-01');
  });

  it('should render tail only for file/note/title labels', () => {
    component.attribute.string = 'File';
    component.attribute.tail = 'photo.jpg';
    expect(component.displayString()).toBe('photo.jpg');
  });

  it('should render type and string when tail is empty', () => {
    component.attribute.type = 'attribute';
    component.attribute.string = 'Status';
    component.attribute.tail = '';
    expect(component.displayString()).toBe('attribute Status');
  });

  it('should render string and tail in default path', () => {
    component.attribute.string = 'Place';
    component.attribute.tail = 'Berlin';
    component.attribute.type = 'attribute';
    expect(component.displayString()).toBe('Place Berlin');
  });

  it('should detect image via ImageUtil', () => {
    component.attribute.tail = 'image.png';
    expect(component.image()).toBe(true);
  });

  it('should evaluate helper predicates', () => {
    component.attribute.type = 'submitterlink';
    expect(component.linkString()).toBe(true);

    component.attribute.type = 'place';
    expect(component.stringOnly()).toBe(true);

    component.attribute.string = 'Title';
    expect(component.tailOnly()).toBe(true);
  });
});

