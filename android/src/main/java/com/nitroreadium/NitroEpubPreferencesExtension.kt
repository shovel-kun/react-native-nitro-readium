package com.nitroreadium

import com.margelo.nitro.nitroreadium.ColumnCount as NitroColumnCount
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences
import com.margelo.nitro.nitroreadium.ImageFilter as NitroImageFilter
import com.margelo.nitro.nitroreadium.ReadingProgression as NitroReadingProgression
import com.margelo.nitro.nitroreadium.Spread as NitroSpread
import com.margelo.nitro.nitroreadium.TextAlign as NitroTextAlign
import com.margelo.nitro.nitroreadium.Theme as NitroTheme
import org.readium.r2.navigator.epub.EpubPreferences
import org.readium.r2.navigator.epub.EpubSettings
import org.readium.r2.navigator.preferences.Color
import org.readium.r2.navigator.preferences.ColumnCount
import org.readium.r2.navigator.preferences.FontFamily
import org.readium.r2.navigator.preferences.ImageFilter
import org.readium.r2.navigator.preferences.ReadingProgression
import org.readium.r2.navigator.preferences.Spread
import org.readium.r2.navigator.preferences.TextAlign
import org.readium.r2.navigator.preferences.Theme
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.util.Language

@OptIn(ExperimentalStdlibApi::class, ExperimentalReadiumApi::class)
fun NitroEpubPreferences.toEpubPreferences(): EpubPreferences {
    return EpubPreferences(
        backgroundColor = backgroundColor?.let { Color(it.hexToInt()) },
        columnCount =
            when (columnCount) {
                NitroColumnCount.AUTO -> ColumnCount.AUTO
                NitroColumnCount._1 -> ColumnCount.ONE
                NitroColumnCount._2 -> ColumnCount.TWO
                else -> null
            },
        fontFamily = fontFamily?.let { FontFamily(it) },
        fontSize = fontSize,
        fontWeight = fontWeight,
        hyphens = hyphens,
        imageFilter = imageFilter?.let { ImageFilter.valueOf(it.name) },
        language = language?.let { Language(it) },
        letterSpacing = letterSpacing,
        ligatures = ligatures,
        lineHeight = lineHeight,
        pageMargins = pageMargins,
        paragraphIndent = paragraphIndent,
        paragraphSpacing = paragraphSpacing,
        publisherStyles = publisherStyles,
        readingProgression = readingProgression?.let { ReadingProgression.valueOf(it.name) },
        scroll = scroll,
        spread = spread?.let { Spread.valueOf(it.name) },
        textAlign = textAlign?.let { TextAlign.valueOf(it.name) },
        textColor = textColor?.let { Color(it.hexToInt()) },
        textNormalization = textNormalization,
        theme = theme?.let { Theme.valueOf(it.name) },
        typeScale = typeScale,
        verticalText = verticalText,
        wordSpacing = wordSpacing,
    )
}

@OptIn(ExperimentalReadiumApi::class)
fun EpubSettings.toNitroEpubPreferences(): NitroEpubPreferences {
    return NitroEpubPreferences(
        backgroundColor = this.backgroundColor?.int?.toHex(),
        columnCount =
            when (this.columnCount) {
                ColumnCount.AUTO -> NitroColumnCount.AUTO
                ColumnCount.ONE -> NitroColumnCount._1
                ColumnCount.TWO -> NitroColumnCount._2
            },
        fontFamily = this.fontFamily?.name,
        fontSize = this.fontSize,
        fontWeight = this.fontWeight,
        hyphens = this.hyphens,
        imageFilter = this.imageFilter?.let { NitroImageFilter.valueOf(it.name) },
        language = this.language?.code,
        letterSpacing = this.letterSpacing,
        ligatures = this.ligatures,
        lineHeight = this.lineHeight,
        pageMargins = this.pageMargins,
        paragraphIndent = this.paragraphIndent,
        paragraphSpacing = this.paragraphSpacing,
        publisherStyles = this.publisherStyles,
        readingProgression =
            this.readingProgression.let { NitroReadingProgression.valueOf(it.name) },
        scroll = this.scroll,
        spread = this.spread.let { NitroSpread.valueOf(it.name) },
        textAlign = this.textAlign?.let { NitroTextAlign.valueOf(it.name) },
        textColor = this.textColor?.int?.toHex(),
        textNormalization = this.textNormalization,
        theme = this.theme.let { NitroTheme.valueOf(it.name) },
        typeScale = this.typeScale,
        verticalText = this.verticalText,
        wordSpacing = this.wordSpacing,
    )
}

private fun Int.toHex(): String = String.format("#%08X", this)
