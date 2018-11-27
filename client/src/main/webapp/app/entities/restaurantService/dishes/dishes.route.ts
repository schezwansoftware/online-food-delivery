import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dishes } from 'app/shared/model/restaurantService/dishes.model';
import { DishesService } from './dishes.service';
import { DishesComponent } from './dishes.component';
import { DishesDetailComponent } from './dishes-detail.component';
import { DishesUpdateComponent } from './dishes-update.component';
import { DishesDeletePopupComponent } from './dishes-delete-dialog.component';
import { IDishes } from 'app/shared/model/restaurantService/dishes.model';

@Injectable({ providedIn: 'root' })
export class DishesResolve implements Resolve<IDishes> {
    constructor(private service: DishesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dishes: HttpResponse<Dishes>) => dishes.body));
        }
        return of(new Dishes());
    }
}

export const dishesRoute: Routes = [
    {
        path: 'dishes',
        component: DishesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceDishes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dishes/:id/view',
        component: DishesDetailComponent,
        resolve: {
            dishes: DishesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceDishes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dishes/new',
        component: DishesUpdateComponent,
        resolve: {
            dishes: DishesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceDishes.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dishes/:id/edit',
        component: DishesUpdateComponent,
        resolve: {
            dishes: DishesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceDishes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dishesPopupRoute: Routes = [
    {
        path: 'dishes/:id/delete',
        component: DishesDeletePopupComponent,
        resolve: {
            dishes: DishesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceDishes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
