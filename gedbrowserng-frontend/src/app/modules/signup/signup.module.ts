import { CommonModule } from '@angular/common';
import { NgModule, ModuleWithProviders } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
} from '@angular/material';
import { RouterModule } from '@angular/router';

import { GuestGuard } from '../../guards';
import { SignupComponent } from './signup.component';

const signupRouting: ModuleWithProviders = RouterModule.forChild([
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
        FlexLayoutModule,
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
