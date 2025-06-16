package com.nitroreadium

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.util.Log
import android.widget.FrameLayout
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import com.facebook.react.uimanager.BackgroundStyleApplicator
import com.facebook.react.uimanager.PixelUtil.pxToDp
import com.facebook.react.uimanager.ThemedReactContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.readium.r2.navigator.DecorableNavigator
import org.readium.r2.navigator.Decoration
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.navigator.epub.EpubPreferences
import org.readium.r2.navigator.preferences.FontFamily
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.Link
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.services.positions
import org.readium.r2.shared.util.AbsoluteUrl
import org.readium.r2.shared.util.getOrElse
import com.margelo.nitro.nitroreadium.Decoration as NitroDecoration
import com.margelo.nitro.nitroreadium.DecorationActivatedEvent as NitroDecorationActivatedEvent
import com.margelo.nitro.nitroreadium.DecorationStyle as NitroDecorationStyle
import com.margelo.nitro.nitroreadium.DecorationType as NitroDecorationType
import com.margelo.nitro.nitroreadium.DragEvent as NitroDragEvent
import com.margelo.nitro.nitroreadium.Locator as NitroLocator
import com.margelo.nitro.nitroreadium.Point as NitroPoint
import com.margelo.nitro.nitroreadium.Rect as NitroRect
import com.margelo.nitro.nitroreadium.Selection as NitroSelection
import com.margelo.nitro.nitroreadium.TapEvent as NitroTapEvent
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences

