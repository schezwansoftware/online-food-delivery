/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantScheduleUpdateComponent } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule-update.component';
import { RestaurantScheduleService } from 'app/entities/restaurantService/restaurant-schedule/restaurant-schedule.service';
import { RestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

describe('Component Tests', () => {
    describe('RestaurantSchedule Management Update Component', () => {
        let comp: RestaurantScheduleUpdateComponent;
        let fixture: ComponentFixture<RestaurantScheduleUpdateComponent>;
        let service: RestaurantScheduleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantScheduleUpdateComponent]
            })
                .overrideTemplate(RestaurantScheduleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantScheduleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantScheduleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new RestaurantSchedule('9fec3727-3421-4967-b213-ba36557ca194');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurantSchedule = entity;
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
                    const entity = new RestaurantSchedule();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurantSchedule = entity;
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
