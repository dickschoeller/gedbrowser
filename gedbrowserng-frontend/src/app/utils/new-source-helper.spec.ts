import { describe, it, expect } from 'vitest';
import { NewSourceHelper } from './new-source-helper';
import { ApiSource } from '../models';

describe('NewSourceHelper', () => {
  describe('buildSource', () => {
    it('creates a source with all provided data', () => {
      const data = {
        title: 'Birth Certificate',
        abbreviation: 'BC-1980',
        text: 'Official birth record from county clerk'
      };

      const source = NewSourceHelper.buildSource(data);

      expect(source).toBeInstanceOf(ApiSource);
      expect(source.attributes).toBeDefined();
      expect(source.attributes.length).toBe(3);
      
      // Check title
      const titleAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Title');
      expect(titleAttr).toBeDefined();
      expect(titleAttr?.tail).toBe('Birth Certificate');
      
      // Check abbreviation
      const abbrAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Abbreviation');
      expect(abbrAttr).toBeDefined();
      expect(abbrAttr?.tail).toBe('BC-1980');
      
      // Check text
      const textAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Text');
      expect(textAttr).toBeDefined();
      expect(textAttr?.tail).toBe('Official birth record from county clerk');
    });

    it('uses default title when title is empty', () => {
      const data = {
        title: '',
        abbreviation: 'ABC',
        text: 'Some text'
      };

      const source = NewSourceHelper.buildSource(data);
      const titleAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Title');
      expect(titleAttr?.tail).toBe('Person source');
    });

    it('handles empty abbreviation', () => {
      const data = {
        title: 'Census Record',
        abbreviation: '',
        text: 'Census data'
      };

      const source = NewSourceHelper.buildSource(data);
      const abbrAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Abbreviation');
      expect(abbrAttr).toBeDefined();
      expect(abbrAttr?.tail).toBe('');
    });

    it('handles empty text', () => {
      const data = {
        title: 'Family Bible',
        abbreviation: 'FB',
        text: ''
      };

      const source = NewSourceHelper.buildSource(data);
      const textAttr = source.attributes.find(a => a.type === 'attribute' && a.string === 'Text');
      expect(textAttr).toBeDefined();
      expect(textAttr?.tail).toBe('');
    });

    it('handles all empty fields except title', () => {
      const data = {
        title: 'Minimal Source',
        abbreviation: '',
        text: ''
      };

      const source = NewSourceHelper.buildSource(data);
      expect(source.attributes.length).toBe(3);
    });
  });

  describe('config', () => {
    it('wraps data in config object', () => {
      const input = { title: 'Test', abbreviation: 'TST', text: 'Test text' };
      const result = NewSourceHelper.config(input);
      expect(result).toEqual({ data: input });
    });
  });

  describe('initNew', () => {
    it('initializes new source data with title', () => {
      const result = NewSourceHelper.initNew('Marriage Record');
      expect(result).toEqual({
        title: 'Marriage Record',
        abbreviation: 'Marriage Record',
        text: ''
      });
    });

    it('initializes with empty title', () => {
      const result = NewSourceHelper.initNew('');
      expect(result).toEqual({
        title: '',
        abbreviation: '',
        text: ''
      });
    });
  });
});
