import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { BaseDialog } from '../../bases';
import { LinkDialogInterface } from '../../interfaces';
import { LinkDialogData } from '../../models';

@Component({
  selector: 'app-link-dialog',
  templateUrl: './link-dialog.component.html',
  styleUrls: ['./link-dialog.component.css']
})
export class LinkDialogComponent
  extends BaseDialog<LinkDialogData, LinkDialogComponent>
  implements OnInit, OnChanges, LinkDialogInterface {
  @Input() titleString: string;
  dataset: string;
  objects: Array<any>;

  _data: LinkDialogData = new LinkDialogData();

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
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
  }
}
