import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMenu } from 'app/shared/model/restaurantService/menu.model';

type EntityResponseType = HttpResponse<IMenu>;
type EntityArrayResponseType = HttpResponse<IMenu[]>;

@Injectable({ providedIn: 'root' })
export class MenuService {
    private resourceUrl = SERVER_API_URL + 'restaurantservice/api/menus';

    constructor(private http: HttpClient) {}

    create(menu: IMenu): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(menu);
        return this.http
            .post<IMenu>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(menu: IMenu): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(menu);
        return this.http
            .put<IMenu>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http
            .get<IMenu>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMenu[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(menu: IMenu): IMenu {
        const copy: IMenu = Object.assign({}, menu, {
            startDate: menu.startDate != null && menu.startDate.isValid() ? menu.startDate.toJSON() : null,
            endDate: menu.endDate != null && menu.endDate.isValid() ? menu.endDate.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
        res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((menu: IMenu) => {
            menu.startDate = menu.startDate != null ? moment(menu.startDate) : null;
            menu.endDate = menu.endDate != null ? moment(menu.endDate) : null;
        });
        return res;
    }
}
