/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantScheduleDetailComponent } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule-detail.component';
import { RestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

describe('Component Tests', () => {
    describe('RestaurantSchedule Management Detail Component', () => {
        let comp: RestaurantScheduleDetailComponent;
        let fixture: ComponentFixture<RestaurantScheduleDetailComponent>;
        const route = ({
            data: of({ restaurantSchedule: new RestaurantSchedule('9fec3727-3421-4967-b213-ba36557ca194') })
        } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantScheduleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RestaurantScheduleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RestaurantScheduleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.restaurantSchedule).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
