import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRestaurant } from 'app/shared/model/restaurantService/restaurant.model';

type EntityResponseType = HttpResponse<IRestaurant>;
type EntityArrayResponseType = HttpResponse<IRestaurant[]>;

@Injectable({ providedIn: 'root' })
export class RestaurantService {
    private resourceUrl = SERVER_API_URL + 'restaurantservice/api/restaurants';

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

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRestaurant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
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
