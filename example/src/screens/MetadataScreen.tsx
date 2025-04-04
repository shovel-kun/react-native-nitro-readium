import React from 'react';
import {View, Text, Image, FlatList, Button, StyleSheet} from 'react-native';
import {useNavigation} from '@react-navigation/native';
import {parseLocalizedString, parseAuthors} from '../utils';
import type {MetadataScreenProps} from '../navigation/types';
import {usePublication} from '../contexts/PublicationContext';
import {Manifest} from 'react-native-nitro-readium';

const MetadataScreen = ({route}: MetadataScreenProps) => {
  const {absolutePath} = route.params;
  const {currentPublication} = usePublication();
  const [coverImageUri, setCoverImageUri] = React.useState<string | undefined>(
    undefined,
  );
  const [manifest, setManifest] = React.useState<Manifest | undefined>(
    undefined,
  );
  const navigation = useNavigation();

  React.useEffect(() => {
    const fetchCoverImage = async () => {
      const coverImageUri = await currentPublication?.cover();
      if (!coverImageUri) return;
      setCoverImageUri(coverImageUri);
    };

    const manifest = currentPublication?.manifest;
    if (!manifest) return;
    setManifest(manifest);

    fetchCoverImage();
  }, [coverImageUri]);

  if (!manifest) return null;

  return (
    <View style={metadataStyles.container}>
      {coverImageUri && (
        <Image
          source={{uri: coverImageUri}}
          style={metadataStyles.coverImage}
        />
      )}
      <Text style={metadataStyles.label}>Title:</Text>
      <Text style={metadataStyles.value}>
        {parseLocalizedString(manifest.metadata.title) || 'N/A'}
      </Text>
      <Text style={metadataStyles.label}>Author:</Text>
      <Text style={metadataStyles.value}>
        {parseAuthors(manifest.metadata.author) || 'N/A'}
      </Text>
      <Text style={metadataStyles.label}>Table of Contents:</Text>
      <FlatList
        data={manifest.toc}
        keyExtractor={item => item.href}
        renderItem={({item}) => (
          <Text
            style={metadataStyles.link}
            onPress={() => {
              const locator =
                currentPublication?.locatorFromLink(item) ?? undefined;
              navigation.navigate('ReadiumViewer', {
                absolutePath,
                locator,
              });
            }}>
            {item.title ? parseLocalizedString(item.title) : 'N/A'}
          </Text>
        )}
      />
      <Button title="Close" onPress={() => navigation.goBack()} />
    </View>
  );
};

export default MetadataScreen;

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
  link: {
    fontSize: 16,
    marginBottom: 10,
    color: '#0000ff',
  },
  coverImage: {
    width: 200,
    height: 300,
    resizeMode: 'contain',
    marginTop: 20,
  },
});
