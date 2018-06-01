import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {BaseDialog} from '../../bases';
import {ApiPerson, LinkPersonItem, LinkPersonDialogData} from '../../models';

@Component({
  selector: 'app-link-person-dialog',
  templateUrl: './link-person-dialog.component.html',
  styleUrls: ['./link-person-dialog.component.css']
})
export class LinkPersonDialogComponent
  extends BaseDialog<LinkPersonDialogData, LinkPersonDialogComponent>
  implements OnInit, OnChanges {
  @Input() titleString: string;
  dataset: string;
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
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
  }

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiPerson, b: ApiPerson) {
    const val = a.indexName.localeCompare(b.indexName);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  };
}
