import type { HybridObject } from 'react-native-nitro-modules'
import type { Locator } from '../types'

export interface Publication
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  manifest: string
  metadata: string
  tableOfContents: string
  images: string
  cover(): Promise<string>
  locatorFromLink(link: string): Locator | null
  locate(locator: Locator): Promise<Locator | null>
  locateProgression(progression: number): Promise<Locator | null>
}
