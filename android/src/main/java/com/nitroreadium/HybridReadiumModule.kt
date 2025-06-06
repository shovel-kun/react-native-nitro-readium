package com.nitroreadium

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.NitroModules
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitroreadium.HybridPublicationSpec
import com.margelo.nitro.nitroreadium.HybridReadiumModuleSpec
import org.readium.r2.shared.util.AbsoluteUrl
import org.readium.r2.shared.util.getOrElse

@Suppress("unused")
@Keep
@DoNotStrip
class HybridReadiumModule: HybridReadiumModuleSpec() {

    val bookService = BookService.getInstance(NitroModules.applicationContext as Context)

    override fun add(a: Double, b: Double): Double {
        return a + b
    }

    override fun openPublication(absoluteUrl: String): Promise<HybridPublicationSpec?> {
        return try {
            val cleanAbsoluteUrl = AbsoluteUrl(absoluteUrl) ?: return Promise.rejected(Exception("Invalid absoluteUrl: $absoluteUrl"))
            Promise.async {
                val publication = bookService.openPublication(cleanAbsoluteUrl).getOrElse { throw Exception("Cannot open publication: $it") }
                HybridPublication(publication)
            }
        } catch (e: Exception) {
            Log.e("HybridReadiumModule", "Error getting manifest: $e")
            Promise.rejected(e)
        }
    }
}