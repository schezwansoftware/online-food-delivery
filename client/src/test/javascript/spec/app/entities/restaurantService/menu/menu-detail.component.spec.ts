/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FoodClientTestModule } from '../../../../test.module';
import { MenuDetailComponent } from 'app/entities/restaurantService/menu/menu-detail.component';
import { Menu } from 'app/shared/model/restaurantService/menu.model';

describe('Component Tests', () => {
    describe('Menu Management Detail Component', () => {
        let comp: MenuDetailComponent;
        let fixture: ComponentFixture<MenuDetailComponent>;
        const route = ({ data: of({ menu: new Menu('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [MenuDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MenuDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MenuDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.menu).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
            });
        });
    });
});
