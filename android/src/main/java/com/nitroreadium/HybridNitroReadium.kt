package com.nitroreadium

import android.util.Log
import androidx.annotation.Keep
import com.facebook.proguard.annotations.DoNotStrip
import com.facebook.react.uimanager.ThemedReactContext
import com.margelo.nitro.nitroreadium.HybridNitroReadiumSpec
import com.margelo.nitro.core.Promise
import com.margelo.nitro.nitroreadium.Decoration
import com.margelo.nitro.nitroreadium.DecorationActivatedEvent
import com.margelo.nitro.nitroreadium.DragEvent
import com.margelo.nitro.nitroreadium.Locator
import com.margelo.nitro.nitroreadium.Selection
import com.margelo.nitro.nitroreadium.TapEvent
import org.readium.r2.shared.util.AbsoluteUrl
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences

@Keep
@DoNotStrip
class HybridNitroReadium(context: ThemedReactContext): HybridNitroReadiumSpec() {
    val bookService = BookService.getInstance(context)

    // View
    override val view = EpubView(context).apply {
        bookService = this@HybridNitroReadium.bookService
    }

    // Props
    override var absolutePath: String? = null
        set(value) {
            if (value == null || field == value ) return
            field = value
            view.absoluteUrl = AbsoluteUrl(value)
            Promise.async {
                view.initializeNavigator()
            }
        }

    override var locator: Locator? = null
        set(value) {
            if (value == null || field == value) return
            val locatorToGoTo = value.toLocator() ?: return
            val maybeCurrentLocator = view.navigator?.currentLocator?.value
            maybeCurrentLocator?.let { currentLocator ->
                if (currentLocator == locatorToGoTo) return
            }
            field = value
            view.go(locatorToGoTo)
        }

    override var preferences: NitroEpubPreferences? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.preferences = value.toEpubPreferences()
        }

    override var decorations: Array<Decoration>? = emptyArray()
        set(value) {
            if (value == null || field == value) return
            field = value
            view.highlights = value.toList()
            view.decorateHighlights()
        }

    override var injectedJavascript: String? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.injectedJavascript = value
        }

    override var injectedJavascriptTarget: String? = null
        set(value) {
            if (value == null || field == value) return
            field = value
//            view.injectedJavascript = value
        }

    // Callbacks
    override var onLocatorChanged: ((Locator) -> Unit)? = {}
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onLocatorChanged = { this.onLocatorChanged?.invoke(it) }
        }

    override var onSelection: ((Selection?) -> Unit)? = {}
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onSelection = { this.onSelection?.invoke(it) }
        }

    override var onTap: ((TapEvent) -> Unit)? = null
        set(value) {
            Log.d("HybridNitroReadium", "onTap of value: ${value}")
//            if (value == null || field == value) return
            Log.d("HybridNitroReadium", "Invoking: ${value?.invoke(TapEvent(5.0, 5.0))}")
            Log.d("HybridNitroReadium", "End of invoke")
            field = value
            view.onTap = value
            Log.d("HybridNitroReadium", "onTap of view: ${view.onTap}")
        }

    override var onDrag: ((DragEvent) -> Unit)? = {}
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onDrag = { this.onDrag?.invoke(it) }
        }

    override var onDecorationActivated: ((DecorationActivatedEvent) -> Unit)? = {}
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onDecorationActivated = { this.onDecorationActivated?.invoke(it) }
        }

    override var onPageChanged: ((page: Double, totalPages: Double, locator: Locator) -> Unit)? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onPageChanged = { page, totalPages, locator -> this.onPageChanged?.invoke(page, totalPages, locator) }
        }

    override var onPageLoaded: (() -> Unit)? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onPageLoaded = { this.onPageLoaded?.invoke() }
        }

    override var onMessage: ((String) -> Unit)? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onMessage = { this.onMessage?.invoke(it) }
        }


    // Methods
    override fun evaluateJavascript(script: String): Promise<String?> {
        return Promise.async {
            view.evaluateJavascript(script)
        }
    }

    override fun injectJavascript(script: String) {
        view.injectJavascript(script)
    }

    override fun go(locator: Locator) {
        view.go(locator.toLocator() ?: throw Exception("Invalid locator"))
    }

    override fun clearSelection() {
        try {
            view.clearSelection()
        } catch (e: Exception) {
            Log.e("HybridReadiumNitro", "Error clearing selection", e)
        }
    }
}
