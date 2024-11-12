import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';
import { IProfessor } from 'app/entities/professor/professor.model';
import { ProfessorService } from 'app/entities/professor/service/professor.service';
import { IPlanoEnsino } from '../plano-ensino.model';
import { PlanoEnsinoService } from '../service/plano-ensino.service';
import { PlanoEnsinoFormService } from './plano-ensino-form.service';

import { PlanoEnsinoUpdateComponent } from './plano-ensino-update.component';

describe('PlanoEnsino Management Update Component', () => {
  let comp: PlanoEnsinoUpdateComponent;
  let fixture: ComponentFixture<PlanoEnsinoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let planoEnsinoFormService: PlanoEnsinoFormService;
  let planoEnsinoService: PlanoEnsinoService;
  let disciplinaService: DisciplinaService;
  let professorService: ProfessorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlanoEnsinoUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlanoEnsinoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlanoEnsinoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    planoEnsinoFormService = TestBed.inject(PlanoEnsinoFormService);
    planoEnsinoService = TestBed.inject(PlanoEnsinoService);
    disciplinaService = TestBed.inject(DisciplinaService);
    professorService = TestBed.inject(ProfessorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Disciplina query and add missing value', () => {
      const planoEnsino: IPlanoEnsino = { id: 456 };
      const disciplina: IDisciplina = { id: 21788 };
      planoEnsino.disciplina = disciplina;

      const disciplinaCollection: IDisciplina[] = [{ id: 7576 }];
      jest.spyOn(disciplinaService, 'query').mockReturnValue(of(new HttpResponse({ body: disciplinaCollection })));
      const additionalDisciplinas = [disciplina];
      const expectedCollection: IDisciplina[] = [...additionalDisciplinas, ...disciplinaCollection];
      jest.spyOn(disciplinaService, 'addDisciplinaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoEnsino });
      comp.ngOnInit();

      expect(disciplinaService.query).toHaveBeenCalled();
      expect(disciplinaService.addDisciplinaToCollectionIfMissing).toHaveBeenCalledWith(
        disciplinaCollection,
        ...additionalDisciplinas.map(expect.objectContaining),
      );
      expect(comp.disciplinasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Professor query and add missing value', () => {
      const planoEnsino: IPlanoEnsino = { id: 456 };
      const professorResponsavel: IProfessor = { id: 11531 };
      planoEnsino.professorResponsavel = professorResponsavel;

      const professorCollection: IProfessor[] = [{ id: 28599 }];
      jest.spyOn(professorService, 'query').mockReturnValue(of(new HttpResponse({ body: professorCollection })));
      const additionalProfessors = [professorResponsavel];
      const expectedCollection: IProfessor[] = [...additionalProfessors, ...professorCollection];
      jest.spyOn(professorService, 'addProfessorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ planoEnsino });
      comp.ngOnInit();

      expect(professorService.query).toHaveBeenCalled();
      expect(professorService.addProfessorToCollectionIfMissing).toHaveBeenCalledWith(
        professorCollection,
        ...additionalProfessors.map(expect.objectContaining),
      );
      expect(comp.professorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const planoEnsino: IPlanoEnsino = { id: 456 };
      const disciplina: IDisciplina = { id: 14454 };
      planoEnsino.disciplina = disciplina;
      const professorResponsavel: IProfessor = { id: 32019 };
      planoEnsino.professorResponsavel = professorResponsavel;

      activatedRoute.data = of({ planoEnsino });
      comp.ngOnInit();

      expect(comp.disciplinasSharedCollection).toContain(disciplina);
      expect(comp.professorsSharedCollection).toContain(professorResponsavel);
      expect(comp.planoEnsino).toEqual(planoEnsino);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoEnsino>>();
      const planoEnsino = { id: 123 };
      jest.spyOn(planoEnsinoFormService, 'getPlanoEnsino').mockReturnValue(planoEnsino);
      jest.spyOn(planoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoEnsino }));
      saveSubject.complete();

      // THEN
      expect(planoEnsinoFormService.getPlanoEnsino).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(planoEnsinoService.update).toHaveBeenCalledWith(expect.objectContaining(planoEnsino));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoEnsino>>();
      const planoEnsino = { id: 123 };
      jest.spyOn(planoEnsinoFormService, 'getPlanoEnsino').mockReturnValue({ id: null });
      jest.spyOn(planoEnsinoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoEnsino: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: planoEnsino }));
      saveSubject.complete();

      // THEN
      expect(planoEnsinoFormService.getPlanoEnsino).toHaveBeenCalled();
      expect(planoEnsinoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlanoEnsino>>();
      const planoEnsino = { id: 123 };
      jest.spyOn(planoEnsinoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ planoEnsino });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(planoEnsinoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDisciplina', () => {
      it('Should forward to disciplinaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(disciplinaService, 'compareDisciplina');
        comp.compareDisciplina(entity, entity2);
        expect(disciplinaService.compareDisciplina).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareProfessor', () => {
      it('Should forward to professorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(professorService, 'compareProfessor');
        comp.compareProfessor(entity, entity2);
        expect(professorService.compareProfessor).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
