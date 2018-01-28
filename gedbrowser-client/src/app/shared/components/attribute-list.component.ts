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

  /**
   * Remove family links and the first instance of name.
   * Those will be handled elsewhere.
   */
  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.attributes) {
      if (attribute.type !== 'fams' && attribute.type !== 'famc') {
        stripped.push(attribute);
      }
    }
    return stripped;
  }
}
