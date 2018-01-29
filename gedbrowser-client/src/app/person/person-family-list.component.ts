import { Component, OnInit, Input } from '@angular/core';
import {
  ApiAttribute,
  ApiPerson,
} from '../shared';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  attributes: the attributes that refer to families
 *  person: the person this page is for
 */
@Component({
  selector: 'app-person-family-list',
  templateUrl: './person-family-list.component.html',
  styleUrls: ['./person-family-list.component.css']
})
export class PersonFamilyListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;
  @Input() person: ApiPerson;

  constructor() { }

  ngOnInit() {
  }

}
