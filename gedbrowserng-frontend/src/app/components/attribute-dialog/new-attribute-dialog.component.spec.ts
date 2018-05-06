import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewAttributeDialogComponent } from './new-attribute-dialog.component';

describe('NewAttributeDialogComponent', () => {
  let component: NewAttributeDialogComponent;
  let fixture: ComponentFixture<NewAttributeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewAttributeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAttributeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
