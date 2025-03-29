import React, {useState, useRef, useCallback, useEffect} from 'react';
import {
  Button,
  Text,
  View,
  StyleSheet,
  Platform,
  Dimensions,
  LayoutChangeEvent,
  Image,
} from 'react-native';
import {
  Readium,
  Locator,
  Selection,
  Decoration,
  DecorationType,
  ActivatedDecoration,
  ReadiumRef,
  openPublication,
  LocalizedString,
  Contributor,
} from 'react-native-nitro-readium';
import {SelectionMenu} from './SelectionMenu';
import {DecorationMenu} from './DecorationMenu';
import {pick} from '@react-native-documents/picker';

// New component for displaying metadata
const MetadataScreen = ({metadata, coverImageUri, onClose}) => {
  return (
    <View style={metadataStyles.container}>
      <Text style={metadataStyles.title}>Metadata</Text>
      <Text style={metadataStyles.label}>Title:</Text>
      <Text style={metadataStyles.value}>{metadata.title || 'N/A'}</Text>
      <Text style={metadataStyles.label}>Author:</Text>
      <Text style={metadataStyles.value}>{metadata.author || 'N/A'}</Text>
      {coverImageUri && (
        <Image
          source={{uri: coverImageUri}}
          style={metadataStyles.coverImage}
        />
      )}
      <Button title="Close" onPress={onClose} />
    </View>
  );
};

const metadataStyles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 10,
  },
  value: {
    fontSize: 16,
    marginBottom: 10,
  },
  coverImage: {
    width: 200,
    height: 300,
    resizeMode: 'contain',
    marginTop: 20,
  },
});

