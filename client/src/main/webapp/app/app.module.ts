import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { FoodClientSharedModule } from 'app/shared';
import { FoodClientCoreModule } from 'app/core';
import { FoodClientAppRoutingModule } from './app-routing.module';
import { FoodClientHomeModule } from './home/home.module';
import { FoodClientAccountModule } from './account/account.module';
import { FoodClientEntityModule } from './entities/entity.module';
import * as moment from 'moment';
import { FoodClientAppZomatoRestaurantsModule } from './zomato-restaurants/zomato-restaurants.module';
import { FoodClientAppConfirmRestaurantModule } from './confirm-restaurant/confirm-restaurant.module';
import { AngularFireModule } from 'angularfire2';
import { AngularFireAuthModule } from 'angularfire2/auth';
import { environment } from 'app/shared/environment/environment';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';
import { NgxSpinnerModule } from 'ngx-spinner';

@NgModule({
    imports: [
        BrowserModule,
        FoodClientAppRoutingModule,
        NgxSpinnerModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        FoodClientSharedModule,
        FoodClientCoreModule,
        AngularFireAuthModule,
        FoodClientHomeModule,
        FoodClientAccountModule,
        FoodClientEntityModule,
        FoodClientAppZomatoRestaurantsModule,
        FoodClientAppConfirmRestaurantModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [Injector]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [JhiEventManager]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [Injector]
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class FoodClientAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
