import { LinkDialogComponent } from '../components/link-dialog/link-dialog.component';
import { LinkDialogData } from '../models';
import { LinkHelper } from './link-helper';

export class DialogHelper {
  private static open(that: any, title: string) {
    return that.dialog.open(
      LinkDialogComponent,
      {
        data: { name: title, dataset: that.dataset }
      });
  }

  public static openLinkDialog(that: any, title: string, lh: LinkHelper) {
    const dialogRef = DialogHelper.open(that, title);

    dialogRef.afterOpen().subscribe(() => {
      lh.onLinkDialogOpen(that.service, dialogRef.componentInstance);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (!result || !result.selected) {
        return;
      }
      lh.link(result, that.parent.attributes, () => that.parent.save());
    });
  }

  public static openUnlinkDialog(that: any, title: string, lh: LinkHelper) {
    const dialogRef = DialogHelper.open(that, title);

    dialogRef.afterOpen().subscribe(() => {
      lh.onUnlinkDialogOpen(that.service, dialogRef.componentInstance, that.parent.attributes);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (!result || !result.selected) {
        return;
      }
      lh.unlink(result, that.parent.attributes, () => that.parent.save());
    });
  }
}
