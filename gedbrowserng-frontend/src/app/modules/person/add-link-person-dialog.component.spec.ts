import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { vi } from 'vitest';

import {
  AddLinkPersonDialogComponent,
  AddLinkPersonDialogData,
  AddLinkPersonDialogResult
} from './add-link-person-dialog.component';

describe('AddLinkPersonDialogComponent', () => {
  let component: AddLinkPersonDialogComponent;
  let fixture: ComponentFixture<AddLinkPersonDialogComponent>;
  let mockDialogRef: { close: ReturnType<typeof vi.fn> };

  const mockDialogData: AddLinkPersonDialogData = {
    title: 'Add spouse',
    defaultNewPerson: {
      name: 'Default Name',
      sex: 'F',
      birthDate: '',
      birthPlace: '',
      deathDate: '',
      deathPlace: ''
    }
  };

  beforeEach(async () => {
    mockDialogRef = { close: vi.fn() };

    await TestBed.configureTestingModule({
      imports: [AddLinkPersonDialogComponent],
      providers: [
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: mockDialogData }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddLinkPersonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('copies default new person data on init', () => {
    expect(component.newPersonData).toEqual(mockDialogData.defaultNewPerson);
    expect(component.newPersonData).not.toBe(mockDialogData.defaultNewPerson);
  });

  it('closes with mode new on submit from create tab', () => {
    component.selectedTabIndex = 0;
    component.newPersonData.name = 'Created Person';

    component.submit();

    expect(mockDialogRef.close).toHaveBeenCalledWith({
      mode: 'new',
      newPersonData: component.newPersonData
    } as AddLinkPersonDialogResult);
  });

  it('closes with mode existing and trimmed id on submit from link tab', () => {
    component.selectedTabIndex = 1;
    component.existingPersonId = '  I1234  ';

    component.submit();

    expect(mockDialogRef.close).toHaveBeenCalledWith({
      mode: 'existing',
      existingPersonId: 'I1234'
    } as AddLinkPersonDialogResult);
  });

  it('isSubmitDisabled is false on create tab', () => {
    component.selectedTabIndex = 0;
    component.existingPersonId = '';

    expect(component.isSubmitDisabled()).toBe(false);
  });

  it('isSubmitDisabled is true on link tab without id', () => {
    component.selectedTabIndex = 1;
    component.existingPersonId = '   ';

    expect(component.isSubmitDisabled()).toBe(true);
  });

  it('isSubmitDisabled is false on link tab with id', () => {
    component.selectedTabIndex = 1;
    component.existingPersonId = 'I4567';

    expect(component.isSubmitDisabled()).toBe(false);
  });

  it('onNoClick closes dialog', () => {
    component.onNoClick();

    expect(mockDialogRef.close).toHaveBeenCalled();
  });
});
