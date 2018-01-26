import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../models';

@Component({
  selector: 'app-attribute',
  templateUrl: './attribute.component.html',
  styleUrls: ['./attribute.component.css']
})
export class AttributeComponent implements OnInit {
  @Input() attribute: ApiAttribute;

  constructor() { }

  ngOnInit() {
  }

}
