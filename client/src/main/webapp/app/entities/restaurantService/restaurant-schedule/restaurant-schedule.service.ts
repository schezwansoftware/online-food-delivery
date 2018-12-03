import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurantSchedule } from 'app/shared/model/restaurantService/restaurant-schedule.model';

type EntityResponseType = HttpResponse<IRestaurantSchedule>;
type EntityArrayResponseType = HttpResponse<IRestaurantSchedule[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantScheduleService {
    private resourceUrl = SERVER_API_URL + 'restaurantservice/api/restaurant-schedules';

    constructor(private http: HttpClient) {}

    create(restaurantSchedule: IRestaurantSchedule): Observable<EntityResponseType> {
        return this.http.post<IRestaurantSchedule>(this.resourceUrl, restaurantSchedule, { observe: 'response' });
    }

    update(restaurantSchedule: IRestaurantSchedule): Observable<EntityResponseType> {
        return this.http.put<IRestaurantSchedule>(this.resourceUrl, restaurantSchedule, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IRestaurantSchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRestaurantSchedule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
