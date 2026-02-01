import { describe, it, expect, beforeEach } from 'vitest';
import { SubmitterButtonComponent } from './submitter-button.component';
import { SubmitterService } from '../../services';
import {
  setupResourceButtonTest,
  describeResourceButtonComponent
} from '../testing/button-component-spec-helpers';

describe('SubmitterButtonComponent', () => {
  beforeEach(() => {
    setupResourceButtonTest({
      componentClass: SubmitterButtonComponent,
      serviceClass: SubmitterService,
      resourceName: 'submitter'
    });
  });

  describeResourceButtonComponent(
    {
      componentClass: SubmitterButtonComponent,
      serviceClass: SubmitterService,
      resourceName: 'submitter'
    },
    describe,
    it,
    expect
  );
});
