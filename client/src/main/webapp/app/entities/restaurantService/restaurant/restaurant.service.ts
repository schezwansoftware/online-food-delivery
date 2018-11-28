///<reference path="../../../../../../../node_modules/@angular/common/http/src/response.d.ts"/>
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { IRestaurantLocation } from '../../../shared/model/restaurantService/restaurant-location.model';

type EntityResponseType = HttpResponse<IRestaurant>;
type EntityArrayResponseType = HttpResponse<IRestaurant[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantService {
    private resourceUrl = SERVER_API_URL + 'restaurantservice/api/restaurants';
    private restaurantsLocationUrl = SERVER_API_URL + 'restaurantservice/api/restaurants-location';
    private ZOMATO_API_KEY = 'b1fbbb496c364712fb7dd4829003902d';
    private ZOMATO_RESTAURANT_SEARCH_URL = 'https://developers.zomato.com/api/v2.1/search';

    constructor(private http: HttpClient) {}

    create(restaurant: IRestaurant): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(restaurant);
        return this.http
            .post<IRestaurant>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(restaurant: IRestaurant): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(restaurant);
        return this.http
            .put<IRestaurant>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IRestaurant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByLogin(): Observable<EntityResponseType> {
        return this.http
            .get<IRestaurant>(`${this.resourceUrl}/user`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRestaurant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }
    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    cuisineTypes(): Observable<string[]> {
        return of(['Chinese', 'South-Indian', 'North-Indian', 'Continental']);
    }

    saveRestaurant(restaurant: IRestaurantLocation): Observable<EntityResponseType> {
        return this.http.post<IRestaurant>(this.restaurantsLocationUrl, restaurant, { observe: 'response' });
    }

    findAllZomatoRestaurants(): Observable<any> {
        const headers = new HttpHeaders({ 'user-key': this.ZOMATO_API_KEY });
        return this.http.get<any>(this.ZOMATO_RESTAURANT_SEARCH_URL, { headers });
    }
    private convertDateFromClient(restaurant: IRestaurant): IRestaurant {
        const copy: IRestaurant = Object.assign({}, restaurant, {
            registrationDate:
                restaurant.registrationDate != null && restaurant.registrationDate.isValid() ? restaurant.registrationDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.registrationDate = res.body.registrationDate != null ? moment(res.body.registrationDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((restaurant: IRestaurant) => {
            restaurant.registrationDate = restaurant.registrationDate != null ? moment(restaurant.registrationDate) : null;
        });
        return res;
    }
}
