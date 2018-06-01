import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {BaseDialog} from '../../bases';
import {ApiSource, LinkSourceItem, LinkSourceDialogData} from '../../models';

@Component({
  selector: 'app-link-source-dialog',
  templateUrl: './link-source-dialog.component.html',
  styleUrls: ['./link-source-dialog.component.css']
})
export class LinkSourceDialogComponent
  extends BaseDialog<LinkSourceDialogData, LinkSourceDialogComponent>
  implements OnInit, OnChanges {
  dataset: string;
  sources: Array<ApiSource>;

  _data: LinkSourceDialogData = new LinkSourceDialogData();

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

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiSource, b: ApiSource) {
    const val = a.title.localeCompare(b.title);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  };
}
