/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantScheduleComponent } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule.component';
import { RestaurantScheduleService } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule.service';
import { RestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

describe('Component Tests', () => {
    describe('RestaurantSchedule Management Component', () => {
        let comp: RestaurantScheduleComponent;
        let fixture: ComponentFixture<RestaurantScheduleComponent>;
        let service: RestaurantScheduleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantScheduleComponent],
                providers: []
            })
                .overrideTemplate(RestaurantScheduleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantScheduleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantScheduleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RestaurantSchedule('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.restaurantSchedules[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
