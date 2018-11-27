/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { RestaurantService } from 'app/entities/restaurantService/restaurant/restaurant.service';
import { IRestaurant, Restaurant } from 'app/shared/model/restaurantService/restaurant.model';

describe('Service Tests', () => {
    describe('Restaurant Service', () => {
        let injector: TestBed;
        let service: RestaurantService;
        let httpMock: HttpTestingController;
        let elemDefault: IRestaurant;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RestaurantService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Restaurant('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        registrationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find('9fec3727-3421-4967-b213-ba36557ca194')
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Restaurant', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID',
                        registrationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        registrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Restaurant(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Restaurant', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        locationId: 'BBBBBB',
                        executiveLogin: 'BBBBBB',
                        cuisineTypes: 'BBBBBB',
                        currentMenuId: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        registrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Restaurant', async () => {
                const returnedFromService = Object.assign(
                    {
                        name: 'BBBBBB',
                        locationId: 'BBBBBB',
                        executiveLogin: 'BBBBBB',
                        cuisineTypes: 'BBBBBB',
                        currentMenuId: 'BBBBBB',
                        registrationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        registrationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Restaurant', async () => {
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
