/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantScheduleDeleteDialogComponent } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule-delete-dialog.component';
import { RestaurantScheduleService } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule.service';

describe('Component Tests', () => {
    describe('RestaurantSchedule Management Delete Component', () => {
        let comp: RestaurantScheduleDeleteDialogComponent;
        let fixture: ComponentFixture<RestaurantScheduleDeleteDialogComponent>;
        let service: RestaurantScheduleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantScheduleDeleteDialogComponent]
            })
                .overrideTemplate(RestaurantScheduleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RestaurantScheduleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantScheduleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
