import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { AttributeListItemDetailListComponent } from './attribute-list-item-detail-list.component';

describe('AttributeListItemDetailListComponent', () => {
  let component: AttributeListItemDetailListComponent;
  let fixture: ComponentFixture<AttributeListItemDetailListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatButtonModule, AttributeListItemDetailListComponent],
    providers: [
        provideNoopAnimations()
    ],
    providers: []
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemDetailListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
