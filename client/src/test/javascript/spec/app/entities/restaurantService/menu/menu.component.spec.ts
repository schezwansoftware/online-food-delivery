/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FoodClientTestModule } from '../../../../test.module';
import { MenuComponent } from 'app/entities/restaurantService/menu/menu.component';
import { MenuService } from 'app/entities/restaurantService/menu/menu.service';
import { Menu } from 'app/shared/model/restaurantService/menu.model';

describe('Component Tests', () => {
    describe('Menu Management Component', () => {
        let comp: MenuComponent;
        let fixture: ComponentFixture<MenuComponent>;
        let service: MenuService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [FoodClientTestModule],
                declarations: [MenuComponent],
                providers: []
            })
                .overrideTemplate(MenuComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MenuComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Menu('9fec3727-3421-4967-b213-ba36557ca194')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.menus[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
        });
    });
});
