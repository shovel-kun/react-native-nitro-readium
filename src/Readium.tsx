import React, {
  forwardRef,
  useRef,
  useImperativeHandle,
  memo,
  useCallback,
  useMemo,
} from 'react'
import { getHostComponent, type HybridRef } from 'react-native-nitro-modules'
import type { StyleProp, ViewStyle } from 'react-native'
import NitroReadiumConfig from '../nitrogen/generated/shared/json/NitroReadiumConfig.json'
import type {
  NitroReadiumProps,
  NitroReadiumMethods,
  NitroReadium as NitroReadiumType,
} from './views/nitro-readium.nitro'
import type { Link, Locator } from './types'

const NitroReadium = getHostComponent<NitroReadiumProps, NitroReadiumMethods>(
  'NitroReadium',
  () => NitroReadiumConfig
)

export interface ReadiumProps extends Omit<NitroReadiumProps, 'nitroSource'> {
  source: {
    uri: string
    initialLocation?: Locator | Link
  }
  style?: StyleProp<ViewStyle>
  hybridRef?: {
    f: (ref: ReadiumNitroRef | null) => void
  }
}

export type ReadiumNitroRef = HybridRef<
  Omit<NitroReadiumProps, 'nitroSource'>,
  NitroReadiumMethods
>

const Readium = forwardRef<NitroReadiumMethods, ReadiumProps>(
  (
    {
      source,
      style,
      onLocatorChanged,
      onTap,
      onPageLoaded,
      onPageChanged,
      onSelection,
      onDrag,
      onDecorationActivated,
      onPreferencesChanged,
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

        try {
          return nativeRef.current?.evaluateJavascript(script)
        } catch (error) {
          throw new Error(`Failed to evaluate javascript: ${error}`)
        }
      },
      [nativeRef]
    )

    const injectJavascript = useCallback(
      (script: string) => {
        try {
          nativeRef.current?.injectJavascript(script)
        } catch (error) {
          throw new Error(`Failed to inject javascript: ${error}`)
        }
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

    const getSettings = useCallback(() => {
      if (nativeRef.current === null) {
        throw new Error('Readium component not mounted')
      }

      return nativeRef.current?.getSettings()
    }, [nativeRef])

    useImperativeHandle(
      ref,
      () => {
        return {
          evaluateJavascript,
          injectJavascript,
          go,
          clearSelection,
          getSettings,
        }
      },
      []
    )

    const nitroSource = useMemo(() => {
      return {
        uri: source.uri,
        // Stringify initialLocation before passing it to native side
        initialLocation: JSON.stringify(source.initialLocation),
      }
    }, [source])

    return (
      <NitroReadium
        {...rest}
        nitroSource={nitroSource}
        style={style}
        onLocatorChanged={{ f: onLocatorChanged }}
        onTap={{ f: onTap }}
        onPageLoaded={{ f: onPageLoaded }}
        onPageChanged={{ f: onPageChanged }}
        onSelection={{ f: onSelection }}
        onDrag={{ f: onDrag }}
        onDecorationActivated={{ f: onDecorationActivated }}
        onPreferencesChanged={{ f: onPreferencesChanged }}
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