function App(): React.JSX.Element {
  const [absolutePath, setAbsolutePath] = useState<string | null>(null);
  const [locator, setLocator] = useState<Locator | null>(null);
  const [selection, setSelection] = useState<Selection | null>(null);
  const [decorations, setDecorations] = useState<Decoration[]>([]);
  const [activatedDecoration, setActivatedDecoration] =
    useState<ActivatedDecoration | null>(null);
  const [viewDimensions, setViewDimensions] = useState({width: 0, height: 0});
  const readiumRef = useRef<ReadiumRef>(null);
  const [isMetadataVisible, setIsMetadataVisible] = useState(false);
  const [metadata, setMetadata] = useState({});
  const [coverImageUri, setCoverImageUri] = useState<string | undefined>(
    undefined,
  );

  const handleAddDecoration = useCallback(
    (type: DecorationType) => {
      if (!selection) return;

      const newDecoration: Decoration = {
        id: `decoration-${Date.now()}`,
        locator: selection.locator,
        style: {
          type,
          tint: type === 'highlight' ? 'FFFF00' : '0000FF', // Yellow for highlight, blue for underline
        },
      };

      setDecorations(prev => [...prev, newDecoration]);
      readiumRef.current?.clearSelection();
    },
    [selection, readiumRef],
  );

  const handleRemoveDecoration = useCallback((id: string) => {
    setDecorations(prev => prev.filter(decoration => decoration.id !== id));
    setActivatedDecoration(null);
  }, []);

  const handleTap = useCallback(
    (x: number, y: number) => {
      console.log('Tap detected');
      console.log(`x: ${x}, y: ${y}`);
      console.log(
        `width: ${viewDimensions.width}, height: ${viewDimensions.height}`,
      );
      console.log('Locator:', locator);

      const middleWidth = viewDimensions.width * 0.4; // Middle 40% of the width
      const middleHeight = viewDimensions.height * 0.4; // Middle 40% of the height

      const isInMiddleX =
        x > (viewDimensions.width - middleWidth) / 2 &&
        x < (viewDimensions.width + middleWidth) / 2;
      const isInMiddleY =
        y > (viewDimensions.height - middleHeight) / 2 &&
        y < (viewDimensions.height + middleHeight) / 2;

      if (isInMiddleX && isInMiddleY) {
        console.log('Tap is in the middle of the view');
      } else {
        console.log('Tap is not in the middle of the view');
      }

      setActivatedDecoration(null);
    },
    [viewDimensions, locator],
  );

  const onLocatorChanged = useCallback((locator: Locator) => {
    setLocator(locator);
    console.log(locator);
  }, []);

  const onSelectionChanged = useCallback(
    (selection: Selection | null) => {
      setSelection(selection);
      console.log(locator);
      console.log(selection);
    },
    [locator],
  );

  const onDecorationActivatedChanged = useCallback(
    (activatedDecoration: ActivatedDecoration) => {
      setActivatedDecoration(activatedDecoration);
    },
    [],
  );

  const onLayout = useCallback(
    (event: LayoutChangeEvent) => {
      console.log('layout changed');
      const {width, height} = event.nativeEvent.layout;
      setViewDimensions({width, height});
    },
    [locator],
  );

  const injectedJavascript: string = require('./script.raw.js');
  console.log('Injecting: ', injectedJavascript.substring(0, 100));

  const fetchMetadata = async () => {
    try {
      const [result] = await pick({
        mode: 'open',
      });
      if (result.uri) {
        console.log(result);
        const publication = await openPublication(result.uri);
        console.log(JSON.stringify(publication.manifest.metadata, null, 2));

        const title =
          parseLocalizedString(publication.manifest.metadata?.title) ||
          'Unknown Title';
        const author =
          readiumToAuthors(publication.manifest.metadata?.author) ||
          'Unknown Author';

        setMetadata({title, author});

        const coverUri = await publication.cover();
        setCoverImageUri(coverUri);

        setIsMetadataVisible(true);
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <View style={styles.container}>
      {!absolutePath && !isMetadataVisible && (
        <>
          <Button
            title="read epub in readium"
            onPress={async () => {
              try {
                const [result] = await pick({
                  mode: 'open',
                });
                if (result.uri) {
                  console.log(result);
                  setAbsolutePath(result.uri);
                  const manifest = await openPublication(result.uri);
                  console.log(manifest.tableOfContents);
                  console.log(await manifest.cover());
                }
              } catch (err) {
                console.error(err);
              }
            }}
          />
          <Button title="view epub metadata" onPress={fetchMetadata} />
        </>
      )}

      {isMetadataVisible && (
        <MetadataScreen
          metadata={metadata}
          coverImageUri={coverImageUri}
          onClose={() => setIsMetadataVisible(false)}
        />
      )}

      {absolutePath && !isMetadataVisible && (
        <>
          <Readium
            style={styles.view}
            absolutePath={absolutePath}
            onLocatorChanged={onLocatorChanged}
            decorations={decorations}
            onSelection={onSelectionChanged}
            onDecorationActivated={onDecorationActivatedChanged}
            injectedJavascriptOnResourcesLoad={injectedJavascript}
            onTap={({x, y}) => handleTap(x, y)}
            onMessage={console.log}
            onDrag={() => {
              setActivatedDecoration(null);
            }}
            onLayout={onLayout}
            preferences={{
              theme: 'dark',
              scroll: true,
            }}
            ref={readiumRef}
          />
          {selection && (
            <SelectionMenu
              selection={selection}
              onClose={() => null}
              onAddDecoration={handleAddDecoration}
            />
          )}
          {activatedDecoration && (
            <DecorationMenu
              activatedDecoration={activatedDecoration}
              onClose={() => null}
              onRemoveDecoration={handleRemoveDecoration}
            />
          )}
        </>
      )}
    </View>
  );
}

function parseLocalizedString(
  localizedString: LocalizedString,
  locale = 'en-US',
): string {
  if (typeof localizedString === 'string') {
    return localizedString;
  }

  if (locale in localizedString) {
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    return localizedString[locale]!;
  }

  const firstLocale =
    // Localized strings all have at least one locale
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    Object.keys(localizedString)[0]!;

  // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
  return localizedString[firstLocale]!;
}

function readiumToAuthors(
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

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  view: {
    width: '100%',
    height: '100%',
  },
});

export default App;
