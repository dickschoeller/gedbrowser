import {TestBed, async} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {RouterTestingModule} from '@angular/router/testing';

import {ButtonModule} from 'primeng/button';
import {TieredMenuModule} from 'primeng/tieredmenu';
import {ToolbarModule} from 'primeng/toolbar';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
      ],
      imports: [
        RouterTestingModule,
        ButtonModule,
        TieredMenuModule,
        ToolbarModule
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'gedbrowserng - schoeller'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('gedbrowserng - schoeller');
  }));
//  it('should render title in a h1 tag', async(() => {
//    const fixture = TestBed.createComponent(AppComponent);
//    fixture.detectChanges();
//    const compiled = fixture.debugElement.nativeElement;
//    expect(compiled.querySelector('h1').textContent).toContain('Welcome to gedbrowserng!');
//  }));
});