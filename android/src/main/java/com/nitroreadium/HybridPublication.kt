package com.nitroreadium

import android.graphics.Bitmap
import android.util.Base64
import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitroreadium.HybridPublicationSpec
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.opds.images
import org.readium.r2.shared.publication.services.cover
import org.readium.r2.shared.toJSON
import java.io.ByteArrayOutputStream
import com.margelo.nitro.NitroModules
import java.io.File
import java.io.FileOutputStream


@Suppress("unused")
@Keep
@DoNotStrip
class HybridPublication(val publication: Publication) : HybridPublicationSpec() {

    val context = NitroModules.applicationContext
    override var manifest: String = publication.manifest.toJSON().toString()
    override var tableOfContents: String = publication.tableOfContents.toJSON().toString()
    override var images: String = publication.images.toJSON().toString()
    //  override var metadata: String = publication.metadata.toJSON().toString()

    // TODO: Use caching logic from
    // https://github.com/oblador/react-native-vector-icons/blob/ff6d043e1098ff2dc44a57c970f7932b70d2448a/packages/common/android/src/main/java/com/reactnativevectoricons/common/VectorIconsModule.kt#L4
    override fun cover(): Promise<String> {
        val cacheFolder = context?.cacheDir ?: return Promise.rejected(Exception("Missing context"))
        val cacheFolderPath = "${cacheFolder.absolutePath}/"
        val cacheKey = publication.metadata.identifier ?: publication.metadata.toJSON().toString()
        val hash = cacheKey.hashCode().toString(32)
        val cacheFilePath = "${cacheFolderPath}${hash}.png"
        val cacheFileUrl = "file://$cacheFilePath"
        val cacheFile = File(cacheFilePath)

        if (cacheFile.exists()) {
            return Promise.async { cacheFileUrl }
        }

        // val baos = ByteArrayOutputStream()
        // publication.cover()?.compress(Bitmap.CompressFormat.JPEG, 100, baos) ?: throw Exception("No cover found")
        // val b = baos.toByteArray()
        // Base64.encodeToString(b, Base64.DEFAULT)

        return Promise.async {
            FileOutputStream(cacheFile).use { fos ->
                publication.cover()?.compress(Bitmap.CompressFormat.PNG, 100, fos)
                    ?: throw Exception("No cover found")
                fos.flush()
                cacheFileUrl
            }
        }
    }
}