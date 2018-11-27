/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { DishesDetailComponent } from 'app/entities/restaurantService/dishes/dishes-detail.component';
import { Dishes } from 'app/shared/model/restaurantService/dishes.model';

describe('Component Tests', () => {
    describe('Dishes Management Detail Component', () => {
        let comp: DishesDetailComponent;
        let fixture: ComponentFixture<DishesDetailComponent>;
        const route = ({ data: of({ dishes: new Dishes('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [DishesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DishesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DishesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dishes).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
