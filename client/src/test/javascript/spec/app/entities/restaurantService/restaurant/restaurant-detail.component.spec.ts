/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantDetailComponent } from 'app/entities/restaurantService/restaurant/restaurant-detail.component';
import { Restaurant } from 'app/shared/model/restaurantService/restaurant.model';

describe('Component Tests', () => {
    describe('Restaurant Management Detail Component', () => {
        let comp: RestaurantDetailComponent;
        let fixture: ComponentFixture<RestaurantDetailComponent>;
        const route = ({ data: of({ restaurant: new Restaurant('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RestaurantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RestaurantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.restaurant).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
