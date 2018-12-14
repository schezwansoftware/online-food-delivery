/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { OrderItemUpdateComponent } from 'app/entities/orderService/order-item/order-item-update.component';
import { OrderItemService } from 'app/entities/orderService/order-item/order-item.service';
import { OrderItem } from 'app/shared/model/orderService/order-item.model';

describe('Component Tests', () => {
    describe('OrderItem Management Update Component', () => {
        let comp: OrderItemUpdateComponent;
        let fixture: ComponentFixture<OrderItemUpdateComponent>;
        let service: OrderItemService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [OrderItemUpdateComponent]
            })
                .overrideTemplate(OrderItemUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrderItemUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderItemService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderItem('9fec3727-3421-4967-b213-ba36557ca194');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OrderItem();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orderItem = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
