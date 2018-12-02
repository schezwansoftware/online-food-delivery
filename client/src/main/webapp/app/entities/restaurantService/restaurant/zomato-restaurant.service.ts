///<reference path="../../../../../../../node_modules/@angular/common/http/src/response.d.ts"/>
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';
import { IRestaurantLocation, RestaurantLocation } from '../../../shared/model/restaurantService/restaurant-location.model';

@Injectable({ providedIn: 'root' })
export class ZomatoRestaurantService {
    private ZOMATO_API_KEY = 'b1fbbb496c364712fb7dd4829003902d';
    private ZOMATO_RESTAURANT_SEARCH_URL = 'https://developers.zomato.com/api/v2.1/restaurant';

    constructor(private http: HttpClient) {}

    // findAllZomatoRestaurants(): Observable<any> {
    //     const headers = new HttpHeaders({ 'user-key': this.ZOMATO_API_KEY });
    //     return this.http.get<any>(this.ZOMATO_RESTAURANT_SEARCH_URL, { headers });
    // }
    //
    // customZomatoSearch(q: string) {
    //     const params = new HttpParams().set('q', q);
    //     const headers = new HttpHeaders({ 'user-key': this.ZOMATO_API_KEY });
    //     return this.http.get<any>(this.ZOMATO_RESTAURANT_SEARCH_URL, { headers, params });
    // }
    findRestaurantById(res_id: string) {
        const params = new HttpParams().set('res_id', res_id);
        const headers = new HttpHeaders({ 'user-key': this.ZOMATO_API_KEY });
        return this.http.get<any>(`${this.ZOMATO_RESTAURANT_SEARCH_URL}`, { headers, params });
    }

    mapToRestaurant(zomatoRestaurant: any): Observable<IRestaurantLocation> {
        const restaurantLocation = new RestaurantLocation();
        restaurantLocation.id = zomatoRestaurant.id;
        restaurantLocation.name = zomatoRestaurant.name;
        restaurantLocation.locality = zomatoRestaurant.location.locality;
        restaurantLocation.latitude = zomatoRestaurant.location.latitude;
        restaurantLocation.longitude = zomatoRestaurant.location.longitude;
        restaurantLocation.pincode = zomatoRestaurant.location.zipcode;
        restaurantLocation.address = zomatoRestaurant.location.address;
        restaurantLocation.cuisineTypes = zomatoRestaurant.cuisines.split(', ');
        return of(restaurantLocation);
    }
}
