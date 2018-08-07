import { StringUtil } from './string-util';

export class NameUtil {
  public static cleanup(name: string): string {
    return StringUtil.replaceAll(StringUtil.replaceAll(name, '/', ' '), '  ', ' ').trim();
  }
}
