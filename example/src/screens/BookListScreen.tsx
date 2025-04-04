import React, {useEffect} from 'react';
import {FlatList, View, Text, Image, Button, StyleSheet} from 'react-native';
import {useNavigation} from '@react-navigation/native';
import {parseLocalizedString, parseAuthors} from '../utils';
import type {BookListScreenProps} from '../navigation/types';
import {Book} from 'src/types';

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
          {parseAuthors(item.manifest.metadata.author) || 'Unknown Author'}
        </Text>
      </View>
      {/* <Button */}
      {/*   title="View" */}
      {/*   onPress={() => */}
      {/*     navigation.navigate('Metadata', { */}
      {/*       manifest: item.manifest, */}
      {/*       coverImageUri: item.coverUri, */}
      {/*     }) */}
      {/*   } */}
      {/* /> */}
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

export default BookListScreen;

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
