import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import { NitroModules } from 'react-native-nitro-modules'
import NitroReadiumConfig from '../nitrogen/generated/shared/json/NitroReadiumConfig.json'
import type {
  NitroReadiumProps,
  NitroReadiumMethods,
} from './views/nitro-readium.nitro'
export * from './types'
export type { DecorationActivatedEvent as ActivatedDecoration } from './types'
import type { ReadiumModule as ReadiumModuleSpecs } from './specs/readium-module.nitro'
import type { Manifest } from './types'

/**
 * Represents the HybridView `EpubView`, which can be rendered as a React Native view.
 */
export const Readium = getHostComponent<NitroReadiumProps, NitroReadiumMethods>(
  'NitroReadium',
  () => NitroReadiumConfig
)

const ReadiumModule =
  NitroModules.createHybridObject<ReadiumModuleSpecs>('ReadiumModule')

export async function getManifest(absoluteUrl: string): Promise<Manifest> {
  const manifest = await ReadiumModule.getManifest(absoluteUrl)
  return JSON.parse(manifest)
}

export type ReadiumNitroRef = HybridRef<NitroReadiumProps, NitroReadiumMethods>
