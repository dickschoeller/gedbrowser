import { NO_ERRORS_SCHEMA } from '@angular/core';
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';

describe('AttributeListItemDetailListItemComponent', () => {
  let component: AttributeListItemDetailListItemComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListItemComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ AttributeListItemDetailListItemComponent ],
      imports: [ MatButtonModule, NoopAnimationsModule ],
      providers: []
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
