import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDisciplina } from 'app/entities/disciplina/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/service/disciplina.service';
import { IProfessor } from 'app/entities/professor/professor.model';
import { ProfessorService } from 'app/entities/professor/service/professor.service';
import { PlanoEnsinoService } from '../service/plano-ensino.service';
import { IPlanoEnsino } from '../plano-ensino.model';
import { PlanoEnsinoFormGroup, PlanoEnsinoFormService } from './plano-ensino-form.service';

@Component({
  standalone: true,
  selector: 'jhi-plano-ensino-update',
  templateUrl: './plano-ensino-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PlanoEnsinoUpdateComponent implements OnInit {
  isSaving = false;
  planoEnsino: IPlanoEnsino | null = null;

  disciplinasSharedCollection: IDisciplina[] = [];
  professorsSharedCollection: IProfessor[] = [];

  protected planoEnsinoService = inject(PlanoEnsinoService);
  protected planoEnsinoFormService = inject(PlanoEnsinoFormService);
  protected disciplinaService = inject(DisciplinaService);
  protected professorService = inject(ProfessorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PlanoEnsinoFormGroup = this.planoEnsinoFormService.createPlanoEnsinoFormGroup();

  compareDisciplina = (o1: IDisciplina | null, o2: IDisciplina | null): boolean => this.disciplinaService.compareDisciplina(o1, o2);

  compareProfessor = (o1: IProfessor | null, o2: IProfessor | null): boolean => this.professorService.compareProfessor(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ planoEnsino }) => {
      this.planoEnsino = planoEnsino;
      if (planoEnsino) {
        this.updateForm(planoEnsino);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const planoEnsino = this.planoEnsinoFormService.getPlanoEnsino(this.editForm);
    if (planoEnsino.id !== null) {
      this.subscribeToSaveResponse(this.planoEnsinoService.update(planoEnsino));
    } else {
      this.subscribeToSaveResponse(this.planoEnsinoService.create(planoEnsino));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlanoEnsino>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(planoEnsino: IPlanoEnsino): void {
    this.planoEnsino = planoEnsino;
    this.planoEnsinoFormService.resetForm(this.editForm, planoEnsino);

    this.disciplinasSharedCollection = this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(
      this.disciplinasSharedCollection,
      planoEnsino.disciplina,
    );
    this.professorsSharedCollection = this.professorService.addProfessorToCollectionIfMissing<IProfessor>(
      this.professorsSharedCollection,
      planoEnsino.professorResponsavel,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.disciplinaService
      .query()
      .pipe(map((res: HttpResponse<IDisciplina[]>) => res.body ?? []))
      .pipe(
        map((disciplinas: IDisciplina[]) =>
          this.disciplinaService.addDisciplinaToCollectionIfMissing<IDisciplina>(disciplinas, this.planoEnsino?.disciplina),
        ),
      )
      .subscribe((disciplinas: IDisciplina[]) => (this.disciplinasSharedCollection = disciplinas));

    this.professorService
      .query()
      .pipe(map((res: HttpResponse<IProfessor[]>) => res.body ?? []))
      .pipe(
        map((professors: IProfessor[]) =>
          this.professorService.addProfessorToCollectionIfMissing<IProfessor>(professors, this.planoEnsino?.professorResponsavel),
        ),
      )
      .subscribe((professors: IProfessor[]) => (this.professorsSharedCollection = professors));
  }
}
