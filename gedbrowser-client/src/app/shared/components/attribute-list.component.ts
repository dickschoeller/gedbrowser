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
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, {
        width: '500px',
        height: '600px',
        data: {type: 'Birth', text: '', date: '', place: '', note: ''}
      });

    dialogRef.afterClosed().subscribe(result => {
      const data = result;
    });
  }
}
