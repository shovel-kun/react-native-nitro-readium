import { NitroModules } from 'react-native-nitro-modules'
export * from './types'
export type { DecorationActivatedEvent as ActivatedDecoration } from './types'
import type { ReadiumModule as ReadiumModuleSpecs } from './specs/readium-module.nitro'
import type { Manifest, Link, MetaData, Locator } from './types'
import type { Publication as RawPublication } from './specs/publication.nitro'
export {
  default as Readium,
  type ReadiumProps,
  type ReadiumNitroRef as ReadiumRef,
} from './Readium'

const ReadiumModule =
  NitroModules.createHybridObject<ReadiumModuleSpecs>('ReadiumModule')

export interface Publication {
  manifest: Manifest
  tableOfContents: Link[]
  metadata: MetaData
  images: Link[]
  cover(): Promise<string>
  locatorFromLink(link: Link): Locator | null
  locate(locator: Locator): Promise<Locator | null>
  locateProgression(progression: number): Promise<Locator | null>
}

async function parsePublication(
  rawPublication: RawPublication
): Promise<Publication> {
  return {
    manifest: JSON.parse(rawPublication.manifest),
    tableOfContents: JSON.parse(rawPublication.tableOfContents),
    images: JSON.parse(rawPublication.images),
    metadata: JSON.parse(rawPublication.metadata),
    cover: () => rawPublication.cover(),
    locatorFromLink: (link: Link) =>
      rawPublication.locatorFromLink(JSON.stringify(link)),
    locate: (locator: Locator) => rawPublication.locate(locator),
    locateProgression: (progression: number) =>
      rawPublication.locateProgression(progression),
  }
}

export async function openPublication(absoluteUrl: string) {
  const rawPublication = await ReadiumModule.openPublication(absoluteUrl)
  return parsePublication(rawPublication)
}
