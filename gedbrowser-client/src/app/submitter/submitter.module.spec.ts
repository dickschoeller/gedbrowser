import { SubmitterModule } from './submitter.module';

describe('SubmitterModule', () => {
  let submitterModule: SubmitterModule;

  beforeEach(() => {
    submitterModule = new SubmitterModule();
  });

  it('should create an instance', () => {
    expect(submitterModule).toBeTruthy();
  });
});
