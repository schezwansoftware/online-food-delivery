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
import { IDishes } from '../../../shared/model/restaurantService/dishes.model';
import { IOrderItem } from '../../../shared/model/orderService/order-item.model';

@Component({
    selector: 'jhi-orders-update',
    templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
    orders: IOrders;
    isSaving: boolean;
    restaurants: IRestaurant[];
    menuItem: IMenuItem;
    page = 0;
    order: Order = {
        orderInfo: {},
        itemsInfo: []
    };
    orderItem: IOrderItem = {};

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
        });
    }

    addDish(dish: IDishes) {
        const x = this.findDishInOrder(dish.id);
        if (x.length === 0) {
            this.orderItem.itemId = dish.id;
            this.orderItem.itemName = dish.dishName;
            this.orderItem.itemPrice = dish.dishPrice;
            this.orderItem.itemQuantity = 1;
            this.order.itemsInfo.push(this.orderItem);
            this.orderItem = {};
        } else {
            x[0].itemQuantity += 1;
        }

        console.log(this.order.itemsInfo);
    }

    removeDish(dish: IDishes) {
        const x = this.findDishInOrder(dish.id);
        if (x.length === 0) {
            return;
        } else if (x[0].itemQuantity > 1) {
            x[0].itemQuantity -= 1;
        } else {
            const index = this.order.itemsInfo.indexOf(x[0]);
            this.order.itemsInfo.splice(index, 1);
        }

        console.log(this.order.itemsInfo);
    }

    private findDishInOrder(itemId: string): IOrderItem[] {
        return this.order.itemsInfo.filter(item => item.itemId === itemId);
    }

    getItemQuantity(dish: IDishes) {
        const x = this.findDishInOrder(dish.id);
        if (x.length === 0) {
            return 0;
        } else {
            return x[0].itemQuantity;
        }
    }

    next() {
        this.page = this.page + 1;
    }

    back() {
        this.page -= 1;
    }

    markCurrentLocation() {}
}

interface Order {
    orderInfo?: IOrders;
    itemsInfo?: IOrderItem[];
}
