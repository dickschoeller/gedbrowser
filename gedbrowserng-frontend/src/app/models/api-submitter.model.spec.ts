import { ApiSubmitter } from './api-submitter.model';
import {
  describeModelInstantiation,
  describeApiObjectInheritance,
  describeStringProperty,
  describeMultipleInstances
} from './testing/api-model-spec-helpers';

describe('ApiSubmitter Model', () => {
  describeModelInstantiation('ApiSubmitter', ApiSubmitter);
  describeStringProperty(ApiSubmitter, 'name');
  describeApiObjectInheritance(ApiSubmitter);
  describeMultipleInstances(ApiSubmitter, 'name');
});
