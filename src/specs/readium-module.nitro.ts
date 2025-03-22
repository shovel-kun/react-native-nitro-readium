import type { HybridObject } from 'react-native-nitro-modules'
// import type { Manifest } from '../types/Manifest'

export interface ReadiumModule
  extends HybridObject<{ ios: 'swift'; android: 'kotlin' }> {
  add(a: number, b: number): number
  getManifest(absoluteUrl: string): Promise<string>
}
