import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DishesService } from 'app/entities/restaurantService/dishes';

import { IMenu } from 'app/shared/model/restaurantService/menu.model';

@Component({
    selector: 'jhi-menu-detail',
    templateUrl: './menu-detail.component.html'
})
export class MenuDetailComponent implements OnInit {
    menu: IMenu;

    constructor(private activatedRoute: ActivatedRoute, private dishService: DishesService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
            this.dishService.dishByMenuId(this.menu.id).subscribe(res => {
                console.log(res.body);
            });
        });
    }

    previousState() {
        window.history.back();
    }
}
