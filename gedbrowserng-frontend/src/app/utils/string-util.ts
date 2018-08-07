export class StringUtil {
  public static truncate(input: string, length: number): string {
    if (input.trim().length <= length) {
      return input.trim();
    }
    return input.trim().substr(0, length) + '...';
  }

  /**
   * Change the input string to title case. For the most part, follow Chicago style.
   */
  public static titleCase (target: string): string {
    const strSplit = target.split(' ');
    for (let i = 0; i < strSplit.length; i++) {
      StringUtil.capitalizeSplit(strSplit, i);
    }
    return strSplit.join(' ');
  }

  private static capitalizeSplit(strSplit, i): void {
    let s: string = strSplit[i];
    if (i === 0 || i === strSplit.length - 1) {
      s = StringUtil.capitalize(s);
    } else if (!StringUtil.allcaps(s)) {
      // Note all caps wins over donotcap, otherwise 'OR' gets mishandled as 'or'.
      s = StringUtil.capitalize(s);
    } else if (StringUtil.donotcap(s)) {
      s = s.toLowerCase();
    }
    strSplit[i] = s;
  }

  /**
   * Change the first character to upper and the subsequent characters to lower case.
   */
  public static capitalize (target: string): string {
    return target.charAt(0).toUpperCase() + target.slice(1).toLowerCase();
  }

  /**
   * Check for words that are all lower case in Chicago style.
   */
  private static donotcap(target: string): boolean {
    const specials = [
      'a', 'an', 'the', 'and', 'but', 'or', 'for', 'nor',
      'on', 'in', 'at', 'since', 'for', 'before', 'to', 'until', 'by', 'beside', 'under', 'below',
      'over', 'above', 'across', 'through', 'into', 'towards', 'onto', 'from',
    ];
    return specials.includes(target.toLowerCase());
  }

  /**
   * Check for certain all capitalized situations. Only true if the input is all caps.
   */
  private static allcaps (target: string): boolean {
    const states = [
      'AL', 'AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE',
      'FL', 'GA', 'HI', 'ID', 'IL', 'IN', 'IA', 'KS',
      'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS',
      'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY',
      'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC',
      'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV',
      'WI', 'WY',
      'AS', 'DC', 'FM', 'GU', 'MH', 'MP', 'PW', 'PR',
      'VI',
    ];
    const specials = [ 'FBI', 'RPI', 'USA' ];
    // Only match if the target is already all upper
    return specials.includes(target) || states.includes(target);
  }

  /**
   * Replace all instance of the search string found in the target with the replacement
   * string.
   */
  public static replaceAll(target: string, search: string, replacement: string): string {
    return target.replace(new RegExp(search, 'g'), replacement);
  }

  public static isEmpty(input: string) {
    return input === null || input === undefined || input === '';
  }
}
