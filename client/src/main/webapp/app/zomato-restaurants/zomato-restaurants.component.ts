import { Component, OnInit } from '@angular/core';
import { RestaurantService } from '../entities/restaurantService/restaurant/restaurant.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
    selector: 'jhi-zomato-restaurants',
    templateUrl: './zomato-restaurants.component.html',
    styleUrls: ['zomato-restaurants.css']
})
export class ZomatoRestaurantsComponent implements OnInit {
    message: string;
    restaurants: any[];
    search: string;
    fetchError: boolean;

    constructor(private restaurantService: RestaurantService, private spinner: NgxSpinnerService) {
        this.message = 'ZomatoRestaurantsComponent message';
    }

    ngOnInit() {
        this.loadRestaurantsFromZomato();
    }
    loadRestaurantsFromZomato() {
        this.spinner.show();
        this.fetchError = false;
        this.restaurantService.findAllZomatoRestaurants().subscribe(
            res => {
                this.restaurants = res.restaurants;
                this.spinner.hide();
            },
            error => {
                this.fetchError = true;
            }
        );
    }

    cutsomSearch() {
        this.restaurants = [];
        this.fetchError = false;
        this.restaurantService.customZomatoSearch(this.search).subscribe(
            res => {
                this.restaurants = res.restaurants;
                this.spinner.hide();
            },
            error => {
                this.fetchError = true;
            }
        );
    }
}
