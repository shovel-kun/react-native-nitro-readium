import React, {useState, useRef, useCallback, useEffect} from 'react';
import {
  Button,
  Text,
  View,
  StyleSheet,
  Image,
  LayoutChangeEvent,
  FlatList,
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
  Manifest,
} from 'react-native-nitro-readium';
import {SelectionMenu} from './SelectionMenu';
import {DecorationMenu} from './DecorationMenu';
import {pick, pickDirectory} from '@react-native-documents/picker';
import {UniFile, fromUri} from 'react-native-unifile';
import {
  createStaticNavigation,
  useFocusEffect,
  useNavigation,
} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import type {
  StaticParamList,
  StaticScreenProps,
} from '@react-navigation/native';

interface Book {
  manifest: Manifest;
  coverUri: string;
}

type BookListScreenProps = StaticScreenProps<{
  books: Book[];
}>;

type ReadiumViewerScreenProps = StaticScreenProps<{
  absolutePath: string;
}>;

type MetadataScreenProps = StaticScreenProps<{
  manifest: Manifest;
  coverImageUri: string;
}>;

type HomeScreenProps = StaticScreenProps<{}>;

const BookListScreen = ({route}: BookListScreenProps) => {
  const navigation = useNavigation();
  const {books, isAutoScan} = route.params;
  // CHANGE ME!
  const TIME_BETWEEN_SCANS = 50;

  useEffect(() => {
    if (isAutoScan) {
      const timer = setTimeout(() => {
        navigation.goBack();
      }, TIME_BETWEEN_SCANS);

      return () => clearTimeout(timer); // Cleanup on unmount
    }
  }, []);

  const renderItem = ({item}: {item: Book}) => (
    <View style={bookListStyles.itemContainer}>
      {item.coverUri && (
        <Image
          source={{uri: item.coverUri}}
          style={bookListStyles.coverImage}
        />
      )}
      <View style={bookListStyles.textContainer}>
        <Text style={bookListStyles.title}>
          {parseLocalizedString(item.manifest.metadata.title) ||
            'Unknown Title'}
        </Text>
        <Text style={bookListStyles.author}>
          {readiumToAuthors(item.manifest.metadata.author) || 'Unknown Author'}
        </Text>
      </View>
      <Button
        title="View"
        onPress={() =>
          navigation.navigate('Metadata', {
            manifest: item.manifest,
            coverImageUri: item.coverUri,
          })
        }
      />
    </View>
  );

  return (
    <FlatList
      data={books}
      renderItem={renderItem}
      keyExtractor={(item, index) => index.toString()}
      contentContainerStyle={bookListStyles.listContainer}
    />
  );
};

const MetadataScreen = ({route}: MetadataScreenProps) => {
  const {coverImageUri, manifest} = route.params;
  const navigation = useNavigation();

  return (
    <View style={metadataStyles.container}>
      <Text style={metadataStyles.title}>Metadata</Text>
      <Text style={metadataStyles.label}>Title:</Text>
      <Text style={metadataStyles.value}>
        {parseLocalizedString(manifest.metadata.title) || 'N/A'}
      </Text>
      <Text style={metadataStyles.label}>Author:</Text>
      <Text style={metadataStyles.value}>
        {readiumToAuthors(manifest.metadata.author) || 'N/A'}
      </Text>
      {coverImageUri && (
        <Image
          source={{uri: coverImageUri}}
          style={metadataStyles.coverImage}
        />
      )}
      <Button title="Close" onPress={() => navigation.goBack()} />
    </View>
  );
};

