import type { EpubPreferences } from './epub-preferences'
import type { Manifest, Link } from './manifest'

export interface Locator {
  href: string
  type: string // MediaType
  title?: string
  locations?: Locations
  text?: TextObject
}

export interface Locations {
  fragments?: string[]
  progression?: number
  position?: number
  totalProgression?: number
}

export interface TextObject {
  after?: string
  before?: string
  highlight?: string
}

export interface Selection {
  locator: Locator
  rect?: Rect
}

export interface Rect {
  left: number
  top: number
  right: number
  bottom: number
}

export interface Point {
  x: number
  y: number
}

export interface Decoration {
  id: string
  locator: Locator
  style: DecorationStyle
}

export interface DecorationStyle {
  type: DecorationType
  tint: string
}

export type DecorationType = 'highlight' | 'underline'

export interface DecorationActivatedEvent {
  decoration: Decoration
  group: string
  rect?: Rect
  point?: Point
}

export interface TapEvent {
  x: number
  y: number
}

export type DragEventType = 'start' | 'move' | 'end'

export interface DragEvent {
  type: DragEventType
  start: Point
  end: Point
}

export type { EpubPreferences, Manifest, Link }
