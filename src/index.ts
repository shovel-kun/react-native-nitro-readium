import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import NitroReadiumConfig from '../nitrogen/generated/shared/json/NitroReadiumConfig.json'
import type {
  NitroReadiumProps,
  NitroReadiumMethods,
} from './views/nitro-readium.nitro'


export const NitroReadium = getHostComponent<NitroReadiumProps, NitroReadiumMethods>(
  'NitroReadium',
  () => NitroReadiumConfig
)

export type NitroReadiumRef = HybridRef<NitroReadiumProps, NitroReadiumMethods>
