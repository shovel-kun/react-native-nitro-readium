import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {createStaticNavigation} from '@react-navigation/native';
import HomeScreen from '../screens/HomeScreen';
import BookListScreen from '../screens/BookListScreen';
import MetadataScreen from '../screens/MetadataScreen';
import ReadiumReaderScreen from '../screens/ReadiumReaderScreen';

export const RootStack = createNativeStackNavigator({
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
      screen: ReadiumReaderScreen,
      options: {headerShown: false},
    },
  },
});

export const Navigation = createStaticNavigation(RootStack);
