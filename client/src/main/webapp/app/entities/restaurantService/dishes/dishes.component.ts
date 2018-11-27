import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDishes } from 'app/shared/model/restaurantService/dishes.model';
import { Principal } from 'app/core';
import { DishesService } from './dishes.service';

@Component({
    selector: 'jhi-dishes',
    templateUrl: './dishes.component.html'
})
export class DishesComponent implements OnInit, OnDestroy {
    dishes: IDishes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dishesService: DishesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dishesService.query().subscribe(
            (res: HttpResponse<IDishes[]>) => {
                this.dishes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDishes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDishes) {
        return item.id;
    }

    registerChangeInDishes() {
        this.eventSubscriber = this.eventManager.subscribe('dishesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
