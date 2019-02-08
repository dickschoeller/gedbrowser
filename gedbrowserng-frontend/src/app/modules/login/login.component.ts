import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { DisplayMessage } from '../../models/display-message';
import {
    UserService,
    AuthService
} from '../../services';

import { Subject } from 'rxjs';
import { takeUntil, delay } from 'rxjs/operators';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
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
    private ngUnsubscribe: Subject<void> = new Subject<void>();

    constructor(
        private userService: UserService,
        private authService: AuthService,
        private router: Router,
        private route: ActivatedRoute,
        private formBuilder: FormBuilder
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
            .subscribe(
                () => {
                    this.userService.getMyInfo().subscribe();
                    this.router.navigate([this.returnUrl]);
                },
                () => {
                    this.submitted = false;
                    this.notification = {
                        msgType: 'error',
                        msgBody: 'Incorrect username or password.'
                    };
                }
            );
    }
}
