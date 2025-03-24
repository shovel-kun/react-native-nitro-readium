import React, {
  forwardRef,
  useRef,
  // useImperativeHandle,
  memo,
  // useCallback,
} from 'react'
import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import type { StyleProp, ViewStyle } from 'react-native'
import NitroReadiumConfig from '../nitrogen/generated/shared/json/NitroReadiumConfig.json'
import type {
  NitroReadiumProps,
  NitroReadiumMethods,
  NitroReadium as NitroReadiumType,
} from './views/nitro-readium.nitro'

const NitroReadium = getHostComponent<NitroReadiumProps, NitroReadiumMethods>(
  'NitroReadium',
  () => NitroReadiumConfig
)

export interface ReadiumProps extends NitroReadiumProps {
  style?: StyleProp<ViewStyle>
}

export type ReadiumNitroRef = HybridRef<NitroReadiumProps, NitroReadiumMethods>

const Readium = forwardRef<NitroReadiumMethods, ReadiumProps>(
  (
    {
      style,
      onLocatorChanged,
      onTap,
      onPageLoaded,
      onPageChanged,
      onSelection,
      onDrag,
      onDecorationActivated,
      onMessage,
      ...rest
    },
    _
  ) => {
    const nativeRef = useRef<NitroReadiumType>(null)

    if (__DEV__) console.log('Readium component rendered')

    return (
      <NitroReadium
        {...rest}
        style={style}
        onLocatorChanged={{ f: onLocatorChanged }}
        onTap={{ f: onTap }}
        onPageLoaded={{ f: onPageLoaded }}
        onPageChanged={{ f: onPageChanged }}
        onSelection={{ f: onSelection }}
        onDrag={{ f: onDrag }}
        onDecorationActivated={{ f: onDecorationActivated }}
        onMessage={{ f: onMessage }}
        hybridRef={{
          f: (ref) => {
            nativeRef.current = ref
          },
        }}
      />
    )
  }
)

export default memo(Readium)
