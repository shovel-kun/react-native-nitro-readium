import {Manifest} from 'react-native-nitro-readium';

export interface Book {
  manifest: Manifest;
  coverUri: string;
}
