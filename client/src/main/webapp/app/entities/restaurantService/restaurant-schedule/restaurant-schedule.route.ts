import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';
import { RestaurantScheduleService } from './restaurant-schedule.service';
import { RestaurantScheduleComponent } from './restaurant-schedule.component';
import { RestaurantScheduleDetailComponent } from './restaurant-schedule-detail.component';
import { RestaurantScheduleUpdateComponent } from './restaurant-schedule-update.component';
import { RestaurantScheduleDeletePopupComponent } from './restaurant-schedule-delete-dialog.component';
import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

@Injectable({ providedIn: 'root' })
export class RestaurantScheduleResolve implements Resolve<IRestaurantSchedule> {
    constructor(private service: RestaurantScheduleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((restaurantSchedule: HttpResponse<RestaurantSchedule>) => restaurantSchedule.body));
        }
        return of(new RestaurantSchedule());
    }
}

export const restaurantScheduleRoute: Routes = [
    {
        path: 'restaurant-schedule',
        component: RestaurantScheduleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurantSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-schedule/:id/view',
        component: RestaurantScheduleDetailComponent,
        resolve: {
            restaurantSchedule: RestaurantScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurantSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-schedule/new',
        component: RestaurantScheduleUpdateComponent,
        resolve: {
            restaurantSchedule: RestaurantScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurantSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'restaurant-schedule/:id/edit',
        component: RestaurantScheduleUpdateComponent,
        resolve: {
            restaurantSchedule: RestaurantScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurantSchedule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const restaurantSchedulePopupRoute: Routes = [
    {
        path: 'restaurant-schedule/:id/delete',
        component: RestaurantScheduleDeletePopupComponent,
        resolve: {
            restaurantSchedule: RestaurantScheduleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceRestaurantSchedule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
