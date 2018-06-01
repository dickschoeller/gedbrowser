import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkPersonDialogComponent } from './link-person-dialog.component';

describe('LinkPersonDialogComponent', () => {
  let component: LinkPersonDialogComponent;
  let fixture: ComponentFixture<LinkPersonDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinkPersonDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkPersonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
