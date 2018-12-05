import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMenu } from 'app/shared/model/restaurantService/menu.model';
import { MenuService } from './menu.service';
import { IMenuItem, MenuItemModel } from 'app/shared/model/restaurantService/menu-Item.model';
import { Dishes } from 'app/shared/model/restaurantService/dishes.model';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;
    startDate: string;
    endDate: string;
    saveMenu: boolean;
    menuItem: MenuItemModel = {};
    dish: Dishes = {};

    constructor(private menuService: MenuService, private activatedRoute: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        this.menuItem.dishes = [];
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
            this.startDate = this.menu.startDate != null ? this.menu.startDate.format(DATE_TIME_FORMAT) : null;
            this.endDate = this.menu.endDate != null ? this.menu.endDate.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.menu.startDate = this.startDate != null ? moment(this.startDate, DATE_TIME_FORMAT) : null;
        this.menu.endDate = this.endDate != null ? moment(this.endDate, DATE_TIME_FORMAT) : null;
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    addDish() {
        this.menuItem.dishes.push(this.dish);
        this.dish = {};
    }

    removeDish(dish) {
        this.menuItem.dishes.splice(dish, 1);
    }

    saveMenuDishes() {
        this.saveMenu = true;
        this.menuItem.restaurantId = this.activatedRoute.snapshot.paramMap.get('restaurantId');
        this.menuService.saveMenuItem(this.menuItem).subscribe(
            res => {
                alert(res.body);
                console.log(res.body);
                this.router.navigate(['/restaurant', `${res.body.restaurantId}`, 'view']);
            },
            error1 => {
                this.saveMenu = false;
                console.log(error1);
            }
        );
    }
}
