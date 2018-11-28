import { Component, OnInit } from '@angular/core';
import { RestaurantService } from '../entities/restaurantService/restaurant/restaurant.service';

@Component({
    selector: 'jhi-zomato-restaurants',
    templateUrl: './zomato-restaurants.component.html',
    styleUrls: ['zomato-restaurants.css']
})
export class ZomatoRestaurantsComponent implements OnInit {
    message: string;
    restaurants: any[];

    constructor(private restaurantService: RestaurantService) {
        this.message = 'ZomatoRestaurantsComponent message';
    }

    ngOnInit() {
        this.loadRestaurantsFromZomato();
    }
    loadRestaurantsFromZomato() {
        this.restaurantService.findAllZomatoRestaurants().subscribe(
            res => {
                this.restaurants = res.restaurants;
                console.log(this.restaurants[0]);
            },
            error => {
                console.log(error);
            }
        );
    }
}
