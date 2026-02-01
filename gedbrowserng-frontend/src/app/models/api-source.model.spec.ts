import { ApiSource } from './api-source.model';
import {
  describeModelInstantiation,
  describeApiObjectInheritance,
  describeStringProperty,
  describeArrayProperty
} from './testing/api-model-spec-helpers';

describe('ApiSource Model', () => {
  describeModelInstantiation('ApiSource', ApiSource);
  describeStringProperty(ApiSource, 'title');
  describeArrayProperty(ApiSource, 'images');
  describeApiObjectInheritance(ApiSource);
});
