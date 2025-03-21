import type {
  HybridView,
  HybridViewProps,
  HybridViewMethods,
} from 'react-native-nitro-modules'

export interface NitroReadiumProps extends HybridViewProps {
   isRed: boolean
}

export interface NitroReadiumMethods extends HybridViewMethods {}

export type NitroReadium = HybridView<NitroReadiumProps, NitroReadiumMethods, { ios: 'swift', android: 'kotlin' }>