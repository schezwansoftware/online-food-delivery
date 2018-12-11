import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IOrders } from 'app/shared/model/orderService/orders.model';
import { OrdersService } from './orders.service';
import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { RestaurantService } from 'app/entities/restaurantService/restaurant';
import { MenuService } from '../../restaurantService/menu/menu.service';
import { IMenuItem } from '../../../shared/model/restaurantService/menu-Item.model';

@Component({
    selector: 'jhi-orders-update',
    templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
    orders: IOrders;
    isSaving: boolean;
    restaurants: IRestaurant[];
    menuItem: IMenuItem;

    constructor(
        private ordersService: OrdersService,
        private activatedRoute: ActivatedRoute,
        private restaurantService: RestaurantService,
        private menuService: MenuService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orders }) => {
            this.orders = orders;
        });
        this.loadAllRestaurants();
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orders.id !== undefined) {
            this.subscribeToSaveResponse(this.ordersService.update(this.orders));
        } else {
            this.subscribeToSaveResponse(this.ordersService.create(this.orders));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
        result.subscribe((res: HttpResponse<IOrders>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private loadAllRestaurants() {
        this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => {
            this.restaurants = res.body;
        });
    }

    loadRestaurantMenu(restaurantId: string) {
        this.menuService.findByRestaurantId(restaurantId).subscribe((res: HttpResponse<IMenuItem>) => {
            this.menuItem = res.body;
            console.log(this.menuItem);
        });
    }
}