const ReadiumViewerScreen = ({route}: ReadiumViewerScreenProps) => {
  const {absolutePath} = route.params;
  const readiumRef = useRef<ReadiumRef>(null);
  const [locator, setLocator] = useState<Locator | null>(null);
  const [selection, setSelection] = useState<Selection | null>(null);
  const [decorations, setDecorations] = useState<Decoration[]>([]);
  const [activatedDecoration, setActivatedDecoration] =
    useState<ActivatedDecoration | null>(null);
  const [viewDimensions, setViewDimensions] = useState({width: 0, height: 0});

  const handleAddDecoration = useCallback(
    (type: DecorationType) => {
      if (!selection) return;

      const newDecoration: Decoration = {
        id: `decoration-${Date.now()}`,
        locator: selection.locator,
        style: {
          type,
          tint: type === 'highlight' ? 'FFFF00' : '0000FF',
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

      const middleWidth = viewDimensions.width * 0.4;
      const middleHeight = viewDimensions.height * 0.4;

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
    // console.log(locator);
  }, []);

  const onSelectionChanged = useCallback(
    (selection: Selection | null) => {
      setSelection(selection);
      // console.log(locator);
      // console.log(selection);
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

  return (
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
        onMessage={message => console.log(`From Kotlin: ${message}`)}
        onDrag={() => {
          setActivatedDecoration(null);
        }}
        //@ts-expect-error remove once nitro fixes the type
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
  );
};

const HomeScreen = ({}: HomeScreenProps) => {
  const navigation = useNavigation();
  const [pickedDirectory, setPickedDirectory] = useState<string | null>(null);
  const [isButtonToggled, setIsButtonToggled] = useState(false);
  const scanCountRef = useRef(0);

  // DEBUG
  useFocusEffect(
    React.useCallback(() => {
      if (isButtonToggled) {
        fetchBooksInDirectory();
      }
    }, [isButtonToggled]),
  );

  const fetchMetadata = async () => {
    try {
      const [result] = await pick({
        mode: 'open',
      });
      if (result.uri) {
        const publication = await openPublication(result.uri);
        const coverUri = await publication.cover();

        navigation.navigate('Metadata', {
          manifest: publication.manifest,
          coverImageUri: coverUri,
        });
      }
    } catch (err) {
      console.error(err);
    }
  };

  const fetchBooksInDirectory = async () => {
    console.log('Starting to fetch books...');

    function getAllEpubFiles(unifile: UniFile): UniFile[] {
      console.log(`Scanning directory: ${unifile.fileName || 'root'}`);
      const files: UniFile[] = [];
      const contents = unifile.listFiles();

      for (const [index, item] of contents.entries()) {
        if (item.isDirectory) {
          const subDirFiles = getAllEpubFiles(item);
          files.push(...subDirFiles);
        } else if (item.isFile && item.extension === 'epub') {
          files.push(item);
        }
      }

      return files;
    }

    if (!pickedDirectory) {
      console.log('No directory picked, requesting directory selection...');
      const result = await pickDirectory({
        requestLongTermAccess: true,
      });
      if (result.uri) {
        console.log(`Directory selected: ${result.uri}`);
        setPickedDirectory(result.uri);
      } else {
        console.log('Directory selection cancelled');
        return;
      }
    } else {
      console.log(`Using previously picked directory: ${pickedDirectory}`);
    }

    if (pickedDirectory) {
      console.log('Creating UniFile from directory URI...');
      const unifile = fromUri(pickedDirectory);
      if (!unifile) {
        console.error('Failed to create UniFile from directory URI');
        return;
      }

      const files = getAllEpubFiles(unifile);

      const books: Book[] = [];

      console.log('Starting to process EPUB files...');
      for (const [index, file] of files.entries()) {
        console.log(
          `Processing EPUB ${index + 1}/${files.length}: ${file.fileName}`,
        );
        try {
          const publication = await openPublication(file.uri);

          // TODO: return null instead of throwing an error
          const coverUri = await publication.cover();

          books.push({
            manifest: publication.manifest,
            coverUri: coverUri,
          });
          console.log(`Successfully processed: ${file.fileName}`);
        } catch (error) {
          console.error(`Error processing ${file.fileName}:`, error);
        }
      }

      console.log(
        `Finished processing all files. Found ${books.length} valid books.`,
      );

      // DEBUG
      scanCountRef.current += 1; // Update the ref
      console.log('Scan count:', scanCountRef.current);

      navigation.navigate('BookList', {
        books,
        isAutoScan: isButtonToggled,
      });
    }
  };

  const readEpubInReadium = async () => {
    try {
      const [result] = await pick({
        mode: 'open',
      });
      if (result.uri) {
        navigation.navigate('ReadiumViewer', {absolutePath: result.uri});
      } else {
        console.log('No file selected');
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <View style={styles.container}>
      <>
        <Button title="read epub in readium" onPress={readEpubInReadium} />
        <Button title="view epub metadata" onPress={fetchMetadata} />
        <Button
          title="view epubs in directory"
          onPress={fetchBooksInDirectory}
        />
        <Button
          title={isButtonToggled ? 'Scanning...' : 'Start Auto Scan'}
          onPress={() => setIsButtonToggled(true)}
          disabled={!pickedDirectory || isButtonToggled}
          color={isButtonToggled ? 'green' : undefined}
        />
      </>
    </View>
  );
};

const RootStack = createNativeStackNavigator({
  initialRouteName: 'Home',
  screens: {
    Home: HomeScreen,
    Metadata: MetadataScreen,
    BookList: {
      screen: BookListScreen,
      options: {
        title: 'Books',
      },
    },
    ReadiumViewer: {
      screen: ReadiumViewerScreen,
      options: {headerShown: false},
    },
  },
});

const Navigation = createStaticNavigation(RootStack);

function parseLocalizedString(
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

const bookListStyles = StyleSheet.create({
  listContainer: {
    padding: 16,
  },
  itemContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 12,
    marginBottom: 12,
    backgroundColor: '#f5f5f5',
    borderRadius: 8,
  },
  coverImage: {
    width: 50,
    height: 70,
    marginRight: 12,
  },
  textContainer: {
    flex: 1,
  },
  title: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  author: {
    fontSize: 14,
    color: '#666',
  },
});

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  fullScreen: {
    flex: 1,
  },
  view: {
    flex: 1,
  },
});

type RootStackParamList = StaticParamList<typeof RootStack>;

declare global {
  namespace ReactNavigation {
    interface RootParamList extends RootStackParamList {}
  }
}

function App(): React.JSX.Element {
  return <Navigation />;
}

export default App;
