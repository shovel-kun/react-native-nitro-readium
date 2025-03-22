package com.nitroreadium

import org.readium.r2.navigator.epub.EpubPreferences
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
import com.margelo.nitro.nitroreadium.ColumnCount as NitroColumnCount
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences

@OptIn(ExperimentalStdlibApi::class, ExperimentalReadiumApi::class)
fun NitroEpubPreferences.toEpubPreferences(): EpubPreferences {
    return EpubPreferences(
        backgroundColor = backgroundColor?.let { Color(it.hexToInt()) },
        columnCount = when (columnCount) {
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
