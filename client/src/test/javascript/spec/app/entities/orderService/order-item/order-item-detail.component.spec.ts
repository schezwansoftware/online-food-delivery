/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { OrderItemDetailComponent } from 'app/entities/orderService/order-item/order-item-detail.component';
import { OrderItem } from 'app/shared/model/orderService/order-item.model';

describe('Component Tests', () => {
    describe('OrderItem Management Detail Component', () => {
        let comp: OrderItemDetailComponent;
        let fixture: ComponentFixture<OrderItemDetailComponent>;
        const route = ({ data: of({ orderItem: new OrderItem('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [OrderItemDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrderItemDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrderItemDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orderItem).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
