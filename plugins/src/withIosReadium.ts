import { mergeContents } from '@expo/config-plugins/build/utils/generateCode'
import { type ConfigPlugin, withDangerousMod } from '@expo/config-plugins'
import { promises } from 'fs'
import path from 'path'

/** Dangerously adds the custom import to the CocoaPods. */
export const withCocoaPodsImport: ConfigPlugin = (c) => {
  return withDangerousMod(c, [
    'ios',
    async (config) => {
      const file = path.join(config.modRequest.platformProjectRoot, 'Podfile')

      let contents = await promises.readFile(file, 'utf8')
      contents = addCocoaPodsSource(contents)
      contents = addCocoaPodsImport(contents)

      await promises.writeFile(file, contents, 'utf-8')
      return config
    },
  ])
}

const MOD_TAG = 'react-native-nitro-readium'

function addCocoaPodsImport(src: string): string {
  return mergeContents({
    tag: `${MOD_TAG}-import`,
    src,
    newSrc: `
  pod 'ReadiumShared', '~> 3.2.0'
  pod 'ReadiumStreamer', '~> 3.2.0'
  pod 'ReadiumNavigator', '~> 3.2.0'
  pod 'ReadiumOPDS', '~> 3.2.0'
  pod 'Minizip', modular_headers: true
`,
    anchor: /use_native_modules/,
    offset: 0,
    comment: '#',
  }).contents
}

function addCocoaPodsSource(src: string): string {
  return mergeContents({
    tag: `${MOD_TAG}-source`,
    src,
    newSrc: `
source 'https://github.com/readium/podspecs'
source 'https://cdn.cocoapods.org'
`,
    anchor: /^/,
    offset: 0,
    comment: '#',
  }).contents
}
