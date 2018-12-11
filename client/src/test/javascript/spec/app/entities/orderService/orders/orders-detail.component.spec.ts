/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { OrdersDetailComponent } from 'app/entities/orderService/orders/orders-detail.component';
import { Orders } from 'app/shared/model/orderService/orders.model';

describe('Component Tests', () => {
    describe('Orders Management Detail Component', () => {
        let comp: OrdersDetailComponent;
        let fixture: ComponentFixture<OrdersDetailComponent>;
        const route = ({ data: of({ orders: new Orders('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [OrdersDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrdersDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdersDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orders).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
