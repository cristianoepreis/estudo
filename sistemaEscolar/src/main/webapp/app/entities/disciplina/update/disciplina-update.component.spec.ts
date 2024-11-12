import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IProfessor } from 'app/entities/professor/professor.model';
import { ProfessorService } from 'app/entities/professor/service/professor.service';
import { ICurso } from 'app/entities/curso/curso.model';
import { CursoService } from 'app/entities/curso/service/curso.service';
import { IDisciplina } from '../disciplina.model';
import { DisciplinaService } from '../service/disciplina.service';
import { DisciplinaFormService } from './disciplina-form.service';

import { DisciplinaUpdateComponent } from './disciplina-update.component';

describe('Disciplina Management Update Component', () => {
  let comp: DisciplinaUpdateComponent;
  let fixture: ComponentFixture<DisciplinaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let disciplinaFormService: DisciplinaFormService;
  let disciplinaService: DisciplinaService;
  let professorService: ProfessorService;
  let cursoService: CursoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DisciplinaUpdateComponent],
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
      .overrideTemplate(DisciplinaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DisciplinaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    disciplinaFormService = TestBed.inject(DisciplinaFormService);
    disciplinaService = TestBed.inject(DisciplinaService);
    professorService = TestBed.inject(ProfessorService);
    cursoService = TestBed.inject(CursoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Professor query and add missing value', () => {
      const disciplina: IDisciplina = { id: 456 };
      const professorResponsavel: IProfessor = { id: 8138 };
      disciplina.professorResponsavel = professorResponsavel;

      const professorCollection: IProfessor[] = [{ id: 18986 }];
      jest.spyOn(professorService, 'query').mockReturnValue(of(new HttpResponse({ body: professorCollection })));
      const additionalProfessors = [professorResponsavel];
      const expectedCollection: IProfessor[] = [...additionalProfessors, ...professorCollection];
      jest.spyOn(professorService, 'addProfessorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplina });
      comp.ngOnInit();

      expect(professorService.query).toHaveBeenCalled();
      expect(professorService.addProfessorToCollectionIfMissing).toHaveBeenCalledWith(
        professorCollection,
        ...additionalProfessors.map(expect.objectContaining),
      );
      expect(comp.professorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Curso query and add missing value', () => {
      const disciplina: IDisciplina = { id: 456 };
      const cursos: ICurso[] = [{ id: 15219 }];
      disciplina.cursos = cursos;

      const cursoCollection: ICurso[] = [{ id: 16899 }];
      jest.spyOn(cursoService, 'query').mockReturnValue(of(new HttpResponse({ body: cursoCollection })));
      const additionalCursos = [...cursos];
      const expectedCollection: ICurso[] = [...additionalCursos, ...cursoCollection];
      jest.spyOn(cursoService, 'addCursoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ disciplina });
      comp.ngOnInit();

      expect(cursoService.query).toHaveBeenCalled();
      expect(cursoService.addCursoToCollectionIfMissing).toHaveBeenCalledWith(
        cursoCollection,
        ...additionalCursos.map(expect.objectContaining),
      );
      expect(comp.cursosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const disciplina: IDisciplina = { id: 456 };
      const professorResponsavel: IProfessor = { id: 19565 };
      disciplina.professorResponsavel = professorResponsavel;
      const curso: ICurso = { id: 1052 };
      disciplina.cursos = [curso];

      activatedRoute.data = of({ disciplina });
      comp.ngOnInit();

      expect(comp.professorsSharedCollection).toContain(professorResponsavel);
      expect(comp.cursosSharedCollection).toContain(curso);
      expect(comp.disciplina).toEqual(disciplina);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplina>>();
      const disciplina = { id: 123 };
      jest.spyOn(disciplinaFormService, 'getDisciplina').mockReturnValue(disciplina);
      jest.spyOn(disciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disciplina }));
      saveSubject.complete();

      // THEN
      expect(disciplinaFormService.getDisciplina).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(disciplinaService.update).toHaveBeenCalledWith(expect.objectContaining(disciplina));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplina>>();
      const disciplina = { id: 123 };
      jest.spyOn(disciplinaFormService, 'getDisciplina').mockReturnValue({ id: null });
      jest.spyOn(disciplinaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplina: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: disciplina }));
      saveSubject.complete();

      // THEN
      expect(disciplinaFormService.getDisciplina).toHaveBeenCalled();
      expect(disciplinaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDisciplina>>();
      const disciplina = { id: 123 };
      jest.spyOn(disciplinaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ disciplina });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(disciplinaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProfessor', () => {
      it('Should forward to professorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(professorService, 'compareProfessor');
        comp.compareProfessor(entity, entity2);
        expect(professorService.compareProfessor).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCurso', () => {
      it('Should forward to cursoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cursoService, 'compareCurso');
        comp.compareCurso(entity, entity2);
        expect(cursoService.compareCurso).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
