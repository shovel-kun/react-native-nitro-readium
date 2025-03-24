const {getDefaultConfig, mergeConfig} = require('@react-native/metro-config');
const path = require('path');
const root = path.resolve(__dirname, '..');
const defaultConfig = getDefaultConfig(__dirname);

/**
 * Metro configuration
 * https://facebook.github.io/metro/docs/configuration
 *
 * @type {import('metro-config').MetroConfig}
 */
const config = {
  transformer: {
    getTransformOptions: async () => ({
      transform: {
        experimentalImportSupport: false,
        inlineRequires: true,
      },
    }),
  },
  watchFolders: [root],
};

const injectedCodeTransformerConfig = {
  transformer: {
    babelTransformerPath: require.resolve('./injected-code-transformer.js'),
  },
};

module.exports = mergeConfig(
  defaultConfig,
  injectedCodeTransformerConfig,
  config,
);

