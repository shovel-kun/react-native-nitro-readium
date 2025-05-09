type Theme = 'light' | 'dark' | 'sepia'

type TextAlign = 'center' | 'justify' | 'start' | 'end' | 'left' | 'right'

type ColumnCount = 'auto' | '1' | '2'

type ImageFilter = 'darken' | 'invert'

// type Axis = "horizontal" | "vertical";

type Spread = 'auto' | 'never' | 'always'

type ReadingProgression = 'ltr' | 'rtl'

export interface EpubPreferences {
  /**
   * Default page background color.
   */
  backgroundColor?: string

  /**
   * Number of reflowable columns to display (one-page view or two-page spread).
   */
  columnCount?: ColumnCount

  /**
   * Default typeface for the text.
   */
  fontFamily?: string

  /**
   * Base text font size.
   */
  fontSize?: number

  /**
   * Default boldness for the text.
   */
  fontWeight?: number

  /**
   * Enable hyphenation.
   */
  hyphens?: boolean

  /**
   * Filter applied to images in dark theme.
   */
  imageFilter?: ImageFilter

  /**
   * Language of the publication content.
   */
  language?: string

  /**
   * Space between letters.
   */
  letterSpacing?: number

  /**
   * Enable ligatures in Arabic.
   */
  ligatures?: boolean

  /**
   * Leading line height.
   */
  lineHeight?: number

  /**
   * Factor applied to horizontal margins.
   */
  pageMargins?: number

  /**
   * Text indentation for paragraphs.
   */
  paragraphIndent?: number

  /**
   * Vertical margins for paragraphs.
   */
  paragraphSpacing?: number

  /**
   * Indicates whether the original publisher styles should be observed.
   * Many settings require this to be off.
   */
  publisherStyles?: boolean

  /**
   * Direction of the reading progression across resources.
   */
  readingProgression?: ReadingProgression

  /**
   * Indicates if the overflow of resources should be handled using scrolling
   * instead of synthetic pagination.
   */
  scroll?: boolean

  /**
   * Indicates if the fixed-layout publication should be rendered with a
   * synthetic spread (dual-page).
   */
  spread?: Spread

  /**
   * Page text alignment.
   */
  textAlign?: TextAlign

  /**
   * Default page text color.
   */
  textColor?: string

  /**
   * Normalize text styles to increase accessibility.
   */
  textNormalization?: boolean

  /**
   * Reader theme.
   */
  theme?: Theme

  /**
   * Scale applied to all element font sizes.
   */
  typeScale?: number

  /**
   * Indicates whether the text should be laid out vertically. This is used
   * for example with CJK languages. This setting is automatically derived from the language if
   * no preference is given.
   */
  verticalText?: boolean

  /**
   * Space between words.
   */
  wordSpacing?: number
}
