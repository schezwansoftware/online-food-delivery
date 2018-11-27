/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { LocationService } from 'app/entities/restaurantService/location/location.service';
import { ILocation, Location } from 'app/shared/model/restaurantService/location.model';

describe('Service Tests', () => {
    describe('Location Service', () => {
        let injector: TestBed;
        let service: LocationService;
        let httpMock: HttpTestingController;
        let elemDefault: ILocation;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(LocationService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Location('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
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

            it('should create a Location', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 'ID'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Location(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Location', async () => {
                const returnedFromService = Object.assign(
                    {
                        address: 'BBBBBB',
                        state: 'BBBBBB',
                        city: 'BBBBBB',
                        locality: 'BBBBBB',
                        longitude: 1,
                        latitude: 1,
                        pincode: 'BBBBBB',
                        countryName: 'BBBBBB',
                        countryid: 'BBBBBB'
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

            it('should return a list of Location', async () => {
                const returnedFromService = Object.assign(
                    {
                        address: 'BBBBBB',
                        state: 'BBBBBB',
                        city: 'BBBBBB',
                        locality: 'BBBBBB',
                        longitude: 1,
                        latitude: 1,
                        pincode: 'BBBBBB',
                        countryName: 'BBBBBB',
                        countryid: 'BBBBBB'
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

            it('should delete a Location', async () => {
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
