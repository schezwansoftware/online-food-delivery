/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantComponent } from 'app/entities/restaurantService/restaurant/restaurant.component';
import { RestaurantService } from 'app/entities/restaurantService/restaurant/restaurant.service';
import { Restaurant } from 'app/shared/model/restaurantService/restaurant.model';

describe('Component Tests', () => {
    describe('Restaurant Management Component', () => {
        let comp: RestaurantComponent;
        let fixture: ComponentFixture<RestaurantComponent>;
        let service: RestaurantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantComponent],
                providers: []
            })
                .overrideTemplate(RestaurantComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Restaurant('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.restaurants[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
