import { SourceModule } from './source.module';

describe('SourceModule', () => {
  let sourceModule: SourceModule;

  beforeEach(() => {
    sourceModule = new SourceModule();
  });

  it('should create an instance', () => {
    expect(sourceModule).toBeTruthy();
  });
});
