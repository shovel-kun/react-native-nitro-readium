import { NitroModules } from 'react-native-nitro-modules'
export * from './types'
export type { DecorationActivatedEvent as ActivatedDecoration } from './types'
import type { ReadiumModule as ReadiumModuleSpecs } from './specs/readium-module.nitro'
import type { Manifest } from './types'
export {
  default as Readium,
  type ReadiumProps,
  type ReadiumNitroRef as ReadiumRef,
} from './Readium'

const ReadiumModule =
  NitroModules.createHybridObject<ReadiumModuleSpecs>('ReadiumModule')

export async function getManifest(absoluteUrl: string): Promise<Manifest> {
  const manifest = await ReadiumModule.getManifest(absoluteUrl)
  return JSON.parse(manifest)
}
