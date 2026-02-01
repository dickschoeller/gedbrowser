import { describe, it, expect, beforeEach } from 'vitest';
import { SourceButtonComponent } from './source-button.component';
import { SourceService } from '../../services';
import {
  setupResourceButtonTest,
  describeResourceButtonComponent
} from '../testing/button-component-spec-helpers';

describe('SourceButtonComponent', () => {
  beforeEach(() => {
    setupResourceButtonTest({
      componentClass: SourceButtonComponent,
      serviceClass: SourceService,
      resourceName: 'source'
    });
  });

  describeResourceButtonComponent(
    {
      componentClass: SourceButtonComponent,
      serviceClass: SourceService,
      resourceName: 'source'
    },
    describe,
    it,
    expect
  );
});
