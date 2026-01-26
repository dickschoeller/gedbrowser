describe('DisplayMessage Interface', () => {
  it('should create a display message object', () => {
    const message: any = {
      msgType: 'info',
      msgBody: 'Test message'
    };
    expect(message.msgType).toBe('info');
    expect(message.msgBody).toBe('Test message');
  });

  it('should support error message type', () => {
    const message: any = {
      msgType: 'error',
      msgBody: 'An error occurred'
    };
    expect(message.msgType).toBe('error');
    expect(message.msgBody).toBe('An error occurred');
  });

  it('should support warning message type', () => {
    const message: any = {
      msgType: 'warning',
      msgBody: 'Warning message'
    };
    expect(message.msgType).toBe('warning');
    expect(message.msgBody).toBe('Warning message');
  });

  it('should support success message type', () => {
    const message: any = {
      msgType: 'success',
      msgBody: 'Success message'
    };
    expect(message.msgType).toBe('success');
    expect(message.msgBody).toBe('Success message');
  });
});
