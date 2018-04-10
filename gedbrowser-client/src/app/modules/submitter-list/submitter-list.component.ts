import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiSubmitter} from '../../models';
import {SubmitterService} from '../../services';

@Component({
  selector: 'app-submitter-list',
  templateUrl: './submitter-list.component.html',
  styleUrls: ['./submitter-list.component.css']
})
export class SubmitterListComponent implements OnInit {
  submitters: ApiSubmitter[];

  constructor(private route: ActivatedRoute,
    private submitterService: SubmitterService,
    private router: Router
  ) {}

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

  /**
   * Prepare the page display.
   *
   * Read submitters from the web service and sort using our comparator.
   */
  ngOnInit() {
    this.route.data.subscribe(
      (data: {submitters: ApiSubmitter[]}) => {
        this.submitters = data.submitters;
        this.submitters.sort(this.compare);
      }
    );
  }
}
