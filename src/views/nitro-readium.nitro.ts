import type {
  HybridView,
  HybridViewProps,
  HybridViewMethods,
} from 'react-native-nitro-modules'
import type {
  NitroFileSource,
  Locator,
  Selection,
  Decoration,
  DecorationActivatedEvent,
  TapEvent,
  DragEvent,
  EpubPreferences,
} from '../types'

export interface NitroReadiumProps extends HybridViewProps {
  nitroSource: NitroFileSource
  locator?: Locator
  preferences?: EpubPreferences
  decorations?: Decoration[]
  // runs on all webviews when resources are loaded
  injectedJavascriptOnResourcesLoad?: string
  // runs on the webview that is the current page
  injectedJavascriptOnPageLoad?: string
  onLocatorChanged?: (locator: Locator) => void
  onSelection?: (selection: Selection | null) => void
  onDecorationActivated?: (event: DecorationActivatedEvent) => void
  onTap?: (event: TapEvent) => void
  onDrag?: (event: DragEvent) => void
  onPageChanged?: (page: number, totalPages: number, locator: Locator) => void
  onPageLoaded?: () => void
  onPreferencesChanged?: (preferences: EpubPreferences) => void
  /*
   * To post a message from Readium WebView to React Native, call:
   *
   * window.ReadiumWebView.postMessage("your message")
   */
  onMessage?: (message: string) => void
}

export interface NitroReadiumMethods extends HybridViewMethods {
  evaluateJavascript(script: string): Promise<string | null>
  injectJavascript(script: string): void
  go(locator: Locator): void
  clearSelection(): void
  getSettings(): EpubPreferences
}

export type NitroReadium = HybridView<
  NitroReadiumProps,
  NitroReadiumMethods,
  { ios: 'swift'; android: 'kotlin' }
>
