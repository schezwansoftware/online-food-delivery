/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { DishesUpdateComponent } from 'app/entities/restaurantService/dishes/dishes-update.component';
import { DishesService } from 'app/entities/restaurantService/dishes/dishes.service';
import { Dishes } from 'app/shared/model/restaurantService/dishes.model';

describe('Component Tests', () => {
    describe('Dishes Management Update Component', () => {
        let comp: DishesUpdateComponent;
        let fixture: ComponentFixture<DishesUpdateComponent>;
        let service: DishesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [DishesUpdateComponent]
            })
                .overrideTemplate(DishesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DishesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DishesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dishes('9fec3727-3421-4967-b213-ba36557ca194');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dishes = entity;
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
                    const entity = new Dishes();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dishes = entity;
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
