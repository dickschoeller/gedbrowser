import { Component } from '@angular/core';

import { MainLayoutComponent } from './components/main-layout/main-layout.component';

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [MainLayoutComponent],
  template: `
    <app-main-layout [dataset]="'default'">
      <div style="padding:16px">
        <h1>gedbrowserng</h1>
        <p>The UI has loaded successfully.</p>
        <p>Use the side menu to navigate (if available) or open a dataset URL (e.g. #/SAMPLE/persons).</p>
      </div>
    </app-main-layout>
  `
})
export class WelcomeComponent {}