import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoEnsino, NewPlanoEnsino } from '../plano-ensino.model';

export type PartialUpdatePlanoEnsino = Partial<IPlanoEnsino> & Pick<IPlanoEnsino, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoEnsino>;
export type EntityArrayResponseType = HttpResponse<IPlanoEnsino[]>;

@Injectable({ providedIn: 'root' })
export class PlanoEnsinoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-ensinos');

  create(planoEnsino: NewPlanoEnsino): Observable<EntityResponseType> {
    return this.http.post<IPlanoEnsino>(this.resourceUrl, planoEnsino, { observe: 'response' });
  }

  update(planoEnsino: IPlanoEnsino): Observable<EntityResponseType> {
    return this.http.put<IPlanoEnsino>(`${this.resourceUrl}/${this.getPlanoEnsinoIdentifier(planoEnsino)}`, planoEnsino, {
      observe: 'response',
    });
  }

  partialUpdate(planoEnsino: PartialUpdatePlanoEnsino): Observable<EntityResponseType> {
    return this.http.patch<IPlanoEnsino>(`${this.resourceUrl}/${this.getPlanoEnsinoIdentifier(planoEnsino)}`, planoEnsino, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoEnsino>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoEnsino[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoEnsinoIdentifier(planoEnsino: Pick<IPlanoEnsino, 'id'>): number {
    return planoEnsino.id;
  }

  comparePlanoEnsino(o1: Pick<IPlanoEnsino, 'id'> | null, o2: Pick<IPlanoEnsino, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoEnsinoIdentifier(o1) === this.getPlanoEnsinoIdentifier(o2) : o1 === o2;
  }

  addPlanoEnsinoToCollectionIfMissing<Type extends Pick<IPlanoEnsino, 'id'>>(
    planoEnsinoCollection: Type[],
    ...planoEnsinosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoEnsinos: Type[] = planoEnsinosToCheck.filter(isPresent);
    if (planoEnsinos.length > 0) {
      const planoEnsinoCollectionIdentifiers = planoEnsinoCollection.map(planoEnsinoItem => this.getPlanoEnsinoIdentifier(planoEnsinoItem));
      const planoEnsinosToAdd = planoEnsinos.filter(planoEnsinoItem => {
        const planoEnsinoIdentifier = this.getPlanoEnsinoIdentifier(planoEnsinoItem);
        if (planoEnsinoCollectionIdentifiers.includes(planoEnsinoIdentifier)) {
          return false;
        }
        planoEnsinoCollectionIdentifiers.push(planoEnsinoIdentifier);
        return true;
      });
      return [...planoEnsinosToAdd, ...planoEnsinoCollection];
    }
    return planoEnsinoCollection;
  }
}
