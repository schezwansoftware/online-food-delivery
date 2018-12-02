import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDishes } from 'app/shared/model/restaurantService/dishes.model';

type EntityResponseType = HttpResponse<IDishes>;
type EntityArrayResponseType = HttpResponse<IDishes[]>;

@Injectable({ providedIn: 'root' })
export class DishesService {
    private resourceUrl = SERVER_API_URL + 'restaurantservice/api/dishes';

    constructor(private http: HttpClient) {}

    create(dishes: IDishes): Observable<EntityResponseType> {
        return this.http.post<IDishes>(this.resourceUrl, dishes, { observe: 'response' });
    }

    update(dishes: IDishes): Observable<EntityResponseType> {
        return this.http.put<IDishes>(this.resourceUrl, dishes, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IDishes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDishes[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
