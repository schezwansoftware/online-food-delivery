import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrderItem } from 'app/shared/model/orderService/order-item.model';

@Component({
    selector: 'jhi-order-item-detail',
    templateUrl: './order-item-detail.component.html'
})
export class OrderItemDetailComponent implements OnInit {
    orderItem: IOrderItem;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orderItem }) => {
            this.orderItem = orderItem;
        });
    }

    previousState() {
        window.history.back();
    }
}
