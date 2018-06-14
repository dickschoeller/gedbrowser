import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkPersonComponent } from './link-person.component';

describe('LinkPersonComponent', () => {
  let component: LinkPersonComponent;
  let fixture: ComponentFixture<LinkPersonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LinkPersonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
