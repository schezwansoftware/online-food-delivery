import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { IRestaurantLocation } from '../../../shared/model/restaurantService/restaurant-location.model';

@Component({
    selector: 'jhi-restaurant-update',
    templateUrl: './restaurant-update.component.html'
})
export class RestaurantUpdateComponent implements OnInit {
    restaurant: IRestaurant;
    isSaving: boolean;
    registrationDate: string;
    restaurantLocation: IRestaurantLocation;

    constructor(private restaurantService: RestaurantService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.restaurantLocation = {};
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurant }) => {
            this.restaurant = restaurant;
            this.registrationDate =
                this.restaurant.registrationDate != null ? this.restaurant.registrationDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurant>>) {
        result.subscribe((res: HttpResponse<IRestaurant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    getLocation() {
        if (this.restaurantLocation.longitude && this.restaurantLocation.latitude) {
            return;
        }
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                this.restaurantLocation.latitude = position.coords.latitude;
                this.restaurantLocation.longitude = position.coords.longitude;
            });
        } else {
            alert('Geo Location is not supported by this browser');
        }
    }
}
