/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { LocationComponent } from 'app/entities/restaurantService/location/location.component';
import { LocationService } from 'app/entities/restaurantService/location/location.service';
import { Location } from 'app/shared/model/restaurantService/location.model';

describe('Component Tests', () => {
    describe('Location Management Component', () => {
        let comp: LocationComponent;
        let fixture: ComponentFixture<LocationComponent>;
        let service: LocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [LocationComponent],
                providers: []
            })
                .overrideTemplate(LocationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Location('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locations[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
