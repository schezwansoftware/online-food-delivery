import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';

@Component({
    selector: 'jhi-restaurant-detail',
    templateUrl: './restaurant-detail.component.html'
})
export class RestaurantDetailComponent implements OnInit {
    restaurant: IRestaurant;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
        });
    }

    previousState() {
        window.history.back();
    }
}
