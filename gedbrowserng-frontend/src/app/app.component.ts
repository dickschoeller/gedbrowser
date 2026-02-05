import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
    selector: 'app-root',
    standalone: true,
    template: `<div class="body">
  <router-outlet></router-outlet>
</div>`,
    styles: [],
    imports: [RouterOutlet]
})
export class AppComponent {
  constructor() { }
}
