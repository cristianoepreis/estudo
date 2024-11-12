import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../plano-ensino.test-samples';

import { PlanoEnsinoFormService } from './plano-ensino-form.service';

describe('PlanoEnsino Form Service', () => {
  let service: PlanoEnsinoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoEnsinoFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoEnsinoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoEnsinoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ementa: expect.any(Object),
            bibliografiaBasica: expect.any(Object),
            bibliografiaComplementar: expect.any(Object),
            praticaLaboratorial: expect.any(Object),
            disciplina: expect.any(Object),
            professorResponsavel: expect.any(Object),
          }),
        );
      });

      it('passing IPlanoEnsino should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoEnsinoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ementa: expect.any(Object),
            bibliografiaBasica: expect.any(Object),
            bibliografiaComplementar: expect.any(Object),
            praticaLaboratorial: expect.any(Object),
            disciplina: expect.any(Object),
            professorResponsavel: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlanoEnsino', () => {
      it('should return NewPlanoEnsino for default PlanoEnsino initial value', () => {
        const formGroup = service.createPlanoEnsinoFormGroup(sampleWithNewData);

        const planoEnsino = service.getPlanoEnsino(formGroup) as any;

        expect(planoEnsino).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoEnsino for empty PlanoEnsino initial value', () => {
        const formGroup = service.createPlanoEnsinoFormGroup();

        const planoEnsino = service.getPlanoEnsino(formGroup) as any;

        expect(planoEnsino).toMatchObject({});
      });

      it('should return IPlanoEnsino', () => {
        const formGroup = service.createPlanoEnsinoFormGroup(sampleWithRequiredData);

        const planoEnsino = service.getPlanoEnsino(formGroup) as any;

        expect(planoEnsino).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoEnsino should not enable id FormControl', () => {
        const formGroup = service.createPlanoEnsinoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoEnsino should disable id FormControl', () => {
        const formGroup = service.createPlanoEnsinoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
