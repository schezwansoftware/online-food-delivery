/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { LocationUpdateComponent } from 'app/entities/restaurantService/location/location-update.component';
import { LocationService } from 'app/entities/restaurantService/location/location.service';
import { Location } from 'app/shared/model/restaurantService/location.model';

describe('Component Tests', () => {
    describe('Location Management Update Component', () => {
        let comp: LocationUpdateComponent;
        let fixture: ComponentFixture<LocationUpdateComponent>;
        let service: LocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [LocationUpdateComponent]
            })
                .overrideTemplate(LocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Location('9fec3727-3421-4967-b213-ba36557ca194');
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.location = entity;
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
                    const entity = new Location();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.location = entity;
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
