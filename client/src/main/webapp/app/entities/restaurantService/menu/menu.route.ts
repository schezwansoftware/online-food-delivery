import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Menu } from 'app/shared/model/restaurantService/menu.model';
import { MenuService } from './menu.service';
import { MenuComponent } from './menu.component';
import { MenuDetailComponent } from './menu-detail.component';
import { MenuUpdateComponent } from './menu-update.component';
import { MenuDeletePopupComponent } from './menu-delete-dialog.component';
import { IMenu } from 'app/shared/model/restaurantService/menu.model';

@Injectable({ providedIn: 'root' })
export class MenuResolve implements Resolve<IMenu> {
    constructor(private service: MenuService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((menu: HttpResponse<Menu>) => menu.body));
        }
        return of(new Menu());
    }
}

export const menuRoute: Routes = [
    {
        path: 'menu',
        component: MenuComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'menu/:id/view',
        component: MenuDetailComponent,
        resolve: {
            menu: MenuResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_RESTAURANT_EXECUTIVE'],
            pageTitle: 'foodClientApp.restaurantServiceMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'menu/new/:restaurantId',
        component: MenuUpdateComponent,
        resolve: {
            menu: MenuResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_RESTAURANT_EXECUTIVE'],
            pageTitle: 'foodClientApp.restaurantServiceMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'menu/:id/edit',
        component: MenuUpdateComponent,
        resolve: {
            menu: MenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceMenu.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const menuPopupRoute: Routes = [
    {
        path: 'menu/:id/delete',
        component: MenuDeletePopupComponent,
        resolve: {
            menu: MenuResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.restaurantServiceMenu.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
