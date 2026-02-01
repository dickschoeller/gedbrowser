import { Component, OnDestroy, OnInit , Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil, delay } from 'rxjs/operators';

import { DisplayMessage } from '../../models';
import {
    UserService,
    AuthService
} from '../../services';

@Component({
  standalone: false,
  selector: 'app-signup',
  template: `<div class="content">
  <div>
    <mat-card elevation="5">
      <mat-card-title>{{ title }}</mat-card-title>
      <mat-card-subtitle>gedbrowserng</mat-card-subtitle>

      <mat-card-content>
        <p [class]="notification.msgType" *ngIf="notification">{{ notification.msgBody }}</p>

        <form *ngIf="!submitted" [formGroup]="form" (ngSubmit)="onSubmit()" #signupForm="ngForm">
          <mat-form-field>
            <label>Username: <input matInput formControlName="username" required></label>
          </mat-form-field>
          <mat-form-field>
            <label>Password: <input matInput formControlName="password" required type="password"></label>
          </mat-form-field>
          <mat-form-field>
            <label>First Name: <input matInput formControlName="firstname"></label>
          </mat-form-field>
          <mat-form-field>
            <label>Last Name: <input matInput formControlName="lastname"></label>
          </mat-form-field>
          <mat-form-field>
            <label>Email: <input matInput formControlName="email"></label>
          </mat-form-field>
          <button type="submit" [disabled]="!signupForm.form.valid" mat-raised-button color="primary">Sign up</button>
        </form>
        <br>

        <mat-spinner *ngIf="submitted" mode="indeterminate"></mat-spinner>
      </mat-card-content>
    </mat-card>
  </div>
</div>`,
    styles: []
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
    private ngUnsubscribe: Subject<void> = new Subject<void>();

    constructor(
        @Inject(AuthService) private authService: AuthService,
        @Inject(UserService) private userService: UserService,
        @Inject(ActivatedRoute) private route: ActivatedRoute,
        @Inject(Router) private router: Router,
        @Inject(FormBuilder) private formBuilder: FormBuilder
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
