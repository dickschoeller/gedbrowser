import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkSourceDialogComponent } from './link-source-dialog.component';

describe('LinkSourceDialogComponent', () => {
  let component: LinkSourceDialogComponent;
  let fixture: ComponentFixture<LinkSourceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinkSourceDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkSourceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
