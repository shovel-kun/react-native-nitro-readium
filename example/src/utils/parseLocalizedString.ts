import type {LocalizedString} from 'react-native-nitro-readium';

export function parseLocalizedString(
  localizedString: LocalizedString,
  locale = 'en-US',
): string {
  if (typeof localizedString === 'string') {
    return localizedString;
  }

  if (locale in localizedString) {
    return localizedString[locale]!;
  }

  const firstLocale = Object.keys(localizedString)[0]!;
  return localizedString[firstLocale]!;
}
