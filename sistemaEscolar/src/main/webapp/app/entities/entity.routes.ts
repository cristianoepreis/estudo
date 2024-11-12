import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'sistemaEscolarApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'professor',
    data: { pageTitle: 'sistemaEscolarApp.professor.home.title' },
    loadChildren: () => import('./professor/professor.routes'),
  },
  {
    path: 'curso',
    data: { pageTitle: 'sistemaEscolarApp.curso.home.title' },
    loadChildren: () => import('./curso/curso.routes'),
  },
  {
    path: 'disciplina',
    data: { pageTitle: 'sistemaEscolarApp.disciplina.home.title' },
    loadChildren: () => import('./disciplina/disciplina.routes'),
  },
  {
    path: 'plano-ensino',
    data: { pageTitle: 'sistemaEscolarApp.planoEnsino.home.title' },
    loadChildren: () => import('./plano-ensino/plano-ensino.routes'),
  },
  {
    path: 'usuario',
    data: { pageTitle: 'sistemaEscolarApp.usuario.home.title' },
    loadChildren: () => import('./usuario/usuario.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
