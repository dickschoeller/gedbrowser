import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';

describe('AttributeListItemDetailListItemComponent', () => {
  let component: AttributeListItemDetailListItemComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttributeListItemDetailListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemDetailListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
