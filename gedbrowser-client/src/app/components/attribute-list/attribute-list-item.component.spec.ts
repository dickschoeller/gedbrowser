import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributeListItemComponent } from './attribute-list-item.component';

describe('AttributeListItemComponent', () => {
  let component: AttributeListItemComponent;
  let fixture: ComponentFixture<AttributeListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttributeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
