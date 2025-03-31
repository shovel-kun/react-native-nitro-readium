export type LocalizedString = string | Record<string, string>

export type Link = {
  href: string
  type?: string
  templated?: string
  title?: string
  rel?: string | string[]
  height?: number
  width?: number
  bitrate?: number
  duration?: number
  language?: string | string[]
  alternate?: Link[]
  children?: Link[]
  properties?: {
    mediaOverlay?: string
    contains?: Array<
      'mathml' | 'onix' | 'remote-resources' | 'js' | 'svg' | 'xmp'
    >
    layout?: 'fixed' | 'reflowable'
    encrypted?: {
      algorithm: string
      compression: string
      originalLength: number
      profile: string
      scheme: string
    }
    clipped?: boolean
    fit?: 'contain' | 'cover' | 'width' | 'height'
    orientation?: 'auto' | 'landscape' | 'portrait'
    page?: 'left' | 'right' | 'center'
    spread?: 'auto' | 'both' | 'none' | 'landscape'
  }
}

export type Contributor =
  | string
  | {
      name: LocalizedString
      identifier?: string
      sortAs?: string
      role?: string | string[]
      position?: number
      links?: Link[]
    }

export type Subject =
  | string
  | {
      name: LocalizedString
      sortAs?: string
      code?: string
      scheme?: string
      links?: Link[]
    }

export type MetaData = {
  'identifier'?: string
  '@type'?: string
  'conformsTo'?: string | string[]
  'title': LocalizedString
  'sortAs'?: LocalizedString
  'subtitle'?: LocalizedString
  'accessibility'?: {
    conformsTo?: string | string[]
    certification?: {
      certifiedBy?: string
      credential?: string
      report?: string
    }
    summary?: string
    accessMode?: Array<
      | 'auditory'
      | 'chartOnVisual'
      | 'chemOnVisual'
      | 'colorDependent'
      | 'diagromOnVisual'
      | 'mathOnVisual'
      | 'musicOnVisual'
      | 'tactile'
      | 'textOnVisual'
      | 'textual'
      | 'visual'
    >
    acessModeSufficient?: Array<'auditory' | 'tactile' | 'textual' | 'visual'>
    feature?: Array<
      | 'annotations'
      | 'ARIA'
      | 'bookmarks'
      | 'index'
      | 'printPageNumbers'
      | 'readingOrder'
      | 'structuralNavigation'
      | 'tableOfContents'
      | 'taggedPDF'
      | 'alternativeText'
      | 'audioDescription'
      | 'captions'
      | 'describedMath'
      | 'longDescription'
      | 'rubyAnnotations'
      | 'signLanguage'
      | 'transcript'
      | 'displayTransformability'
      | 'synchronizedAudioText'
      | 'timingControl'
      | 'unlocked'
      | 'ChemML'
      | 'latex'
      | 'MathML'
      | 'ttsMarkup'
      | 'highContrastAudio'
      | 'highContrastDisplay'
      | 'largePrint'
      | 'braille'
      | 'tactileGraphic'
      | 'tactileObject'
      | 'none'
    >
    hazard?: Array<
      | 'flashing'
      | 'noFlashingHazard'
      | 'motionSimulation'
      | 'noMotionSimulationHazard'
      | 'sound'
      | 'noSoundHazard'
      | 'unknown'
      | 'none'
    >
  }
  'modified'?: string
  'published'?: string
  'language'?: string
  'author'?: Contributor | Contributor[]
  'translator'?: Contributor | Contributor[]
  'editor'?: Contributor | Contributor[]
  'artist'?: Contributor | Contributor[]
  'illustrator'?: Contributor | Contributor[]
  'letterer'?: Contributor | Contributor[]
  'penciler'?: Contributor | Contributor[]
  'colorist'?: Contributor | Contributor[]
  'inker'?: Contributor | Contributor[]
  'narrator'?: Contributor | Contributor[]
  'contributor'?: Contributor | Contributor[]
  'publisher'?: Contributor | Contributor[]
  'imprint'?: Contributor | Contributor[]
  'subject'?: Subject[]
  'readingProgression'?: 'rtl' | 'ltr' | 'ttb' | 'btt' | 'auto'
  'description'?: string
  'duration': number
  'numberOfPages'?: number
  'belongsTo'?: {
    collection?: Contributor | Contributor[]
    series?: Contributor | Contributor[]
  }
  'presentation'?: {
    layout?: 'fixed' | 'reflowable'
  }
}

export type Manifest = {
  '@context': string | string[]
  'metadata': MetaData
  'links': Link[]
  'readingOrder': Array<Link & { type: string }>
  'resources'?: Array<Link & { type: string }>
  'toc'?: Link[]
  'pageList'?: Link[]
  'landmarks'?: Link[]
  'loa'?: Link[]
  'loi'?: Link[]
  'lot'?: Link[]
  'lov'?: Link[]
}
