import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiLanguageService } from 'ng-jhipster';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from '../profiles/profile.service';
import { RestaurantService } from '../../entities/restaurantService/restaurant/restaurant.service';
import { HttpResponse } from '@angular/common/http';
import { IRestaurant } from '../../shared/model/restaurantService/restaurant.model';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.css']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    restuarantId: string;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private loginService: LoginService,
        private eventManager: JhiEventManager,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private restaurantService: RestaurantService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }
    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        this.eventManager.subscribe('authenticationSuccess', () => {
            this.loadRestaurantDetails();
        });
    }

    changeLanguage(languageKey: string) {
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }

    loadRestaurantDetails() {
        this.principal.hasAuthority('ROLE_USER').then(value => {
            this.router.navigate(['restaurant']);
        });
        this.principal.hasAnyAuthority(['ROLE_RESTAURANT_EXECUTIVE']).then(value => {
            if (value) {
                this.restaurantService.findByLogin().subscribe(
                    (res: HttpResponse<IRestaurant>) => {
                        this.restuarantId = res.body.id;
                        this.router.navigate(['restaurant', this.restuarantId, 'view']);
                    },
                    error => {
                        this.router.navigate(['restaurant/new']);
                    }
                );
            }
        });
    }
}
