import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrders } from 'app/shared/model/orderService/orders.model';

type EntityResponseType = HttpResponse<IOrders>;
type EntityArrayResponseType = HttpResponse<IOrders[]>;

@Injectable({ providedIn: 'root' })
export class OrdersService {
    private resourceUrl = SERVER_API_URL + 'orderservice/api/orders';

    constructor(private http: HttpClient) {}

    create(orders: IOrders): Observable<EntityResponseType> {
        return this.http.post<IOrders>(this.resourceUrl, orders, { observe: 'response' });
    }

    update(orders: IOrders): Observable<EntityResponseType> {
        return this.http.put<IOrders>(this.resourceUrl, orders, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IOrders>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    demo(id: string): Observable<EntityResponseType> {
        return this.http.get<IOrders>(`${this.resourceUrl}/user/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrders[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
