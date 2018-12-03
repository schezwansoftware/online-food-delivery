import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';
import { RestaurantScheduleService } from './restaurant-schedule.service';

@Component({
    selector: 'jhi-restaurant-schedule-delete-dialog',
    templateUrl: './restaurant-schedule-delete-dialog.component.html'
})
export class RestaurantScheduleDeleteDialogComponent {
    restaurantSchedule: IRestaurantSchedule;

    constructor(
        private restaurantScheduleService: RestaurantScheduleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.restaurantScheduleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'restaurantScheduleListModification',
                content: 'Deleted an restaurantSchedule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-restaurant-schedule-delete-popup',
    template: ''
})
export class RestaurantScheduleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ restaurantSchedule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RestaurantScheduleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.restaurantSchedule = restaurantSchedule;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
