import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Restaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { RestaurantService } from './restaurant.service';
import { RestaurantComponent } from './restaurant.component';
import { RestaurantDetailComponent } from './restaurant-detail.component';
import { RestaurantUpdateComponent } from './restaurant-update.component';
import { RestaurantDeletePopupComponent } from './restaurant-delete-dialog.component';
import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';

@Injectable({ providedIn: 'root' })
export class RestaurantResolve implements Resolve<IRestaurant> {
    constructor(private service: RestaurantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((restaurant: HttpResponse<Restaurant>) => restaurant.body));
        }
        return of(new Restaurant());
    }
}

export const restaurantRoute: Routes = [
    {
        path: 'restaurant',
        component: RestaurantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant/:id/view',
        component: RestaurantDetailComponent,
        resolve: {
            restaurant: RestaurantResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_RESTAURANT_EXECUTIVE'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant/new',
        component: RestaurantUpdateComponent,
        resolve: {
            restaurant: RestaurantResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_RESTAURANT_EXECUTIVE'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant/:id/edit',
        component: RestaurantUpdateComponent,
        resolve: {
            restaurant: RestaurantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const restaurantPopupRoute: Routes = [
    {
        path: 'restaurant/:id/delete',
        component: RestaurantDeletePopupComponent,
        resolve: {
            restaurant: RestaurantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
