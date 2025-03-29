import type { HybridObject } from 'react-native-nitro-modules'

export interface Publication
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  manifest: string
  metadata: string
  tableOfContents: string
  images: string
  cover(): Promise<string>
}
