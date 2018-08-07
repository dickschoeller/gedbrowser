import { RefreshNote } from '../../interfaces';
import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiNote } from '../../models';
import { NoteService } from '../../services';
import { ApiComparators } from '../../utils';

@Component({
  selector: 'app-note-list-page',
  templateUrl: './note-list-page.component.html',
  styleUrls: ['./note-list-page.component.css']
})
export class NoteListPageComponent implements OnInit, OnChanges, RefreshNote {
  dataset: string;
  notes: Array<ApiNote>;

  constructor(private route: ActivatedRoute,
    private noteService: NoteService,
    private router: Router) { }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  private init(): void {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, notes: ApiNote[]}) => {
        this.notes = data.notes;
        this.notes.sort(ApiComparators.compareNotes);
      }
    );
  }

  refreshNote(note: ApiNote): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.noteService.getAll(this.dataset).subscribe(
      (notes: Array<ApiNote>) => {
        this.notes = notes.sort(ApiComparators.compareNotes);
      }
    );
  }
}
