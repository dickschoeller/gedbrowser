import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { vi } from 'vitest';
import { LinkPersonComponent } from './link-person.component';
import { PersonService } from '../../services';
import { MatDialog } from '@angular/material/dialog';
import { LinkPersonDialogComponent } from '../../components';
import { LinkPersonDialogData } from '../../models';

describe('LinkPersonComponent', () => {
  let component: LinkPersonComponent;
  let fixture: ComponentFixture<LinkPersonComponent>;

  const personServiceMock = {} as unknown as PersonService;

  const parentMock = {
    spouseLinked: vi.fn().mockReturnValue(false),
    childLinked: vi.fn().mockReturnValue(false),
  } as any;

  const dialogRefBase = (data: Partial<LinkPersonDialogData> = {}, closedValue?: LinkPersonDialogData) => {
    const dialogData: LinkPersonDialogData = {
      dataset: 'dialogDs',
      titleString: 'Dialog Title',
      multi: true,
      items: [],
      selected: [],
      ...data,
    } as LinkPersonDialogData;

    return {
      componentInstance: { data: dialogData },
      afterOpened: vi.fn(() => of(null)),
      afterClosed: vi.fn(() => of(closedValue)),
    } as any;
  };

  let dialogRefMock: any;
  const dialogOpenMock = vi.fn((comp, options) => dialogRefMock);

  const mockDialog = {
    open: dialogOpenMock,
  } as unknown as MatDialog;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [LinkPersonComponent],
      providers: [
        { provide: PersonService, useValue: personServiceMock },
        { provide: MatDialog, useValue: mockDialog },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LinkPersonComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDs';
    component.parent = parentMock;
    component.label = 'Link parent';
    component.multi = true;
    component.color = 'primary';
    component.lph = {
      onLinkChildDialogOpen: vi.fn(),
    } as any;
    dialogOpenMock.mockClear();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('opens dialog with expected parameters', () => {
    dialogRefMock = dialogRefBase();

    component.openDialog();

    expect(dialogOpenMock).toHaveBeenCalledWith(LinkPersonDialogComponent, {
      data: { dataset: 'testDs', titleString: 'Link parent', multi: true },
    });
  });

  it('updates dataset and invokes helper on afterOpened', () => {
    dialogRefMock = dialogRefBase({ dataset: 'fromDialog' });

    component.openDialog();

    expect(component.dataset).toBe('fromDialog');
    expect(component.lph.onLinkChildDialogOpen).toHaveBeenCalledWith(dialogRefMock.componentInstance, parentMock);
  });

  it('emits emitOK when dialog closes with result', () => {
    const emitted: LinkPersonDialogData[] = [];
    dialogRefMock = dialogRefBase({ dataset: 'fromDialog' }, { dataset: 'out', titleString: 't', multi: true, items: [], selected: [] } as LinkPersonDialogData);
    component.emitOK.subscribe((val) => emitted.push(val));

    component.openDialog();

    expect(emitted.length).toBe(1);
    expect(emitted[0].dataset).toBe('out');
  });

  it('does not emit when dialog closes undefined', () => {
    const emitted: LinkPersonDialogData[] = [];
    dialogRefMock = dialogRefBase({ dataset: 'fromDialog' }, undefined);
    component.emitOK.subscribe((val) => emitted.push(val));

    component.openDialog();

    expect(emitted.length).toBe(0);
  });
});
