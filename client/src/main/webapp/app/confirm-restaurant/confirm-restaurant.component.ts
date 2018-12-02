import { Component, OnInit } from '@angular/core';
import { ZomatoRestaurantService } from '../entities/restaurantService/restaurant/zomato-restaurant.service';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-confirm-restaurant',
    templateUrl: './confirm-restaurant.component.html',
    styleUrls: ['confirm-restaurant.css']
})
export class ConfirmRestaurantComponent implements OnInit {
    message: string;
    restuarantId: string;
    restaurant: any = {};

    constructor(private zomatoService: ZomatoRestaurantService, private route: ActivatedRoute) {
        this.message = 'ConfirmRestaurantComponent message';
    }

    ngOnInit() {
        this.restaurant.location = {};
        this.route.params.subscribe(params => {
            this.restuarantId = params.id;
            this.loadRestaurant(this.restuarantId);
        });
    }

    loadRestaurant(id: string) {
        this.zomatoService.findRestaurantById(id).subscribe(res => {
            this.restaurant = res;
        });
    }

    confirm() {
        this.zomatoService.mapToRestaurant(this.restaurant).subscribe(res => {
            console.log(res);
        });
    }
}
