import { Component, Input, EventEmitter, Output, } from '@angular/core';

import { LinkPersonDialogComponent } from '../../components';
import { LinkCheck } from '../../interfaces/link-check';
import { LinkPersonDialogData } from '../../models';
import { PersonService } from '../../services';
import { LinkPersonHelper } from '../../utils';

@Component({
  selector: 'app-link-person',
  templateUrl: './link-person.component.html',
  styleUrls: ['./link-person.component.css']
})
export class LinkPersonComponent {
  @Input() dataset: string;
  @Input() parent: LinkCheck;
  @Input() multi: boolean;
  @Input() label: string;
  @Output() emitOK = new EventEmitter<LinkPersonDialogData>();

  constructor(private personService: PersonService) { }

  displayDialog = false;
  lph = new LinkPersonHelper(this.personService);

  openDialog(): void {
    this.displayDialog = true;
  }

  onDialogClose(): void {
    this.displayDialog = false;
  }

  onDialogOpen(data: LinkPersonDialogComponent): void {
    this.dataset = data.dataset;
    this.lph.onLinkChildDialogOpen(data, this.parent);
  }

  onDialogOK(data: LinkPersonDialogData): void {
    this.displayDialog = false;
    this.emitOK.emit(data);
  }
}
