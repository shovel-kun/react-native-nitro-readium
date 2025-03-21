import React from 'react';
import { Text, View, StyleSheet } from 'react-native';
import { NitroReadium } from 'react-native-nitro-readium';

function App(): React.JSX.Element {
  return (
    <View style={styles.container}>
        <NitroReadium isRed={true} style={styles.view} />
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
    width: 200,
    height: 200
  }});

export default App;