import { CommonModule } from '@angular/common';
import { NgModule, ModuleWithProviders } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';

import { GuestGuard } from '../../guards';
import { SignupComponent } from './signup.component';

const signupRouting: ModuleWithProviders<RouterModule> = RouterModule.forChild([
    {
        path: 'signup',
        component: SignupComponent,
        canActivate: [GuestGuard],
        pathMatch: 'full',
    }
]);

@NgModule({
    declarations: [
        SignupComponent
    ],
    imports: [
        signupRouting,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatProgressSpinnerModule,
        MatTooltipModule,
    ]
})
export class SignupModule { }
