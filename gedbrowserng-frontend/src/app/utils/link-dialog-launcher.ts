import { LinkDialogComponent } from '../components/link-dialog/link-dialog.component';
import { LinkDialogData } from '../models';
import { LinkDialogActions } from './link-dialog-actions';

export class LinkDialogLauncher {
  private static open(that: any, title: string) {
    return that.dialog.open(
      LinkDialogComponent,
      {
        data: { name: title, dataset: that.dataset }
      });
  }

  public static openDialog(that: any, title: string, lh: LinkDialogActions) {
    const dialogRef = LinkDialogLauncher.open(that, title);

    dialogRef.afterOpen().subscribe(() => {
      lh.onOpen(that.service, dialogRef.componentInstance, that.parent.attributes);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (!result || !result.selected) {
        return;
      }
      lh.onOK(result, that.parent.attributes, () => that.parent.save());
    });
  }
}
