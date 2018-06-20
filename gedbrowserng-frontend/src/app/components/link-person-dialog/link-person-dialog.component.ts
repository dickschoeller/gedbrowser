import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { BaseDialog } from '../../bases';
import { ApiPerson, LinkPersonItem, LinkPersonDialogData } from '../../models';

@Component({
  selector: 'app-link-person-dialog',
  templateUrl: './link-person-dialog.component.html',
  styleUrls: ['./link-person-dialog.component.css']
})
export class LinkPersonDialogComponent
  extends BaseDialog<LinkPersonDialogData, LinkPersonDialogComponent>
  implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() titleString: string;
  @Input() multi: boolean;
  persons: Array<ApiPerson>;

  _data: LinkPersonDialogData = new LinkPersonDialogData();

  constructor(private route: ActivatedRoute,
    private router: Router) {
    super();
  }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  open() {
    this.emitOpen.emit(this);
  }

  private init(): void {
  }

}
