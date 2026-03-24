import { Component, OnInit, OnDestroy , Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil, delay } from 'rxjs/operators';

import { DisplayMessage } from '../../models';
import {
    UserService,
    AuthService
} from '../../services';
import { MatCard, MatCardTitle, MatCardSubtitle, MatCardContent } from '@angular/material/card';
import { MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

@Component({
    selector: 'app-login',
    standalone: true,
    template: `<div class="content">
  <div>
    <mat-card elevation="5">
      <mat-card-title>{{ title }}</mat-card-title>
      <mat-card-subtitle>gedbrowserng</mat-card-subtitle>

            <mat-card-content>
                @if (notification) {
                    <p [class]="notification.msgType">{{ notification.msgBody }}</p>
                }

                @if (!submitted) {
                    <form [formGroup]="form" (ngSubmit)="onSubmit()" #loginForm="ngForm">
          <mat-form-field>
            <input matInput formControlName="username" required placeholder="username">
          </mat-form-field>
          <mat-form-field>
            <input matInput formControlName="password" required type="password" placeholder="password">
          </mat-form-field>
                    <button type="submit" [disabled]="!loginForm.form.valid" mat-raised-button color="primary">Login</button>
                    </form>
                }
        <br/>

                @if (submitted) {
                    <mat-spinner mode="indeterminate"></mat-spinner>
                }
      </mat-card-content>
    </mat-card>
  </div>
</div>`,
        styles: [`
:host {
    display: block;
    min-height: 100vh;
}

.content {
    width: 100%;
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 16px;
    box-sizing: border-box;
}

mat-card {
    width: min(100%, 400px);
    max-width: 400px;
    text-align: center;
    animation: fadein 1s;
    -o-animation: fadein 1s;
    -moz-animation: fadein 1s;
    -webkit-animation: fadein 1s;

}

form {
    display: flex;
    flex-direction: column;
    align-items: stretch;
    gap: 8px;
}

mat-form-field {
    display: block !important;
    width: 100%;
}

.mat-mdc-form-field {
    display: block !important;
    width: 100%;
}

mat-input-container {
    display: block;
}

mat-spinner {
    width: 25px;
    height: 25px;
    margin: 20px auto 0 auto;
}

button {
    display: block;
    width: 100%;
}

.error {
    color: #D50000;
}

.success {
    color: #8BC34A;
}


@media screen and (max-width: 599px) {

    .content {
        min-height: 100vh;
        display: flex !important;
    }

    mat-card {
        display: block !important;
        max-width: 999px;
    }

}

a {
    text-decoration: none;
    cursor: auto;
    color: #FFFFFF;
}
`],
    imports: [MatCard, MatCardTitle, MatCardSubtitle, MatCardContent, FormsModule, ReactiveFormsModule, MatFormField, MatInput, MatButton, MatProgressSpinner]
})
export class LoginComponent implements OnInit, OnDestroy {
    title = 'Login';
    form: FormGroup;

    /**
     * Boolean used in telling the UI
     * that the form has been submitted
     * and is awaiting a response
     */
    submitted = false;

    /**
     * Notification message from received
     * form request or router
     */
    notification: DisplayMessage;

    returnUrl: string;
    private readonly ngUnsubscribe: Subject<void> = new Subject<void>();

    constructor(
        @Inject(UserService) private readonly userService: UserService,
        @Inject(AuthService) private readonly authService: AuthService,
        @Inject(Router) private readonly router: Router,
        @Inject(ActivatedRoute) private readonly route: ActivatedRoute,
        @Inject(FormBuilder) private readonly formBuilder: FormBuilder
    ) {

    }

    ngOnInit() {
        this.route.params.pipe(takeUntil(this.ngUnsubscribe))
            .subscribe((params: DisplayMessage) => {
                this.notification = params;
            });
        // get return url from route parameters or default to '/'
        this.route.paramMap.subscribe(params => this.returnUrl = params.get('returnUrl') || '/');
        this.form = this.formBuilder.group({
            username: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
            password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])]
        });
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

    onResetCredentials() {
        this.userService.resetCredentials()
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(res => {
                if (res.result === 'success') {
                    alert('Password has been reset to 123 for all accounts');
                } else {
                    alert('Server error');
                }
            });
    }

    onSubmit() {
        /**
         * Innocent until proven guilty
         */
        this.notification = undefined;
        this.submitted = true;

        this.authService.login(this.form.value)
            // show me the animation
            .pipe(delay(1000))
            .subscribe({
                next: () => {
                    this.userService.getMyInfo().subscribe();
                    this.router.navigate([this.returnUrl]);
                },
                error: () => {
                    this.submitted = false;
                    this.notification = {
                        msgType: 'error',
                        msgBody: 'Incorrect username or password.'
                    };
                }
            });
    }
}
