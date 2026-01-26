import { UrlBuilder } from './urlbuilder';

describe('UrlBuilder', () => {
  describe('constructor', () => {
    it('should create instance with db and type', () => {
      const builder = new UrlBuilder('mydb', 'persons');
      expect(builder.db).toBe('mydb');
      expect(builder.t).toBe('persons');
    });

    it('should create instance with sub parameter', () => {
      const builder = new UrlBuilder('mydb', 'persons', 'sub');
      expect(builder.sub).toBe('sub');
    });

    it('should handle empty db', () => {
      const builder = new UrlBuilder('', 'persons');
      expect(builder.db).toBe('');
    });

    it('should handle undefined db', () => {
      const builder = new UrlBuilder(undefined, 'persons');
      expect(builder.db).toBeUndefined();
    });
  });

  describe('baseUrl', () => {
    it('should return dbs endpoint when db is empty string', () => {
      const builder = new UrlBuilder('', 'persons');
      expect(builder.baseUrl()).toBe('/gedbrowserng/v1/dbs');
    });

    it('should return v1 endpoint when db is undefined', () => {
      const builder = new UrlBuilder(undefined, 'persons');
      expect(builder.baseUrl()).toBe('/gedbrowserng/v1');
    });

    it('should return db-specific endpoint', () => {
      const builder = new UrlBuilder('mydb', 'persons');
      expect(builder.baseUrl()).toBe('/gedbrowserng/v1/dbs/mydb');
    });
  });

  describe('plusT', () => {
    it('should combine baseUrl with type', () => {
      const builder = new UrlBuilder('mydb', 'persons');
      expect(builder.plusT()).toBe('/gedbrowserng/v1/dbs/mydb/persons');
    });

    it('should work with empty db', () => {
      const builder = new UrlBuilder('', 'persons');
      expect(builder.plusT()).toBe('/gedbrowserng/v1/dbs/persons');
    });
  });

  describe('plusId', () => {
    it('should add id and sub to base url', () => {
      const builder = new UrlBuilder('mydb', 'persons', 'notes');
      const result = builder.plusId('P001');
      expect(result).toContain('/gedbrowserng/v1/dbs/mydb/persons');
      expect(result).toContain('P001');
      expect(result).toContain('notes');
    });

    it('should work without sub', () => {
      const builder = new UrlBuilder('mydb', 'persons');
      const result = builder.plusId('P001');
      expect(result).toContain('P001');
    });
  });

  describe('url', () => {
    it('should return plusT when no ids', () => {
      const builder = new UrlBuilder('mydb', 'persons');
      expect(builder.url()).toBe(builder.plusT());
    });

    it('should return plusId for single id', () => {
      const builder = new UrlBuilder('mydb', 'persons', 'sub');
      const result = builder.url('P001');
      expect(result).toContain('P001');
      expect(result).toContain('sub');
    });

    it('should handle two ids', () => {
      const builder = new UrlBuilder('mydb', 'families', 'children');
      const result = builder.url('F001', 'C001');
      expect(result).toContain('F001');
      expect(result).toContain('C001');
    });

    it('should return proper structure for id and id2', () => {
      const builder = new UrlBuilder('mydb', 'persons', 'sub');
      const result = builder.url('P001', 'P002');
      expect(result).toContain('/');
      expect(result.indexOf('P001')).toBeLessThan(result.indexOf('P002'));
    });
  });
});
