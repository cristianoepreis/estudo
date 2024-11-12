import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPlanoEnsino } from '../plano-ensino.model';
import { PlanoEnsinoService } from '../service/plano-ensino.service';

@Component({
  standalone: true,
  templateUrl: './plano-ensino-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PlanoEnsinoDeleteDialogComponent {
  planoEnsino?: IPlanoEnsino;

  protected planoEnsinoService = inject(PlanoEnsinoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.planoEnsinoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
