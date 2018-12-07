/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { OrdersComponent } from 'app/entities/orderService/orders/orders.component';
import { OrdersService } from 'app/entities/orderService/orders/orders.service';
import { Orders } from 'app/shared/model/orderService/orders.model';

describe('Component Tests', () => {
    describe('Orders Management Component', () => {
        let comp: OrdersComponent;
        let fixture: ComponentFixture<OrdersComponent>;
        let service: OrdersService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [OrdersComponent],
                providers: []
            })
                .overrideTemplate(OrdersComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrdersComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdersService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Orders('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orders[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
