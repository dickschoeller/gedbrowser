import { describe, it, expect, beforeEach } from 'vitest';
import { NoteButtonComponent } from './note-button.component';
import { NoteService } from '../../services';
import {
  setupResourceButtonTest,
  describeResourceButtonComponent
} from '../testing/button-component-spec-helpers';

describe('NoteButtonComponent', () => {
  beforeEach(() => {
    setupResourceButtonTest({
      componentClass: NoteButtonComponent,
      serviceClass: NoteService,
      resourceName: 'note'
    });
  });

  describeResourceButtonComponent(
    {
      componentClass: NoteButtonComponent,
      serviceClass: NoteService,
      resourceName: 'note'
    },
    describe,
    it,
    expect
  );
});
