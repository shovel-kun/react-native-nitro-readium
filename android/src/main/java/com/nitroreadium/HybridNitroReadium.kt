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
import com.margelo.nitro.nitroreadium.NitroFileSource
import com.margelo.nitro.nitroreadium.Selection
import com.margelo.nitro.nitroreadium.TapEvent
import org.readium.r2.shared.util.AbsoluteUrl
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences

@Keep
@DoNotStrip
class HybridNitroReadium(context: ThemedReactContext) : HybridNitroReadiumSpec() {

    // View
    override val view = EpubView(context).apply {
        bookService = BookService.getInstance(context)
    }

    // Props
    @Suppress("PropertyName")
    var _source: NitroFileSource = NitroFileSource(uri = "", initialLocation = null)
    override var nitroSource: NitroFileSource
        get() = _source
        set(value) {
            if (_source == value) return
            _source = value
            if (_source.uri.isEmpty()) return
            val absoluteUrl = AbsoluteUrl(value.uri) ?: throw Exception("Invalid URI")

            Promise.async {
                view.initializeNavigator(absoluteUrl, value.initialLocation)
            }
        }

    override var locator: Locator? = null
        set(value) {
            if (value == null || field == value) return
            val locatorToGoTo = value.toLocator() ?: throw Exception("Invalid locator")
            val maybeCurrentLocator = view.navigator?.currentLocator?.value
            maybeCurrentLocator?.let { currentLocator ->
                if (currentLocator == locatorToGoTo) return
            }
            field = value
            Log.d("HybridNitroReadium", "Going to locator $locatorToGoTo")
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

    override var injectedJavascriptOnPageLoad: String? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.injectedJavascriptOnPageLoad = value
        }

    override var injectedJavascriptOnResourcesLoad: String? = null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.injectedJavascriptOnResourcesLoad = value
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
            //            Log.d("HybridNitroReadium", "onTap of value: ${value}")
            if (value == null || field == value) return
            //            Log.d("HybridNitroReadium", "Invoking: ${value.invoke(TapEvent(5.0, 5.0))}")
            //            Log.d("HybridNitroReadium", "End of invoke")
            field = value
            view.onTap = value
            //            Log.d("HybridNitroReadium", "onTap of view: ${view.onTap}")
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

    override var onPageChanged: ((page: Double, totalPages: Double, locator: Locator) -> Unit)? =
        null
        set(value) {
            if (value == null || field == value) return
            field = value
            view.onPageChanged = { page, totalPages, locator ->
                this.onPageChanged?.invoke(
                    page,
                    totalPages,
                    locator
                )
            }
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
        view.clearSelection()
    }
}
