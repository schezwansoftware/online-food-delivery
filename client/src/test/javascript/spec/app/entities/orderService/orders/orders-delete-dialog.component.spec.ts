/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FoodClientTestModule } from '../../../../test.module';
import { OrdersDeleteDialogComponent } from 'app/entities/orderService/orders/orders-delete-dialog.component';
import { OrdersService } from 'app/entities/orderService/orders/orders.service';

describe('Component Tests', () => {
    describe('Orders Management Delete Component', () => {
        let comp: OrdersDeleteDialogComponent;
        let fixture: ComponentFixture<OrdersDeleteDialogComponent>;
        let service: OrdersService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [OrdersDeleteDialogComponent]
            })
                .overrideTemplate(OrdersDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdersDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdersService);
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
