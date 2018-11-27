import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { RestaurantService } from './restaurant.service';

@Component({
    selector: 'jhi-restaurant-update',
    templateUrl: './restaurant-update.component.html'
})
export class RestaurantUpdateComponent implements OnInit {
    restaurant: IRestaurant;
    isSaving: boolean;
    registrationDate: string;

    constructor(private restaurantService: RestaurantService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
        this.restaurant.registrationDate = this.registrationDate != null ? moment(this.registrationDate, DATE_TIME_FORMAT) : null;
        if (this.restaurant.id !== undefined) {
            this.subscribeToSaveResponse(this.restaurantService.update(this.restaurant));
        } else {
            this.subscribeToSaveResponse(this.restaurantService.create(this.restaurant));
        }
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
}
