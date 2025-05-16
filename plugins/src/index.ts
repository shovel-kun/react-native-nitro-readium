import type { ConfigPlugin } from '@expo/config-plugins'
import { createRunOncePlugin, withPlugins } from '@expo/config-plugins'

import { withCocoaPodsImport } from './withIosReadium'

const pak = require('react-native-nitro-readium/package.json')

const withReactNativeNitroReadium: ConfigPlugin = (config) => {
  return withPlugins(config, [withCocoaPodsImport])
}

export default createRunOncePlugin(
  withReactNativeNitroReadium,
  pak.name,
  pak.version
)
