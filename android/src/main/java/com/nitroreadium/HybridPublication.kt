package com.nitroreadium

import android.graphics.Bitmap
import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitroreadium.HybridPublicationSpec
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.opds.images
import org.readium.r2.shared.publication.services.cover
import org.readium.r2.shared.toJSON
import com.margelo.nitro.NitroModules
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.services.content.Content
import org.readium.r2.shared.publication.services.content.content
import java.io.File
import java.io.FileOutputStream
import android.graphics.BitmapFactory
import com.facebook.jni.HybridData
import org.readium.r2.shared.util.getOrElse
import org.readium.r2.shared.util.resource.Resource

@Suppress("unused")
@Keep
@DoNotStrip
class HybridPublication(val publication: Publication) : HybridPublicationSpec() {

    val context = NitroModules.applicationContext
    override var manifest: String = publication.manifest.toJSON().toString()
    override var tableOfContents: String = publication.tableOfContents.toJSON().toString()
    override var images: String = publication.images.toJSON().toString()
    override var metadata: String = publication.metadata.toJSON().toString()

    // Using caching logic from
    // https://github.com/oblador/react-native-vector-icons/blob/master/packages/common/android/src/main/java/com/reactnativevectoricons/common/VectorIconsModule.kt
    override fun cover(): Promise<String> {
        // Validate cache directory
        val cacheDir = context?.cacheDir ?: return Promise.rejected(Exception("Missing context"))
        val cacheDirPath = "${cacheDir.absolutePath}/"

        // Generate cache file path
        val cacheKey = publication.metadata.identifier ?: publication.metadata.toJSON().toString()
        val cacheFileName = "${cacheKey.hashCode().toString(32)}.png"
        val cacheFile = File(cacheDirPath, cacheFileName)

        // Return cached file if it exists
        if (cacheFile.exists()) {
            return Promise.async { "file://${cacheFile.absolutePath}" }
        }

        return Promise.async {
            var bitmapWritten = false

            FileOutputStream(cacheFile).use { outputStream ->
                getCoverBitmap()?.let { bitmap ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                    bitmapWritten = true
                }
            }

            if (bitmapWritten) {
                "file://${cacheFile.absolutePath}"
            } else {
                // Clean up empty file if no image was found
                cacheFile.delete()
                throw Exception("No cover image found in publication")
            }
        }
    }

    /**
     * Attempts to retrieve a cover bitmap from the publication, first trying the explicit cover,
     * then falling back to the first image element in the content.
     */
    @OptIn(ExperimentalReadiumApi::class)
    private suspend fun getCoverBitmap(): Bitmap? {
        // Try the explicit cover first
        publication.cover()?.let { return it }

        // Fall back to the first content image
        val firstImageLink = publication.content()
            ?.elements()
            ?.filterIsInstance<Content.ImageElement>()
            ?.firstOrNull()
            ?.embeddedLink ?: return null

        val resource = publication.get(firstImageLink)
            ?: throw Exception("Resource for cover image not found")

        return resource.readAsBitmap()
    }

    /**
     * Reads the resource content and decodes it into a Bitmap.
     * @throws Exception if there's an error reading or decoding the data
     */
    private suspend fun Resource.readAsBitmap(): Bitmap {
        val bytes = read().getOrElse { throw Exception("Failed to read resource: ${it.message}") }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?: throw Exception("Failed to decode bitmap from resource data")
    }

    override val memorySize: Long
        get() {
            val objectOverhead = 16L // Object header + padding
            val stringOverhead = 40L // String object overhead
            val manifestSize = stringOverhead + (manifest.length * 2)
            return manifestSize + objectOverhead
        }
}

// val baos = ByteArrayOutputStream()
// publication.cover()?.compress(Bitmap.CompressFormat.JPEG, 100, baos) ?: throw Exception("No cover found")
// val b = baos.toByteArray()
// Base64.encodeToString(b, Base64.DEFAULT)