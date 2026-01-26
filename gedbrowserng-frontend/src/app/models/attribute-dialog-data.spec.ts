describe('AttributeDialogData Interface', () => {
  it('should create an attribute dialog data object', () => {
    const data: any = {
      insert: true,
      index: 0,
      type: 'BIRT',
      text: 'Birth Date',
      date: '1 JAN 2000',
      place: 'Boston, MA',
      note: 'A note',
      originalType: 'BIRT',
      originalText: 'Birth Date',
      originalDate: '1 JAN 2000',
      originalPlace: 'Boston, MA',
      originalNote: 'A note'
    };
    expect(data.insert).toBe(true);
    expect(data.type).toBe('BIRT');
  });

  it('should support update mode', () => {
    const data: any = {
      insert: false,
      index: 5,
      type: 'DEAT',
      text: 'Death',
      date: '1 JAN 2020',
      place: 'Los Angeles, CA',
      note: 'Death note',
      originalType: 'DEAT',
      originalText: 'Death',
      originalDate: '1 JAN 2020',
      originalPlace: 'Los Angeles, CA',
      originalNote: 'Death note'
    };
    expect(data.insert).toBe(false);
    expect(data.index).toBe(5);
  });

  it('should track original values', () => {
    const data: any = {
      insert: false,
      index: 0,
      type: 'NAME',
      text: 'New Name',
      date: '',
      place: '',
      note: '',
      originalType: 'NAME',
      originalText: 'Old Name',
      originalDate: '',
      originalPlace: '',
      originalNote: ''
    };
    expect(data.text).not.toBe(data.originalText);
    expect(data.type).toBe(data.originalType);
  });
});
