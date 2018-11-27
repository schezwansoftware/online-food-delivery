import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IDishes } from 'app/shared/model/restaurantService/dishes.model';
import { DishesService } from './dishes.service';

@Component({
    selector: 'jhi-dishes-update',
    templateUrl: './dishes-update.component.html'
})
export class DishesUpdateComponent implements OnInit {
    dishes: IDishes;
    isSaving: boolean;

    constructor(private dishesService: DishesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dishes }) => {
            this.dishes = dishes;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dishes.id !== undefined) {
            this.subscribeToSaveResponse(this.dishesService.update(this.dishes));
        } else {
            this.subscribeToSaveResponse(this.dishesService.create(this.dishes));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDishes>>) {
        result.subscribe((res: HttpResponse<IDishes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
