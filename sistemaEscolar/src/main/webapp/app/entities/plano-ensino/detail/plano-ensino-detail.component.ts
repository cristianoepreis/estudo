import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IPlanoEnsino } from '../plano-ensino.model';

@Component({
  standalone: true,
  selector: 'jhi-plano-ensino-detail',
  templateUrl: './plano-ensino-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PlanoEnsinoDetailComponent {
  planoEnsino = input<IPlanoEnsino | null>(null);

  previousState(): void {
    window.history.back();
  }
}
