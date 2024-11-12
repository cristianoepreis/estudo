import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoEnsino } from '../plano-ensino.model';
import { PlanoEnsinoService } from '../service/plano-ensino.service';

const planoEnsinoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlanoEnsino> => {
  const id = route.params.id;
  if (id) {
    return inject(PlanoEnsinoService)
      .find(id)
      .pipe(
        mergeMap((planoEnsino: HttpResponse<IPlanoEnsino>) => {
          if (planoEnsino.body) {
            return of(planoEnsino.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default planoEnsinoResolve;
