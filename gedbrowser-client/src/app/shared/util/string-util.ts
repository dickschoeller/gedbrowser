export class StringUtil {
  replaceAll = function(target, search, replacement) {
    return target.replace(new RegExp(search, 'g'), replacement);
  };
}
