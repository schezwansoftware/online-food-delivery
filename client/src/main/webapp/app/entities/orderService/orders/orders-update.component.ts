import { Component, NgZone, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
import { GoogleMapsAPIWrapper, MapsAPILoader } from '@agm/core';
import { Principal } from '../../../core/auth/principal.service';

declare let google: any;

@Component({
    selector: 'jhi-orders-update',
    templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
    orders: IOrders;
    isSaving: boolean;
    addressType: string;
    account: any;
    restaurants: IRestaurant[];
    geoCoder: any;
    menuItem: IMenuItem;
    page = 0;
    orderItem: IOrderItem = {};
    location: Location = {};
    selectedRestaurant: IRestaurant;
    order: Order = {
        orderInfo: {},
        itemsInfo: []
    };

    constructor(
        private ordersService: OrdersService,
        private activatedRoute: ActivatedRoute,
        private restaurantService: RestaurantService,
        private menuService: MenuService,
        private principal: Principal,
        public mapsApiLoader: MapsAPILoader,
        private zone: NgZone,
        private wrapper: GoogleMapsAPIWrapper,
        private orderService: OrdersService,
        private route: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orders }) => {
            this.orders = orders;
        });
        this.loadAllRestaurants();

        this.mapsApiLoader.load().then(() => {
            this.geoCoder = new google.maps.Geocoder();
        });

        this.principal.identity().then(account => {
            this.account = account;
        });
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
            this.selectedRestaurant = this.getSelectedRestaurant(restaurantId);
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

    markCurrentLocation() {
        this.isSaving = true;
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.location.latitude = position.coords.latitude;
                this.location.longitude = position.coords.longitude;
                this.findAddressFromCoordinates(this.location);
            });
        } else {
            alert('Geo Location is not supported by this browser');
        }
    }

    private findAddressFromCoordinates(location: Location) {
        if (!this.geoCoder) {
            this.geoCoder = new google.maps.Geocoder();
        }
        this.geoCoder.geocode(
            {
                location: {
                    lat: location.latitude,
                    lng: location.longitude
                }
            },
            (result, status) => {
                if (status === google.maps.GeocoderStatus.OK) {
                    this.orders.deliveryAddress = result[0].formatted_address;
                } else {
                    this.orders.deliveryAddress = 'error occured.Please fill the address manually';
                }
                this.isSaving = false;
            }
        );
    }

    confirmOrder() {
        this.order.orderInfo = this.orders;
        this.order.orderInfo.userId = this.account.id;
        this.order.paymentStatus = 'PENDING';
        console.log(this.order);
        this.ordersService.create(this.order).subscribe((res: HttpResponse<Order>) => {
            this.order = res.body;
            this.route.navigate(['orders', this.order.orderInfo.id, 'view']);
        });
    }
    private getSelectedRestaurant(resturantId: string): IRestaurant {
        return this.restaurants.filter(x => x.id === resturantId)[0];
    }
}

interface Order {
    orderInfo?: IOrders;
    itemsInfo?: IOrderItem[];
    paymentStatus?: string;
}

interface Location {
    latitude?: number;
    longitude?: number;
}
