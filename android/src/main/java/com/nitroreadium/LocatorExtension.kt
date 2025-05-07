package com.nitroreadium

import com.margelo.nitro.nitroreadium.Locations as NitroLocations
import com.margelo.nitro.nitroreadium.Locator as NitroLocator
import com.margelo.nitro.nitroreadium.TextObject as NitroTextObject
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.util.Url
import org.readium.r2.shared.util.mediatype.MediaType

fun Locator.toNitroLocator(): NitroLocator {
    return NitroLocator(
        href = href.toString(),
        type = mediaType.toString(),
        title = title,
        locations =
            NitroLocations(
                fragments = locations.fragments.toTypedArray(),
                progression = locations.progression,
                position = locations.position?.toDouble(),
                totalProgression = locations.totalProgression,
            ),
        text = NitroTextObject(before = text.before, highlight = text.highlight, after = text.after),
    )
}

fun NitroLocator.toLocator(): Locator? {
    val url = Url(href) ?: return null
    val mediaType = MediaType(type) ?: return null

    return Locator(
        href = url,
        mediaType = mediaType,
        title = title,
        locations =
            Locator.Locations(
                fragments = locations?.fragments?.toList() ?: listOf(),
                progression = locations?.progression,
                position = locations?.position?.toInt(),
                totalProgression = locations?.totalProgression,
            ),
        text = Locator.Text(before = text?.before, highlight = text?.highlight, after = text?.after),
    )
}
