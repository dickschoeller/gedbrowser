import { ModuleWithProviders } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WelcomeComponent } from './welcome.component';

const rootRouting: ModuleWithProviders<RouterModule> = RouterModule.forRoot([
  { path: '', component: WelcomeComponent },
], { useHash: true, onSameUrlNavigation: 'reload' });


