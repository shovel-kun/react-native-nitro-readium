import React, {useState, useRef, useCallback} from 'react';
import {Button, StyleSheet, View} from 'react-native';
import {useNavigation, useFocusEffect} from '@react-navigation/native';
import {pick, pickDirectory} from '@react-native-documents/picker';
import {UniFile, fromUri} from 'react-native-unifile';
import {openPublication} from 'react-native-nitro-readium';
import type {HomeScreenProps} from '../navigation';
import {usePublication} from '../contexts/PublicationContext';
import {Book} from 'src/types';

const HomeScreen = ({}: HomeScreenProps) => {
  const navigation = useNavigation();
  const [pickedDirectory, setPickedDirectory] = useState<string | null>(null);
  const [isButtonToggled, setIsButtonToggled] = useState(false);
  const scanCountRef = useRef(0);

  const {setCurrentPublication} = usePublication();

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

        setCurrentPublication(publication);

        navigation.navigate('Metadata', {
          absolutePath: result.uri,
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

export default HomeScreen;

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
