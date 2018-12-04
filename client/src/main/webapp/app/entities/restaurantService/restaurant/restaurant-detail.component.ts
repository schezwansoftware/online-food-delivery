import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { LocationService } from '../location/location.service';
import { HttpResponse } from '@angular/common/http';
import { ILocation } from '../../../shared/model/restaurantService/location.model';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-restaurant-detail',
    templateUrl: './restaurant-detail.component.html'
})
export class RestaurantDetailComponent implements OnInit {
    restaurant: IRestaurant;
    location: ILocation = {};

    constructor(private activatedRoute: ActivatedRoute, private locationService: LocationService, private router: Router) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
            this.loadLocatonDetails(this.restaurant.locationId);
        });
    }

    previousState() {
        window.history.back();
    }

    createMenu() {
        this.router.navigate(['menu/new', this.restaurant.id]);
    }

    loadLocatonDetails(id: string) {
        this.locationService.find(id).subscribe((res: HttpResponse<ILocation>) => {
            this.location = res.body;
        });
    }
}
