import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DishesService } from 'app/entities/restaurantService/dishes';
import { IDishes } from 'app/shared/model/restaurantService/dishes.model';

import { IMenu } from 'app/shared/model/restaurantService/menu.model';
import * as _ from 'underscore';

@Component({
    selector: 'jhi-menu-detail',
    templateUrl: './menu-detail.component.html'
})
export class MenuDetailComponent implements OnInit {
    menu: IMenu;
    dishes: IDishes[] = [];
    dessertDishes: IDishes[] = [];
    starterDishes: IDishes[] = [];
    maincourseDishes: IDishes[] = [];

    constructor(private activatedRoute: ActivatedRoute, private dishService: DishesService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
            this.dishService.dishByMenuId(this.menu.id).subscribe(res => {
                this.dishes = res.body;
                this.funcGroupBy();
            });
        });
    }

    previousState() {
        window.history.back();
    }
    myfunc(evt: any) {
        console.log(evt);
    }

    funcGroupBy() {
        const data = _.groupBy(this.dishes, 'dishType');
        console.log(data);

        this.dessertDishes = data.Dessert;
        this.starterDishes = data.Starter;
        this.maincourseDishes = data.MainCourse;

        console.log(this.dessertDishes);
        console.log(this.starterDishes);
        console.log(this.dessertDishes);
    }
}
