import { Component, OnInit, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import * as firebase from 'firebase';

import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared';
import { LoginModalService } from 'app/core';
import { Register } from './register.service';
import { RESTAURANT_EXECUTIVE_AUTHORITY, USER_AUTHORITY } from '../../shared/constants/input.constants';
import { environment } from 'app/shared/environment/environment';

@Component({
    selector: 'jhi-register',
    templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit, AfterViewInit {
    confirmPassword: string;
    doNotMatch: string;
    error: string;
    errorEmailExists: string;
    errorUserExists: string;
    registerAccount: any;
    success: boolean;
    modalRef: NgbModalRef;
    windowRef: any;
    otpSent: boolean;
    matchOtp: boolean = false;
    wrongOtp: boolean = false;
    oneTimeOtp: boolean = false;
    contactUsed: boolean = false;
    confirmationResult: any;
    verificationCode: string;
    recaptchaVerifier: firebase.auth.RecaptchaVerifier;

    constructor(
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService,
        private registerService: Register,
        private elementRef: ElementRef,
        private renderer: Renderer
    ) {}

    ngOnInit() {
        this.success = false;
        this.registerAccount = {};
        firebase.initializeApp(environment.firebase);
        if (!firebase.apps.length) {
            firebase.initializeApp(environment.firebase);
        }
    }

    ngAfterViewInit() {
        this.recaptchaVerifier = new firebase.auth.RecaptchaVerifier('recaptcha-container');
        this.recaptchaVerifier.render();
        this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#login'), 'focus', []);
    }

    register() {
        this.otpSent = false;
        if (this.registerAccount.password !== this.confirmPassword) {
            this.doNotMatch = 'ERROR';
        } else {
            this.doNotMatch = null;
            this.error = null;
            this.errorUserExists = null;
            this.errorEmailExists = null;
            this.languageService.getCurrent().then(key => {
                this.registerAccount.langKey = key;
                this.registerAccount.authority = USER_AUTHORITY;
                this.registerService.save(this.registerAccount).subscribe(
                    () => {
                        this.success = true;
                    },
                    response => this.processError(response)
                );
            });
        }
    }

    openLogin() {
        this.modalRef = this.loginModalService.open();
    }

    private processError(response: HttpErrorResponse) {
        this.success = null;
        if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
            this.errorUserExists = 'ERROR';
        } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
            this.errorEmailExists = 'ERROR';
        } else {
            this.error = 'ERROR';
        }
    }

    keyPress(event: any) {
        const pattern = /[0-9\+\-\ ]/;

        let inputChar = String.fromCharCode(event.charCode);
        if (event.keyCode != 8 && !pattern.test(inputChar)) {
            event.preventDefault();
        }
    }

    get windowRefObject() {
        return window;
    }

    sendLoginCode() {
        this.confirmationResult = false;
        this.contactUsed = false;
        var country = '+91';
        this.getNumber(
            value => {
                console.log(value);
                let num = country + this.registerAccount.mobileNumber;
                this.firebaseAuth(num);
            },
            error1 => {
                this.contactUsed = true;
                console.log(error1);
            }
        );
    }
    firebaseAuth(num) {
        firebase
            .auth()
            .signInWithPhoneNumber(num, this.recaptchaVerifier)
            .then(confirmationResult => {
                this.confirmationResult = confirmationResult;
                this.otpSent = true;
                this.oneTimeOtp = true;

                console.log(confirmationResult);
            })
            .catch(error => console.log(error));
    }

    verifyLoginCode() {
        this.wrongOtp = false;
        this.oneTimeOtp = false;

        this.confirmationResult
            .confirm(this.verificationCode)
            .then(result => {
                this.matchOtp = true;
                console.log(result);
            })
            .catch(error => {
                this.matchOtp = false;
                this.wrongOtp = true;
                console.log(error, 'Incorrect code entered?');
            });
    }

    getNumber(successcall, errorcall) {
        this.registerService.checkContact(this.registerAccount.mobileNumber).subscribe(value => successcall(value), err => errorcall(err));
    }
}
