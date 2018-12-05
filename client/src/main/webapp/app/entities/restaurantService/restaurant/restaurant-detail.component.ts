import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { LocationService } from '../location/location.service';
import { HttpResponse } from '@angular/common/http';
import { ILocation } from '../../../shared/model/restaurantService/location.model';
import { Router } from '@angular/router';
import { MenuService } from 'app/entities/restaurantService/menu';
import { IMenu } from 'app/shared/model/restaurantService/menu.model';

@Component({
    selector: 'jhi-restaurant-detail',
    templateUrl: './restaurant-detail.component.html'
})
export class RestaurantDetailComponent implements OnInit {
    restaurant: IRestaurant;
    location: ILocation = {};
    menu: IMenu = {};
    isMenu: boolean;

    constructor(
        private activatedRoute: ActivatedRoute,
        private locationService: LocationService,
        private router: Router,
        private menuService: MenuService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
            this.loadLocatonDetails(this.restaurant.locationId);
            this.loadMenu(this.restaurant.id);
        });
    }

    previousState() {
        window.history.back();
    }

    loadLocatonDetails(id: string) {
        this.locationService.find(id).subscribe((res: HttpResponse<ILocation>) => {
            this.location = res.body;
        });
    }

    loadMenu(id: string) {
        this.isMenu = false;
        this.menuService.findByRestaurantId(id).subscribe(
            res => {
                this.isMenu = true;
                console.log(res.body);
                this.menu = res.body;
            },
            error1 => {
                console.log(error1);
            }
        );
    }
}
