import {StaticParamList, StaticScreenProps} from '@react-navigation/native';
import {RootStack} from './RootStack';
import {Locator} from '../../../src/types';
import {Book} from 'src/types';

type RootStackParamList = StaticParamList<typeof RootStack>;

declare global {
  namespace ReactNavigation {
    interface RootParamList extends RootStackParamList {}
  }
}

type BookListScreenProps = StaticScreenProps<{
  books: Book[];
}>;

type ReadiumViewerScreenProps = StaticScreenProps<{
  absolutePath: string;
  locator?: Locator;
}>;

type MetadataScreenProps = StaticScreenProps<{
  absolutePath: string;
}>;

type HomeScreenProps = StaticScreenProps<{}>;

export type {
  RootStackParamList,
  BookListScreenProps,
  ReadiumViewerScreenProps,
  MetadataScreenProps,
  HomeScreenProps,
};
