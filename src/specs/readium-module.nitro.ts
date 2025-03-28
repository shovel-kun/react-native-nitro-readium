import type { HybridObject } from 'react-native-nitro-modules'
import type { Publication } from './publication.nitro'

export interface ReadiumModule
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  add(a: number, b: number): number
  openPublication(absoluteUrl: string): Promise<Publication>
}
