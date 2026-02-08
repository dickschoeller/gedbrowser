import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';

import { SubmitterCreator } from './submitter-creator';
import { UrlBuilder } from '../utils';
import { ApiSubmitter, NewSubmitterDialogData } from '../models';

class StubSubmitterService {
  posted = false;
  postLink(ub: UrlBuilder, anchor: string, submitter: ApiSubmitter) {
    this.posted = true;
    return of(new ApiSubmitter());
  }
}

class StubDialogRef<T> {
  constructor(private readonly value: T | undefined) {}
  afterClosed() { return of(this.value); }
}

class StubDialog {
  result: any;
  open(_: any, __: any) { return new StubDialogRef(this.result); }
}

class TestSubmitterCreator extends SubmitterCreator {
  refreshed: ApiSubmitter | null = null;
  constructor(public submitterService: StubSubmitterService, public dialog: StubDialog) { super(submitterService as any, dialog as any); }
  submitterUB(): UrlBuilder { return new UrlBuilder('ds', 'submitters'); }
  submitterAnchor(): string { return 'U1'; }
  refreshSubmitter(submitter: ApiSubmitter): void { this.refreshed = submitter; }
}

describe('SubmitterCreator', () => {
  it('createSubmitter no-ops on null/undefined', () => {
    const svc = new StubSubmitterService();
    const dlg = new StubDialog();
    const creator = new TestSubmitterCreator(svc, dlg);
    creator.createSubmitter(null as unknown as NewSubmitterDialogData);
    expect(svc.posted).toBe(false);
    creator.createSubmitter(undefined as unknown as NewSubmitterDialogData);
    expect(svc.posted).toBe(false);
  });

  it('createSubmitter posts and refreshes on valid data', () => {
    const svc = new StubSubmitterService();
    const dlg = new StubDialog();
    const creator = new TestSubmitterCreator(svc, dlg);
    const data: NewSubmitterDialogData = { name: 'Alice' };
    creator.createSubmitter(data);
    expect(svc.posted).toBe(true);
    expect(creator.refreshed).not.toBeNull();
  });

  it('openCreateSubmitterDialog handles undefined result and valid result', () => {
    const svc = new StubSubmitterService();
    const dlg = new StubDialog();
    const creator = new TestSubmitterCreator(svc, dlg);

    dlg.result = undefined;
    creator.openCreateSubmitterDialog();
    expect(svc.posted).toBe(false);

    dlg.result = { name: 'New Submitter' };
    creator.openCreateSubmitterDialog();
    expect(svc.posted).toBe(true);
  });
});
