import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../shared';

/**
 * Implements a child list within a family on a person page.
 *
 * Inputs:
 *  children: the attributes referring to the children
 */
@Component({
  selector: 'app-person-family-child-list',
  templateUrl: './person-family-child-list.component.html',
  styleUrls: ['./person-family-child-list.component.css']
})
export class PersonFamilyChildListComponent implements OnInit {
  @Input() children: Array<ApiAttribute>;

  constructor() { }

  ngOnInit() {
  }

}
