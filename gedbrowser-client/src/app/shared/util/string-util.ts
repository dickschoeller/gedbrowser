export class StringUtil {
  replaceAll = function(target, search, replacement) {
    return target.replace(new RegExp(search, 'g'), replacement);
  };

  capitalize = function(target: string) {
    return target.charAt(0).toUpperCase() + target.slice(1);
  };

  titleCase = function(target: string) {
    const strSplit = target.split(' ');
    for (let i = 0; i < strSplit.length; i++) {
      let s: string = strSplit[i];
      if (i === 0 || i === strSplit.length - 1) {
        s = this.capitalize(s);
      } else if (this.donotcap(s)) {
        s = s.toLowerCase();
      } else if (this.allcaps(s)) {
        s = s.toUpperCase();
      } else {
        s = this.capitalize(s);
      }
      strSplit[i] = s;
    }
    return strSplit.join(' ');
  };

  private donotcap = function(target: string): boolean {
    const specials = [
      'a', 'an', 'the', 'and', 'but', 'or', 'for', 'nor',
      'on', 'in', 'at', 'since', 'for', 'before', 'to', 'until', 'by', 'beside', 'under', 'below',
      'over', 'above', 'across', 'through', 'into', 'towards', 'onto', 'from',
    ];
    return specials.includes(target.toLowerCase());
  };

  private allcaps = function(target: string): boolean {
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
    const specials = [ 'FBI', 'RPI', 'USA', ''
    ];
    // Only match if the target is already all upper
    return specials.includes(target) || states.includes(target);
  };
}
