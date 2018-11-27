/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { RestaurantUpdateComponent } from 'app/entities/restaurantService/restaurant/restaurant-update.component';
import { RestaurantService } from 'app/entities/restaurantService/restaurant/restaurant.service';
import { Restaurant } from 'app/shared/model/restaurantService/restaurant.model';

describe('Component Tests', () => {
    describe('Restaurant Management Update Component', () => {
        let comp: RestaurantUpdateComponent;
        let fixture: ComponentFixture<RestaurantUpdateComponent>;
        let service: RestaurantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [RestaurantUpdateComponent]
            })
                .overrideTemplate(RestaurantUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Restaurant('9fec3727-3421-4967-b213-ba36557ca194');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurant = entity;
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
                    const entity = new Restaurant();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.restaurant = entity;
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
