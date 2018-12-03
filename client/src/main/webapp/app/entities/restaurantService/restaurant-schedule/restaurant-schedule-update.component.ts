import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';
import { RestaurantScheduleService } from './restaurant-schedule.service';

@Component({
    selector: 'jhi-restaurant-schedule-update',
    templateUrl: './restaurant-schedule-update.component.html'
})
export class RestaurantScheduleUpdateComponent implements OnInit {
    restaurantSchedule: IRestaurantSchedule;
    isSaving: boolean;

    constructor(private restaurantScheduleService: RestaurantScheduleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ restaurantSchedule }) => {
            this.restaurantSchedule = restaurantSchedule;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.restaurantSchedule.id !== undefined) {
            this.subscribeToSaveResponse(this.restaurantScheduleService.update(this.restaurantSchedule));
        } else {
            this.subscribeToSaveResponse(this.restaurantScheduleService.create(this.restaurantSchedule));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRestaurantSchedule>>) {
        result.subscribe((res: HttpResponse<IRestaurantSchedule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
