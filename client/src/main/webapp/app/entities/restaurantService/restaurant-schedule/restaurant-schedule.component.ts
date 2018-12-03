import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';
import { Principal } from 'app/core';
import { RestaurantScheduleService } from './restaurant-schedule.service';

@Component({
    selector: 'jhi-restaurant-schedule',
    templateUrl: './restaurant-schedule.component.html'
})
export class RestaurantScheduleComponent implements OnInit, OnDestroy {
    restaurantSchedules: IRestaurantSchedule[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private restaurantScheduleService: RestaurantScheduleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.restaurantScheduleService.query().subscribe(
            (res: HttpResponse<IRestaurantSchedule[]>) => {
                this.restaurantSchedules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRestaurantSchedules();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRestaurantSchedule) {
        return item.id;
    }

    registerChangeInRestaurantSchedules() {
        this.eventSubscriber = this.eventManager.subscribe('restaurantScheduleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
