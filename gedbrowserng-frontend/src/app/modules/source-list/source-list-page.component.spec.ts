import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SourceListPageComponent } from './source-list-page.component';

describe('SourceListPageComponent', () => {
  let component: SourceListPageComponent;
  let fixture: ComponentFixture<SourceListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SourceListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
