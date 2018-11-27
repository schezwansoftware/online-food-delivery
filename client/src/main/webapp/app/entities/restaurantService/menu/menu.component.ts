import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/restaurantService/menu.model';
import { Principal } from 'app/core';
import { MenuService } from './menu.service';

@Component({
    selector: 'jhi-menu',
    templateUrl: './menu.component.html'
})
export class MenuComponent implements OnInit, OnDestroy {
    menus: IMenu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private menuService: MenuService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.menuService.query().subscribe(
            (res: HttpResponse<IMenu[]>) => {
                this.menus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMenu) {
        return item.id;
    }

    registerChangeInMenus() {
        this.eventSubscriber = this.eventManager.subscribe('menuListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
