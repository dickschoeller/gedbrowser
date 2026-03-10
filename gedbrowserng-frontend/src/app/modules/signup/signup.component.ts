import { Component, OnDestroy, OnInit , Inject } from '@angular/core';
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
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

@Component({
    selector: 'app-signup',
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
          <form [formGroup]="form" (ngSubmit)="onSubmit()" #signupForm="ngForm">
          <mat-form-field>
            <mat-label>Username</mat-label>
            <input matInput formControlName="username" required>
          </mat-form-field>
          <mat-form-field>
            <mat-label>Password</mat-label>
            <input matInput formControlName="password" required type="password">
          </mat-form-field>
          <mat-form-field>
            <mat-label>First Name</mat-label>
            <input matInput formControlName="firstname">
          </mat-form-field>
          <mat-form-field>
            <mat-label>Last Name</mat-label>
            <input matInput formControlName="lastname">
          </mat-form-field>
          <mat-form-field>
            <mat-label>Email</mat-label>
            <input matInput formControlName="email">
          </mat-form-field>
          <button type="submit" [disabled]="!signupForm.form.valid" mat-raised-button color="primary">Sign up</button>
          </form>
        }
        <br>

        @if (submitted) {
          <mat-spinner mode="indeterminate"></mat-spinner>
        }
      </mat-card-content>
    </mat-card>
  </div>
</div>`,
    styleUrls: ['./signup.component.css'],
    imports: [MatCard, MatCardTitle, MatCardSubtitle, MatCardContent, FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatButton, MatProgressSpinner]
})
export class SignupComponent implements OnInit, OnDestroy {
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
        @Inject(AuthService) private readonly authService: AuthService,
        @Inject(UserService) private readonly userService: UserService,
        @Inject(ActivatedRoute) private readonly route: ActivatedRoute,
        @Inject(Router) private readonly router: Router,
        @Inject(FormBuilder) private readonly formBuilder: FormBuilder
    ) {

    }

    ngOnInit() {
        this.route.params.pipe(takeUntil(this.ngUnsubscribe)).subscribe((message: DisplayMessage) => {
            this.notification = message;
        });
        // get return url from route parameters or default to '/'
        this.route.paramMap.subscribe(params => this.returnUrl = params.get('returnUrl') || '/');
        this.initForm();
    }

    private initForm() {
        this.form = this.formBuilder.group({
            username: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(64)])],
            password: ['', Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(32)])],
            firstname: [''],
            lastname: [''],
            email: ['', Validators.compose([Validators.email])]
        });
    }

    ngOnDestroy() {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

    onSubmit() {
        this.notification = undefined;
        this.submitted = true;

        this.authService.signup(this.form.value)
            // show me the animation
            .pipe(delay(1000))
            .subscribe(
                () => {
                    this.authService.login(this.form.value).subscribe(() => {
                        this.userService.getMyInfo().subscribe();
                        this.router.navigate([this.returnUrl]);
                    });
                },
                error => {
                    this.submitted = false;
                    console.log('Sign up error: ' + JSON.stringify(error));
                    this.notification = {
                        msgType: 'error',
                        msgBody: error['error'].errorMessage
                    };
                }
            );
    }
}
