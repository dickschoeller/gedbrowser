import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '..';

@Component({
  selector: 'app-attribute-list',
  templateUrl: './attribute-list.component.html',
  styleUrls: ['./attribute-list.component.css']
})
export class AttributeListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;

  constructor() { }

  ngOnInit() {
  }
}
