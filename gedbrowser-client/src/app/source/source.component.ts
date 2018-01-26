import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import {
  ApiSource,
  AttributeListComponent,
  SourceService,
} from '../shared';

@Component({
  selector: 'app-source',
  templateUrl: './source.component.html',
  styleUrls: ['./source.component.css']
})
export class SourceComponent implements OnInit {
  source: ApiSource;

  constructor(private route: ActivatedRoute,
    private sourceService: SourceService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {source: ApiSource}) => {
        this.source = data.source;
      }
    );
  }
}
