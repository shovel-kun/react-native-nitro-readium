import type {Contributor} from 'react-native-nitro-readium';
import {parseLocalizedString} from './parseLocalizedString';

export function parseAuthors(
  author: Contributor[] | Contributor | undefined,
): string | undefined {
  if (!author) return undefined;

  const rawAuthors = Array.isArray(author) ? author : [author];

  const parsedAuthors = rawAuthors.map<string>(a => {
    if (typeof a === 'string') {
      return parseLocalizedString(a);
    }
    return parseLocalizedString(a.name);
  });

  return parsedAuthors.join(', ');
}
