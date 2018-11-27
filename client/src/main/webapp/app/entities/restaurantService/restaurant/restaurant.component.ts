import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { Principal } from 'app/core';
import { RestaurantService } from './restaurant.service';

@Component({
    selector: 'jhi-restaurant',
    templateUrl: './restaurant.component.html'
})
export class RestaurantComponent implements OnInit, OnDestroy {
    restaurants: IRestaurant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private restaurantService: RestaurantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.restaurantService.query().subscribe(
            (res: HttpResponse<IRestaurant[]>) => {
                this.restaurants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRestaurants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRestaurant) {
        return item.id;
    }

    registerChangeInRestaurants() {
        this.eventSubscriber = this.eventManager.subscribe('restaurantListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
