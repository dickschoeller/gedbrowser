import { StringUtil } from '.';

export class NameUtil {
  cleanup(name: string): string {
    const su = new StringUtil();
    return su.replaceAll(su.replaceAll(name, '/', ' '), '  ', ' ').trim();
  }
}
