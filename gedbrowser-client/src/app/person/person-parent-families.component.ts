import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../shared';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;

  constructor() { }

  ngOnInit() {
  }
}
