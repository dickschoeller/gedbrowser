import { CommonModule } from '@angular/common';
import { NgModule, ModuleWithProviders } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
    MatProgressSpinnerModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatTooltipModule,
    MatButtonModule,
} from '@angular/material';

import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { GuestGuard } from '../../guards';

const loginRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: 'login',
        component: LoginComponent,
        canActivate: [GuestGuard]
    }
]);

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        loginRouting,
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
export class LoginModule { }
