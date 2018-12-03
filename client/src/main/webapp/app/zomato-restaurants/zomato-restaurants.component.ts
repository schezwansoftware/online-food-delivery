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
    page: any;
    collectionsSize: any;
    maxSize: number;
    pageSize: number;

    constructor(private restaurantService: RestaurantService, private spinner: NgxSpinnerService) {
        this.message = 'ZomatoRestaurantsComponent message';
    }

    ngOnInit() {
        this.page = 1;
        this.maxSize = 10;
        this.pageSize = 20;
        this.loadRestaurantsFromZomato(this.page);
    }
    loadRestaurantsFromZomato(page: number) {
        this.fetchError = false;
        this.restaurantService.findAllZomatoRestaurants(page).subscribe(
            res => {
                this.restaurants = res.restaurants;
                console.log(res);
                this.collectionsSize = res.results_found;
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

    onPageChange(page: number) {
        page = (page - 1) * this.pageSize + 1;
        this.loadRestaurantsFromZomato(page);
    }
}
