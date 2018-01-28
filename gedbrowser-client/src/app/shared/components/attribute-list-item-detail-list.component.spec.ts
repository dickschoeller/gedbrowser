import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributeListItemDetailListComponent } from './attribute-list-item-detail-list.component';

describe('AttributeListItemDetailListComponent', () => {
  let component: AttributeListItemDetailListComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttributeListItemDetailListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemDetailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
