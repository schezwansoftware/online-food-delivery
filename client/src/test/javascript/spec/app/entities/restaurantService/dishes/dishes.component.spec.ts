/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { DishesComponent } from 'app/entities/restaurantService/dishes/dishes.component';
import { DishesService } from 'app/entities/restaurantService/dishes/dishes.service';
import { Dishes } from 'app/shared/model/restaurantService/dishes.model';

describe('Component Tests', () => {
    describe('Dishes Management Component', () => {
        let comp: DishesComponent;
        let fixture: ComponentFixture<DishesComponent>;
        let service: DishesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [DishesComponent],
                providers: []
            })
                .overrideTemplate(DishesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DishesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DishesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Dishes('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dishes[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
