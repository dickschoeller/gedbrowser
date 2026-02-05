import {TestBed} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {RouterTestingModule} from '@angular/router/testing';
import {ButtonModule} from 'primeng/button';
import {TieredMenuModule} from 'primeng/tieredmenu';
import {ToolbarModule} from 'primeng/toolbar';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('AppComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [
        RouterTestingModule,
        ButtonModule,
        TieredMenuModule,
        ToolbarModule,
        AppComponent
    ],
    schemas: [NO_ERRORS_SCHEMA],
    providers: []
});
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });
});
