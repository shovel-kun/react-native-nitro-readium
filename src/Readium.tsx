import React, {
  forwardRef,
  useRef,
  useImperativeHandle,
  memo,
  useCallback,
} from 'react'
import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import type { StyleProp, ViewStyle } from 'react-native'
import NitroReadiumConfig from '../nitrogen/generated/shared/json/NitroReadiumConfig.json'
import type {
  NitroReadiumProps,
  NitroReadiumMethods,
  NitroReadium as NitroReadiumType,
} from './views/nitro-readium.nitro'
import type { Locator } from './types'

const NitroReadium = getHostComponent<NitroReadiumProps, NitroReadiumMethods>(
  'NitroReadium',
  () => NitroReadiumConfig
)

export interface ReadiumProps extends NitroReadiumProps {
  style?: StyleProp<ViewStyle>
  hybridRef?: {
    f: (ref: ReadiumNitroRef | null) => void
  }
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
    ref
  ) => {
    const nativeRef = useRef<NitroReadiumType | null>(null)

    // if (__DEV__) console.log('Readium component rendered')

    const evaluateJavascript = useCallback(
      (script: string) => {
        if (nativeRef.current === null) {
          throw new Error('Readium component not mounted')
        }

        return nativeRef.current?.evaluateJavascript(script)
      },
      [nativeRef]
    )

    const injectJavascript = useCallback(
      (script: string) => {
        nativeRef.current?.injectJavascript(script)
      },
      [nativeRef]
    )

    const go = useCallback(
      (locator: Locator) => {
        nativeRef.current?.go(locator)
      },
      [nativeRef]
    )

    const clearSelection = useCallback(() => {
      nativeRef.current?.clearSelection()
    }, [nativeRef])

    useImperativeHandle(ref, () => {
      return {
        evaluateJavascript,
        injectJavascript,
        go,
        clearSelection,
      }
    }, [])

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
