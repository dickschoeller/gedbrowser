import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';

import { SourceCreator } from './source-creator';
import { UrlBuilder } from '../utils';
import { ApiSource, NewSourceDialogData } from '../models';

class StubSourceService {
  posted = false;
  postLink(ub: UrlBuilder, anchor: string, source: ApiSource) {
    this.posted = true;
    return of(new ApiSource());
  }
}

class StubDialogRef<T> {
  constructor(private value: T | undefined) {}
  afterClosed() { return of(this.value); }
}

class StubDialog {
  result: any;
  open(_: any, __: any) { return new StubDialogRef(this.result); }
}

class TestSourceCreator extends SourceCreator {
  refreshed: ApiSource | null = null;
  constructor(public sourceService: StubSourceService, public dialog: StubDialog) { super(sourceService as any, dialog as any); }
  sourceUB(): UrlBuilder { return new UrlBuilder('ds', 'sources'); }
  sourceAnchor(): string { return 'S1'; }
  refreshSource(source: ApiSource): void { this.refreshed = source; }
}

describe('SourceCreator', () => {
  it('createSource no-ops on null/undefined', () => {
    const svc = new StubSourceService();
    const dlg = new StubDialog();
    const creator = new TestSourceCreator(svc, dlg);
    creator.createSource(null as unknown as NewSourceDialogData);
    expect(svc.posted).toBe(false);
    creator.createSource(undefined as unknown as NewSourceDialogData);
    expect(svc.posted).toBe(false);
  });

  it('createSource posts and refreshes on valid data', () => {
    const svc = new StubSourceService();
    const dlg = new StubDialog();
    const creator = new TestSourceCreator(svc, dlg);
    const data: NewSourceDialogData = { title: 'T', abbreviation: 'A', text: 'X' };
    creator.createSource(data);
    expect(svc.posted).toBe(true);
    expect(creator.refreshed).not.toBeNull();
  });

  it('openCreateSourceDialog handles undefined result and valid result', () => {
    const svc = new StubSourceService();
    const dlg = new StubDialog();
    const creator = new TestSourceCreator(svc, dlg);

    dlg.result = undefined;
    creator.openCreateSourceDialog();
    expect(svc.posted).toBe(false);

    dlg.result = { title: 'New', abbreviation: 'New', text: '' };
    creator.openCreateSourceDialog();
    expect(svc.posted).toBe(true);
  });
});
