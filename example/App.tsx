import React, {useState, useRef, useCallback, useEffect} from 'react';
import {
  Button,
  Text,
  View,
  StyleSheet,
  Platform,
  Dimensions,
  LayoutChangeEvent,
} from 'react-native';
import {
  Readium,
  Locator,
  Selection,
  Decoration,
  DecorationType,
  ActivatedDecoration,
  ReadiumRef,
  getManifest,
} from 'react-native-nitro-readium';
import {SelectionMenu} from './SelectionMenu';
import {DecorationMenu} from './DecorationMenu';
import {pick} from '@react-native-documents/picker';

function App(): React.JSX.Element {
  const [absolutePath, setAbsolutePath] = useState<string | null>(null);
  const [locator, setLocator] = useState<Locator | null>(null);
  const [selection, setSelection] = useState<Selection | null>(null);
  const [decorations, setDecorations] = useState<Decoration[]>([]);
  const [activatedDecoration, setActivatedDecoration] =
    useState<ActivatedDecoration | null>(null);
  const [viewDimensions, setViewDimensions] = useState({width: 0, height: 0});
  const readiumRef = useRef<ReadiumRef>(null);

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

  const onHybridRefChanged = useCallback((ref: ReadiumRef | null) => {
    console.log('onHybridRefChanged');
    console.log(readiumRef.current);
    readiumRef.current = ref;
  }, []);

  const onLayout = useCallback(
    (event: LayoutChangeEvent) => {
      console.log('layout changed');
      const {width, height} = event.nativeEvent.layout;
      setViewDimensions({width, height});
    },
    [locator],
  );

  // useEffect(() => {
  //   console.log('view dimensions changed');
  //   console.log(viewDimensions);
  // }, [viewDimensions]);
  //
  //

  const injectedJavascript: string = require('./script.raw.js');
  console.log('Injecting: ', injectedJavascript.substring(0, 100));

  console.log('RERENDER!!!!!');

  return (
    <View style={styles.container}>
      {!absolutePath && (
        <Button
          title="open directory"
          onPress={async () => {
            try {
              const [result] = await pick({
                mode: 'open',
              });
              if (result.uri) {
                console.log(result);
                setAbsolutePath(result.uri);
                const manifest = await getManifest(result.uri);
                console.log(manifest.toc);
              }
            } catch (err) {
              console.error(err);
            }
          }}
        />
      )}
      {absolutePath && (
        <>
          <Readium
            style={styles.view}
            absolutePath={absolutePath}
            onLocatorChanged={onLocatorChanged}
            decorations={decorations}
            onSelection={onSelectionChanged}
            onDecorationActivated={onDecorationActivatedChanged}
            injectedJavascriptOnResourcesLoad={injectedJavascript}
            // onTap={{f: ({x, y}) => handleTap(x, y)}}
            onTap={({x, y}) => {
              // console.log('Tap detected');
              // console.log(`x: ${x}, y: ${y}`);
              // console.log(
              //   `width: ${viewDimensions.width}, height: ${viewDimensions.height}`,
              // );
              // console.log('Locator:', locator);
              console.log(readiumRef);
            }}
            // onTouchStart={(e: GestureResponderEvent) => {
            //   console.log('Touch start detected');
            //   console.log(e);
            // }}
            onMessage={console.log}
            onDrag={() => {
              setActivatedDecoration(null);
            }}
            // onLayout={onLayout}
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
              // onAddDecoration={() => {
              //   console.log('add decoration');
              //   // console.log(locator);
              //   console.log(viewDimensions);
              // }}
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
