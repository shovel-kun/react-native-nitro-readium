import type {
  HybridView,
  HybridViewProps,
  HybridViewMethods,
} from 'react-native-nitro-modules'
import type {
  Locator,
  Selection,
  Decoration,
  DecorationActivatedEvent,
  TapEvent,
  DragEvent,
  EpubPreferences,
} from '../types'

export interface NitroReadiumProps extends HybridViewProps {
  absolutePath?: string
  locator?: Locator
  preferences?: EpubPreferences
  decorations?: Decoration[]
  onLocatorChanged?: (locator: Locator) => void
  onSelection?: (selection: Selection | null) => void
  onDecorationActivated?: (event: DecorationActivatedEvent) => void
  onTap?: (event: TapEvent) => void
  onDrag?: (event: DragEvent) => void
  onPageChanged?: (page: number, totalPages: number, locator: Locator) => void
  onPageLoaded?: () => void
}

export interface NitroReadiumMethods extends HybridViewMethods {
  evaluateJavascript(script: string): Promise<string | null>
  go(locator: Locator): void
  clearSelection(): void
}

export type NitroReadium = HybridView<
  NitroReadiumProps,
  NitroReadiumMethods,
  { ios: 'swift'; android: 'kotlin' }
>
