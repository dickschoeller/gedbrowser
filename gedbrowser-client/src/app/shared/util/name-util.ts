import { StringUtil } from './string-util';

export class NameUtil {
  cleanup(name: string): string {
    const su = new StringUtil();
    return su.replaceAll(su.replaceAll(name, '/', ' '), '  ', ' ').trim();
  }
}
