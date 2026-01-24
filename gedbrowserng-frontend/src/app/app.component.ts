import { Component } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-root',
  template: `<div class="body">
  <router-outlet></router-outlet>
</div>`,
    styles: []
})
export class AppComponent {
  constructor() { }
}
