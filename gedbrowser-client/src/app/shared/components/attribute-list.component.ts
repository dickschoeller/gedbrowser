import {Component, OnInit, Input} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';

import {ApiAttribute} from '../../shared/models';
import {AttributeDialogComponent} from './attribute-dialog.component';

@Component({
  selector: 'app-attribute-list',
  templateUrl: './attribute-list.component.html',
  styleUrls: ['./attribute-list.component.css']
})
export class AttributeListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  createAttribute(): void {
    const config = {
      width: '500px',
      height: '600px',
      data: new ApiAttribute()
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, config);

    dialogRef.afterClosed().subscribe(result => {
      const attribute: ApiAttribute = result;
      alert('attribute: ' + attribute.string);
    });
  }
}
