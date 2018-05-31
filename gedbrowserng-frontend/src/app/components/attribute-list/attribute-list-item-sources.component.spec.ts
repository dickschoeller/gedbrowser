import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AttributeListItemSourcesComponent } from './attribute-list-item-sources.component';

describe('AttributeListItemSourcesComponent', () => {
  let component: AttributeListItemSourcesComponent;
  let fixture: ComponentFixture<AttributeListItemSourcesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AttributeListItemSourcesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AttributeListItemSourcesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
