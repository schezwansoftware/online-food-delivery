import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrders } from 'app/shared/model/orderService/orders.model';
import { Principal } from 'app/core';
import { OrdersService } from './orders.service';

@Component({
    selector: 'jhi-orders',
    templateUrl: './orders.component.html'
})
export class OrdersComponent implements OnInit, OnDestroy {
    orders: IOrders[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ordersService: OrdersService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ordersService.query().subscribe(
            (res: HttpResponse<IOrders[]>) => {
                this.orders = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrders) {
        return item.id;
    }

    registerChangeInOrders() {
        this.eventSubscriber = this.eventManager.subscribe('ordersListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    check() {
        this.ordersService.demo(this.currentAccount.id).subscribe(
            () => {},
            error2 => {
                console.log(error2);
            }
        );
    }
}
