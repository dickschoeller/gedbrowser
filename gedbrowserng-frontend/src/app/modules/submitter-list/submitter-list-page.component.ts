import {Component, OnInit, OnChanges} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiSubmitter} from '../../models';
import {SubmitterService} from '../../services';

@Component({
  selector: 'app-submitter-list-page',
  templateUrl: './submitter-list-page.component.html',
  styleUrls: ['./submitter-list-page.component.css']
})
export class SubmitterListPageComponent implements OnInit, OnChanges {
  dataset: string;
  submitters: Array<ApiSubmitter>;

  constructor(private route: ActivatedRoute,
    private submitterService: SubmitterService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  init() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {submitters: ApiSubmitter[]}) => {
        this.submitters = data.submitters;
        this.submitters.sort(this.compare);
      }
    );
  }

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiSubmitter, b: ApiSubmitter) {
    const val = a.name.localeCompare(b.name);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  };

  refreshSubmitter(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.submitterService.getAll(this.dataset).subscribe(
      (submitters: Array<ApiSubmitter>) => {
        this.submitters = submitters.sort(this.compare);
        alert('there are ' + this.submitters.length + ' submitters');
      }
    );
  }
}
