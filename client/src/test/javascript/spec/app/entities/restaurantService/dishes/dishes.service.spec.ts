/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { DishesService } from 'app/entities/restaurantService/dishes/dishes.service';
import { IDishes, Dishes } from 'app/shared/model/restaurantService/dishes.model';

describe('Service Tests', () => {
    describe('Dishes Service', () => {
        let injector: TestBed;
        let service: DishesService;
        let httpMock: HttpTestingController;
        let elemDefault: IDishes;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(DishesService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Dishes('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find('9fec3727-3421-4967-b213-ba36557ca194')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Dishes', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Dishes(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Dishes', async () => {
                const returnedFromService = Object.assign(
                    {
                        menuId: 'BBBBBB',
                        dishName: 'BBBBBB',
                        dishType: 'BBBBBB',
                        dishPrice: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Dishes', async () => {
                const returnedFromService = Object.assign(
                    {
                        menuId: 'BBBBBB',
                        dishName: 'BBBBBB',
                        dishType: 'BBBBBB',
                        dishPrice: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Dishes', async () => {
                const rxPromise = service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
