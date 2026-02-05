import { Component, Input , Inject } from '@angular/core';
import { MainMenuComponent } from '../main-menu/main-menu.component';
import { MatDrawerContainer, MatDrawer, MatDrawerContent } from '@angular/material/sidenav';
import { SideMenuComponent } from '../side-menu/side-menu.component';

@Component({
    selector: 'app-main-layout',
    template: `<app-main-menu [dataset]="dataset" (emitToggle)="drawer.toggle()"></app-main-menu>
<mat-drawer-container>
  <mat-drawer mode="side" opened="true" #drawer>
    <app-side-menu [dataset]="dataset"></app-side-menu>
  </mat-drawer>
  <mat-drawer-content>
    <ng-content></ng-content>
  </mat-drawer-content>
</mat-drawer-container>`,
    styles: [],
    imports: [MainMenuComponent, MatDrawerContainer, MatDrawer, SideMenuComponent, MatDrawerContent]
})
export class MainLayoutComponent {
  @Input() dataset: string;

  constructor() { }
}
