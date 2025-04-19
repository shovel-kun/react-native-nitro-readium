import React, {useRef, useState, useCallback, useEffect} from 'react';
import {LayoutChangeEvent, StyleSheet} from 'react-native';
import {Readium, ReadiumRef} from 'react-native-nitro-readium';
import {SelectionMenu} from '../components/SelectionMenu';
import {DecorationMenu} from '../components/DecorationMenu';
import type {ReadiumViewerScreenProps} from '../navigation/types';
import type {
  Locator,
  Selection,
  Decoration,
  ActivatedDecoration,
  DecorationType,
  EpubPreferences,
} from 'react-native-nitro-readium';

const ReadiumReaderScreen = ({route}: ReadiumViewerScreenProps) => {
  const {absolutePath, locator} = route.params;
  const readiumRef = useRef<ReadiumRef>(null);
  const [currentLocator, setCurrentLocator] = useState<Locator | null>(null);
  const [selection, setSelection] = useState<Selection | null>(null);
  const [decorations, setDecorations] = useState<Decoration[]>([]);
  const [activatedDecoration, setActivatedDecoration] =
    useState<ActivatedDecoration | null>(null);
  const [viewDimensions, setViewDimensions] = useState({width: 0, height: 0});
  const [injectedJavascript, setInjectedJavascript] = useState<
    string | undefined
  >(undefined);

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
      console.log('Locator:', currentLocator);

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
    [viewDimensions, currentLocator],
  );

  const onLocatorChanged = useCallback((locator: Locator) => {
    setCurrentLocator(locator);

    // console.log('Settings:', readiumRef.current?.getSettings());
    // console.log(locator);
  }, []);

  const onSelectionChanged = useCallback(
    (selection: Selection | null) => {
      setSelection(selection);
      // console.log(locator);
      // console.log(selection);
    },
    [currentLocator],
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
    [currentLocator],
  );

  const onPreferencesChanged = useCallback(
    (preferences: EpubPreferences) => {
      console.log('Preferences changed');
      console.log(preferences);
    },
    [currentLocator],
  );

  useEffect(() => {
    if (injectedJavascript) return;
    const injectedScript: string = require('../../script.raw.js');
    setInjectedJavascript(injectedScript);
  }, [injectedJavascript]);

  return (
    <>
      <Readium
        style={styles.view}
        source={{
          uri: absolutePath,
          initialLocation: locator,
        }}
        //locator={locator}
        onLocatorChanged={onLocatorChanged}
        decorations={decorations}
        onSelection={onSelectionChanged}
        onDecorationActivated={onDecorationActivatedChanged}
        injectedJavascriptOnResourcesLoad={injectedJavascript}
        //onTap={({x, y}) => handleTap(x, y)}
        onMessage={message => console.log(`From Kotlin: ${message}`)}
        onDrag={() => {
          setActivatedDecoration(null);
        }}
        onPreferencesChanged={onPreferencesChanged}
        //@ts-expect-error remove once nitro fixes the type
        onLayout={onLayout}
        preferences={{
          theme: 'light',
          scroll: true,
          publisherStyles: false,
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

export default ReadiumReaderScreen;

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
