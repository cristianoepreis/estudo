import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IDisciplina, NewDisciplina } from '../disciplina.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDisciplina for edit and NewDisciplinaFormGroupInput for create.
 */
type DisciplinaFormGroupInput = IDisciplina | PartialWithRequiredKeyOf<NewDisciplina>;

type DisciplinaFormDefaults = Pick<NewDisciplina, 'id' | 'cursos'>;

type DisciplinaFormGroupContent = {
  id: FormControl<IDisciplina['id'] | NewDisciplina['id']>;
  nome: FormControl<IDisciplina['nome']>;
  professorResponsavel: FormControl<IDisciplina['professorResponsavel']>;
  cursos: FormControl<IDisciplina['cursos']>;
};

export type DisciplinaFormGroup = FormGroup<DisciplinaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DisciplinaFormService {
  createDisciplinaFormGroup(disciplina: DisciplinaFormGroupInput = { id: null }): DisciplinaFormGroup {
    const disciplinaRawValue = {
      ...this.getFormDefaults(),
      ...disciplina,
    };
    return new FormGroup<DisciplinaFormGroupContent>({
      id: new FormControl(
        { value: disciplinaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(disciplinaRawValue.nome, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      professorResponsavel: new FormControl(disciplinaRawValue.professorResponsavel),
      cursos: new FormControl(disciplinaRawValue.cursos ?? []),
    });
  }

  getDisciplina(form: DisciplinaFormGroup): IDisciplina | NewDisciplina {
    return form.getRawValue() as IDisciplina | NewDisciplina;
  }

  resetForm(form: DisciplinaFormGroup, disciplina: DisciplinaFormGroupInput): void {
    const disciplinaRawValue = { ...this.getFormDefaults(), ...disciplina };
    form.reset(
      {
        ...disciplinaRawValue,
        id: { value: disciplinaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DisciplinaFormDefaults {
    return {
      id: null,
      cursos: [],
    };
  }
}
