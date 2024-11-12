import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import PlanoEnsinoResolve from './route/plano-ensino-routing-resolve.service';

const planoEnsinoRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/plano-ensino.component').then(m => m.PlanoEnsinoComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/plano-ensino-detail.component').then(m => m.PlanoEnsinoDetailComponent),
    resolve: {
      planoEnsino: PlanoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/plano-ensino-update.component').then(m => m.PlanoEnsinoUpdateComponent),
    resolve: {
      planoEnsino: PlanoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/plano-ensino-update.component').then(m => m.PlanoEnsinoUpdateComponent),
    resolve: {
      planoEnsino: PlanoEnsinoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default planoEnsinoRoute;
