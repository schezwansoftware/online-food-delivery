import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

@Component({
    selector: 'jhi-restaurant-schedule-detail',
    templateUrl: './restaurant-schedule-detail.component.html'
})
export class RestaurantScheduleDetailComponent implements OnInit {
    restaurantSchedule: IRestaurantSchedule;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurantSchedule }) => {
            this.restaurantSchedule = restaurantSchedule;
        });
    }

    previousState() {
        window.history.back();
    }
}
