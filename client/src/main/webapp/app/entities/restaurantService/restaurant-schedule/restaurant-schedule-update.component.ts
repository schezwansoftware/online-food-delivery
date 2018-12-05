import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';
import { RestaurantScheduleService } from './restaurant-schedule.service';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
    selector: 'jhi-restaurant-schedule-update',
    templateUrl: './restaurant-schedule-update.component.html'
})
export class RestaurantScheduleUpdateComponent implements OnInit {
    restaurantSchedule: IRestaurantSchedule = {};
    isSaving: boolean;
    days: any = {};
    restaurantId: string;
    openingTime: string;
    closingTime: string;
    scheduleModel: RestaurantScheduleModel = {
        restaurantId: null,
        schedule: []
    };

    constructor(private restaurantScheduleService: RestaurantScheduleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.params.subscribe(params => {
            this.restaurantId = params.restaurantId;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.scheduleModel.restaurantId = this.restaurantId;
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

    addSchedule() {
        Object.entries(this.days).forEach(([key, value]) => {
            if (value) {
                const schedule = this.getScheduleByDay(key);
                if (schedule.length > 0) {
                    schedule[0].closingTime = this.closingTime;
                    schedule[0].openingTime = this.openingTime;
                    this.restaurantSchedule = {};
                    return;
                }
                this.restaurantSchedule.day = key;
                this.restaurantSchedule.openingTime = this.openingTime;
                this.restaurantSchedule.closingTime = this.closingTime;
                this.scheduleModel.schedule.push(this.restaurantSchedule);
                this.restaurantSchedule = {};
            }
        });
        this.openingTime = null;
        this.closingTime = null;
        this.days = {};
    }

    removeSchedule(schedule) {
        this.scheduleModel.schedule.splice(schedule, 1);
    }

    private getScheduleByDay(day) {
        return this.scheduleModel.schedule.filter(x => x.day === day);
    }
}

interface RestaurantScheduleModel {
    restaurantId: string;
    schedule: IRestaurantSchedule[];
}
