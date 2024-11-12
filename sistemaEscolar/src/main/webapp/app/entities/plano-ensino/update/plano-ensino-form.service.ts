import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPlanoEnsino, NewPlanoEnsino } from '../plano-ensino.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoEnsino for edit and NewPlanoEnsinoFormGroupInput for create.
 */
type PlanoEnsinoFormGroupInput = IPlanoEnsino | PartialWithRequiredKeyOf<NewPlanoEnsino>;

type PlanoEnsinoFormDefaults = Pick<NewPlanoEnsino, 'id'>;

type PlanoEnsinoFormGroupContent = {
  id: FormControl<IPlanoEnsino['id'] | NewPlanoEnsino['id']>;
  ementa: FormControl<IPlanoEnsino['ementa']>;
  bibliografiaBasica: FormControl<IPlanoEnsino['bibliografiaBasica']>;
  bibliografiaComplementar: FormControl<IPlanoEnsino['bibliografiaComplementar']>;
  praticaLaboratorial: FormControl<IPlanoEnsino['praticaLaboratorial']>;
  disciplina: FormControl<IPlanoEnsino['disciplina']>;
  professorResponsavel: FormControl<IPlanoEnsino['professorResponsavel']>;
};

export type PlanoEnsinoFormGroup = FormGroup<PlanoEnsinoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoEnsinoFormService {
  createPlanoEnsinoFormGroup(planoEnsino: PlanoEnsinoFormGroupInput = { id: null }): PlanoEnsinoFormGroup {
    const planoEnsinoRawValue = {
      ...this.getFormDefaults(),
      ...planoEnsino,
    };
    return new FormGroup<PlanoEnsinoFormGroupContent>({
      id: new FormControl(
        { value: planoEnsinoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ementa: new FormControl(planoEnsinoRawValue.ementa, {
        validators: [Validators.required, Validators.maxLength(2000)],
      }),
      bibliografiaBasica: new FormControl(planoEnsinoRawValue.bibliografiaBasica, {
        validators: [Validators.required, Validators.maxLength(2000)],
      }),
      bibliografiaComplementar: new FormControl(planoEnsinoRawValue.bibliografiaComplementar, {
        validators: [Validators.required, Validators.maxLength(2000)],
      }),
      praticaLaboratorial: new FormControl(planoEnsinoRawValue.praticaLaboratorial, {
        validators: [Validators.required, Validators.maxLength(2000)],
      }),
      disciplina: new FormControl(planoEnsinoRawValue.disciplina),
      professorResponsavel: new FormControl(planoEnsinoRawValue.professorResponsavel),
    });
  }

  getPlanoEnsino(form: PlanoEnsinoFormGroup): IPlanoEnsino | NewPlanoEnsino {
    return form.getRawValue() as IPlanoEnsino | NewPlanoEnsino;
  }

  resetForm(form: PlanoEnsinoFormGroup, planoEnsino: PlanoEnsinoFormGroupInput): void {
    const planoEnsinoRawValue = { ...this.getFormDefaults(), ...planoEnsino };
    form.reset(
      {
        ...planoEnsinoRawValue,
        id: { value: planoEnsinoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlanoEnsinoFormDefaults {
    return {
      id: null,
    };
  }
}