@SuppressLint("ViewConstructor", "ResourceType")
class EpubView(private val context: ThemedReactContext) : FrameLayout(context),
    EpubNavigatorFragment.Listener, DecorableNavigator.Listener,
    EpubNavigatorFragment.PaginationListener, EpubNavigatorFragment.MessageListener {

    //    private var initialLocator: Locator? = null
    //    private var absoluteUrl: AbsoluteUrl? = null
    //    var isPlaying: Boolean = false
    //    var readaloudColor = 0xffffff00.toInt()
    var bookService: BookService? = null
    var navigator: EpubNavigatorFragment? = null
    var customFonts: List<CustomFont> = listOf()
    var highlights: List<NitroDecoration> = listOf()
    var bookmarks: List<Locator> = listOf()
    var injectedJavascriptOnResourcesLoad: String? = null
        set(value) {
            field = "(function() {\n$value\nreturn true;\n})();"
        }
    var injectedJavascriptOnPageLoad: String? = null
        set(value) {
            field = "(function() {\n$value\nreturn true;\n})();"
        }

    @OptIn(ExperimentalReadiumApi::class)
    var preferences: EpubPreferences = EpubPreferences(
        fontFamily = FontFamily("Literata"),
        //lineHeight = 1.4,
        //paragraphSpacing = 0.5,
    )
        set(value) {
            field = value
            updatePreferences()
        }

    var turnPageOnTap: Boolean = false

    // Callbacks
    var onSelection: (NitroSelection?) -> Unit = {}
    var onLocatorChanged: (NitroLocator) -> Unit = {}
    var onTap: ((NitroTapEvent) -> Unit)? = null
    var onDrag: ((NitroDragEvent) -> Unit)? = null
    var onDecorationActivated: (NitroDecorationActivatedEvent) -> Unit = {}
    var onPageChanged: ((page: Double, totalPages: Double, locator: NitroLocator) -> Unit)? = null
    var onPageLoaded: (() -> Unit)? = null
    var onPreferencesChanged: ((NitroEpubPreferences) -> Unit)? = null
    var onMessage: ((String) -> Unit)? = null
    val onBookmarksActivate: (Map<String, Any>) -> Unit = {}

    suspend fun initializeNavigator(
        absoluteUrl: AbsoluteUrl,
        locatorOrLink: String?
    ) {
        withContext(Dispatchers.Main) {
            Log.d(
                "HybridNitroReadium",
                "absoluteUrl: $absoluteUrl, initialLocatorOrLinkString: $locatorOrLink"
            )

            val publication = bookService?.openPublication(absoluteUrl)
                ?.getOrElse { return@withContext } ?: return@withContext

            val fragmentTag = resources.getString(R.string.epub_fragment_tag)
            val activity: FragmentActivity? = context.currentActivity as FragmentActivity?

            // TODO: May not be entirely accurate, need a better way to check
            val locator: Locator = when {
                locatorOrLink == null -> publication.positions().first()
                else -> {
                    val locatorOrLinkJson = JSONObject(locatorOrLink)
                    if (locatorOrLinkJson.has("locations")) {
                        Locator.fromJSON(locatorOrLinkJson) ?: throw Exception("Invalid locator")
                    } else {
                        val link = Link.fromJSON(locatorOrLinkJson)
                            ?: throw Exception("Invalid link: Link could not be parsed")
                        publication.locatorFromLink(link)
                            ?: throw Exception("Invalid locator: Could not get locator for link")
                    }
                }
            }

            // Log.i("HybridNitroReadium", "Activity: $activity")

            val listener = this@EpubView
            val epubFragment = EpubReaderFragment(
                locator,
                publication,
                customFonts,
                listener
            )

            //  Log.i("HybridNitroReadium", "EpubFragment {$epubFragment} created")

            try {
                activity?.supportFragmentManager?.commitNow {
                    setReorderingAllowed(true)
                    add(epubFragment, fragmentTag)
                }
            } catch (e: Exception) {
                Log.e("HybridNitroReadium", "Error adding fragment: $e")
                throw e
            }

            addView(epubFragment.view)
            navigator = epubFragment.navigator

            epubFragment.apply {
                onLocatorChanged = this@EpubView.onLocatorChanged
                onSelection = this@EpubView.onSelection
                onTap = this@EpubView.onTap
                onDrag = this@EpubView.onDrag
                onPreferencesChanged = this@EpubView.onPreferencesChanged
                onDecorationActivated = this@EpubView.onDecorationActivated
            }

            decorateHighlights()
            //            navigator?.addDecorationListener("user-annotations", this@EpubView)
        }
    }

    fun destroyNavigator() {
        val navigator = this.navigator ?: return
        val activity: FragmentActivity? = context.currentActivity as FragmentActivity?
        activity?.supportFragmentManager?.commitNow {
            setReorderingAllowed(true)
            remove(navigator)
        }
        removeView(navigator.view)
    }

    fun go(locator: Locator) {
        navigator?.go(locator, true)
    }

    fun updatePreferences() {
        navigator?.submitPreferences(preferences)
    }

    fun getPreferences(): NitroEpubPreferences {
        return navigator?.settings?.value?.toNitroEpubPreferences() ?: NitroEpubPreferences(
            backgroundColor = null,
            columnCount = null,
            fontFamily = null,
            fontSize = null,
            fontWeight = null,
            hyphens = null,
            imageFilter = null,
            language = null,
            letterSpacing = null,
            ligatures = null,
            lineHeight = null,
            pageMargins = null,
            paragraphIndent = null,
            paragraphSpacing = null,
            publisherStyles = null,
            readingProgression = null,
            scroll = null,
            spread = null,
            textAlign = null,
            textColor = null,
            textNormalization = null,
            theme = null,
            typeScale = null,
            verticalText = null,
            wordSpacing = null
        )
    }

    override fun onDecorationActivated(event: DecorableNavigator.OnActivatedEvent): Boolean {
        event.point?.let {
            onTap?.invoke(
                NitroTapEvent(
                    it.x.pxToDp().toDouble(),
                    it.y.pxToDp().toDouble()
                )
            )
        }
        onDecorationActivated(
            NitroDecorationActivatedEvent(
                decoration = NitroDecoration(
                    id = event.decoration.id,
                    locator = event.decoration.locator.toNitroLocator(),
                    style = NitroDecorationStyle(
                        type = when (event.decoration.style) {
                            is Decoration.Style.Highlight -> NitroDecorationType.HIGHLIGHT
                            is Decoration.Style.Underline -> NitroDecorationType.UNDERLINE
                            else -> NitroDecorationType.HIGHLIGHT
                        },
                        tint = when (event.decoration.style) {
                            is Decoration.Style.Highlight -> (event.decoration.style as Decoration.Style.Highlight).tint.toString()
                            is Decoration.Style.Underline -> (event.decoration.style as Decoration.Style.Underline).tint.toString()
                            else -> "0x000000"
                        }
                    )
                ),
                group = event.group,
                rect = event.rect?.let {
                    NitroRect(
                        it.left.pxToDp().toDouble(),
                        it.top.pxToDp().toDouble(),
                        it.right.pxToDp().toDouble(),
                        it.bottom.pxToDp().toDouble()
                    )
                },
                point = event.point?.let {
                    NitroPoint(
                        it.x.pxToDp().toDouble(),
                        it.y.pxToDp().toDouble()
                    )
                }
            )
        )
        return true
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun decorateHighlights() {
        val decorations = highlights.map {
            val style = Decoration.Style.Highlight(it.style.tint.hexToInt())
            return@map Decoration(
                id = it.id,
                locator = it.locator.toLocator() ?: throw Exception("Invalid locator"),
                style = style
            )
        }

        val activity: FragmentActivity? = context.currentActivity as FragmentActivity?
        activity?.lifecycleScope?.launch {
            navigator?.applyDecorations(decorations, group = "user-annotations")
        }
    }

    suspend fun evaluateJavascript(script: String): String? {
        val result = navigator?.evaluateJavascript(script)

        return result
            ?: throw Exception("Failed to evaluate. Either webview has not been initialized yet, or the resource is not reflowable")
    }

    fun injectJavascript(script: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = navigator?.evaluateJavascript(script.trimIndent())

            result
                ?: throw Exception("Failed to inject. Either webview has not been initialized yet, or the resource is not reflowable")
        }
    }

    override fun onPageChanged(pageIndex: Int, totalPages: Int, locator: Locator) {
        onPageChanged?.invoke(
            pageIndex.toDouble(),
            totalPages.toDouble(),
            locator.toNitroLocator()
        )
    }

    override fun onPageLoaded() {
        onPageLoaded?.invoke()
        injectedJavascriptOnPageLoad?.let { injectJavascript(it) }
    }

    override fun onResourceLoaded(): String? {
        return injectedJavascriptOnResourcesLoad
    }

    override fun onMessage(message: String) {
        onMessage?.invoke(message)
    }

    fun clearSelection() {
        // post needed to avoid calling webview method on wrong thread
        // https://stackoverflow.com/a/60500352
        post(Runnable { navigator?.clearSelection() })
    }

    @ExperimentalReadiumApi
    override fun onExternalLinkActivated(url: AbsoluteUrl) {
        Log.d("hi", "hi")
    }

    // Required for proper layout!
    // Forces React Native to use the Android layout system for this view, rather than RN's.
    // Without this, the ViewPager and WebViews will be laid out incorrectly
    @UiThread
    fun measureAndLayout() {
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
        layout(left, top, right, bottom)
    }

    override fun requestLayout() {
        super.requestLayout()
        post(Runnable { measureAndLayout() })
    }

    fun clipToPaddingBox(canvas: Canvas) {
        // When the border radius is set, we need to clip the content to the padding box.
        // This is because the border radius is applied to the background drawable, not the view itself.
        // It is the same behavior as in React Native.
        if (!clipToPadding) {
            return
        }
        BackgroundStyleApplicator.clipToPaddingBox(this, canvas)
    }

    override fun dispatchDraw(canvas: Canvas) {
        clipToPaddingBox(canvas)
        super.dispatchDraw(canvas)
    }
}

data class CustomFont(val uri: String, val name: String, val type: String)
