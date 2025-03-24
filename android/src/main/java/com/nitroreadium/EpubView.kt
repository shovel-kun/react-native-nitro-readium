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
import kotlinx.serialization.json.Json
import org.readium.r2.navigator.DecorableNavigator
import org.readium.r2.navigator.Decoration
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.navigator.epub.EpubPreferences
import org.readium.r2.navigator.preferences.FontFamily
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.InternalReadiumApi
import org.readium.r2.shared.extensions.toMap
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

data class CustomFont(val uri: String, val name: String, val type: String)

@SuppressLint("ViewConstructor", "ResourceType")
class EpubView(private val context: ThemedReactContext) : FrameLayout(context),
    EpubNavigatorFragment.Listener, DecorableNavigator.Listener, EpubNavigatorFragment.PaginationListener, EpubNavigatorFragment.MessageListener {

    var appContext = context
    var bookService: BookService? = null
    var initialLocator: Locator? = null
    var locator: Locator? = null
    var isPlaying: Boolean = false
    var navigator: EpubNavigatorFragment? = null
    var customFonts: List<CustomFont> = listOf()
    var highlights: List<NitroDecoration> = listOf()
    var bookmarks: List<Locator> = listOf()
    var readaloudColor = 0xffffff00.toInt()
    var injectedJavascript: String? = null
        set(value) {
            field = "(function() {\n$value\nreturn true;\n})();"
        }
    @OptIn(ExperimentalReadiumApi::class)
    var preferences: EpubPreferences = EpubPreferences(
        fontFamily = FontFamily("Literata"),
        lineHeight = 1.4,
        paragraphSpacing = 0.5,
    )
        set(value) {
            field = value
            Log.i("Preferences", value.toString())
            navigator?.submitPreferences(value)
        }
    var absoluteUrl: AbsoluteUrl? = null

    // Callbacks

    var onSelection: (NitroSelection?) -> Unit = {}
    var onLocatorChanged: (NitroLocator) -> Unit = {}
    var onTap: ((NitroTapEvent) -> Unit)? = null
    var onDrag: (NitroDragEvent) -> Unit = {}
    var onDecorationActivated: (NitroDecorationActivatedEvent) -> Unit = {}
    var onPageChanged: ((page: Double, totalPages: Double, locator: NitroLocator) -> Unit)? = null
    var onPageLoaded: (() -> Unit)? = null
    var onMessage: ((String) -> Unit)? = null

    val onBookmarksActivate: (Map<String, Any>) -> Unit = {}

    suspend fun initializeNavigator() {
        withContext(Dispatchers.Main) {
            Log.d("HybridNitroReadium", "absoluteUrl: $absoluteUrl")
            val publication = absoluteUrl?.let {
                bookService?.openPublication(it) ?: return@withContext
            } ?: return@withContext

            Log.d("HybridNitroReadium", "publication: $publication")
            val successPublication = publication.getOrElse { return@withContext }
            Log.d("HybridNitroReadium", "successPublication: $successPublication")
            Log.d("HybridNitroReadium", "${successPublication.positions()}")
            Log.d("HybridNitroReadium", "${successPublication.metadata}")

            val newLocator = successPublication.positions()[0]

            if (newLocator == null) {
                Log.d("HybridNitroReadium", "newLocator is null")
                return@withContext
            }

            Log.d("HybridNitroReadium", "newLocator: $newLocator")

            val fragmentTag = resources.getString(R.string.epub_fragment_tag)
            val activity: FragmentActivity? = appContext.currentActivity as FragmentActivity?

            Log.i("HybridNitroReadium", "Activity: $activity")

            val listener = this@EpubView
            val epubFragment = EpubReaderFragment(
                newLocator,
                successPublication,
                customFonts,
                listener
            )

            Log.i("HybridNitroReadium", "EpubFragment {$epubFragment} created")

            try {
                activity?.supportFragmentManager?.commitNow {
                    setReorderingAllowed(true)
                    add(epubFragment, fragmentTag)
                }
            } catch (e: Exception) {
                Log.e("HybridNitroReadium", "Error adding fragment: $e")
            }

            addView(epubFragment.view)

            navigator = epubFragment.navigator

            epubFragment.apply {
                onLocatorChanged = this@EpubView.onLocatorChanged
                onSelection = this@EpubView.onSelection
                onTap = this@EpubView.onTap
                onDrag = this@EpubView.onDrag
            }

            decorateHighlights()
            navigator?.addDecorationListener("user-annotations", this@EpubView)
        }
    }

    fun destroyNavigator() {
        val navigator = this.navigator ?: return
        val fragmentTag = resources.getString(R.string.epub_fragment_tag)
        val activity: FragmentActivity? = appContext.currentActivity as FragmentActivity?
        activity?.supportFragmentManager?.commitNow {
            setReorderingAllowed(true)
            remove(navigator)
        }
        removeView(navigator.view)
    }

    fun go(locator: Locator) {
//        val navigator = this.navigator ?: run {
//            this.initialLocator = locator
//            return initializeNavigator()
//        }

        navigator?.go(locator, true)
    }

    fun updatePreferences() {
        navigator?.submitPreferences(preferences)
    }

    override fun onDecorationActivated(event: DecorableNavigator.OnActivatedEvent): Boolean {
        Log.d("EpubView", "onTap in EpubView: ${onTap}")
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
                rect = event.rect?.let { NitroRect(it.left.pxToDp().toDouble(), it.top.pxToDp().toDouble(), it.right.pxToDp().toDouble(), it.bottom.pxToDp().toDouble()) },
                point = event.point?.let { NitroPoint(it.x.pxToDp().toDouble(), it.y.pxToDp().toDouble()) }
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
                // If invalid, short circuit?
                locator = it.locator.toLocator() ?: return,
                style = style
            )
        }

        val activity: FragmentActivity? = appContext.currentActivity as FragmentActivity?
        activity?.lifecycleScope?.launch {
            navigator?.applyDecorations(decorations, group = "user-annotations")
        }
    }

    suspend fun evaluateJavascript(script: String): String? {
        val result = navigator?.evaluateJavascript(script)

        if (result == null) {
            throw Exception("Failed to evaluate. Either webview has not been initialized yet, or the resource is not reflowable")
        } else {
            return result
        }
    }

    fun injectJavascript(script: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = navigator?.evaluateJavascript(script.trimIndent())

            if (result == null) {
                throw Exception("Failed to inject. Either webview has not been initialized yet, or the resource is not reflowable")
            } else {
                Log.d("EpubView", result)
            }
        }
    }

    @OptIn(InternalReadiumApi::class)
    suspend fun findOnPage(locator: Locator) {
        val epubNav = navigator ?: return
        val currentProgression = locator.locations.progression ?: return

        val joinedProgressions =
            bookmarks
                .filter { it.href == locator.href }
                .mapNotNull { it.locations.progression }
                .joinToString { it.toString() }


        val jsProgressionsArray = "[${joinedProgressions}]"

        val result = epubNav.evaluateJavascript(
            """
            (function() {
                const maxScreenX = window.orientation === 0 || window.orientation == 180
                        ? screen.width
                        : screen.height;

                function snapOffset(offset) {
                    const value = offset + 1;

                    return value - (value % maxScreenX);
                }

                const documentWidth = document.scrollingElement.scrollWidth;
                const currentPageStart = snapOffset(documentWidth * ${currentProgression});
                const currentPageEnd = currentPageStart + maxScreenX;
                return ${jsProgressionsArray}.filter((progression) =>
                    progression * documentWidth >= currentPageStart &&
                    progression * documentWidth < currentPageEnd
                );
            })();
            """.trimIndent()
        ) ?: return onBookmarksActivate(mapOf("activeBookmarks" to listOf<Locator>()))

        val parsed = Json.decodeFromString<List<Double>>(result)
        val found = bookmarks.filter {
            val progression = it.locations.progression ?: return@filter false
            return@filter parsed.contains(progression)
        }

        onBookmarksActivate(mapOf("activeBookmarks" to found.map { it.toJSON().toMap() }))
    }

//    fun setupUserScript(): EpubView {
//        val activity: FragmentActivity? = appContext.currentActivity as FragmentActivity?
//        activity?.lifecycleScope?.launch {
//            navigator?.evaluateJavascript(
//                """
//                function addDoubleTapListeners() {
//                    let storytellerDoubleClickTimeout = null;
//                    let storytellerTouchMoved = false;
//                    for (const fragment of globalThis.storytellerFragments) {
//                        const element = document.getElementById(fragment);
//                        if (!element) continue;
//                        element.addEventListener('touchstart', (event) => {
//                            storytellerTouchMoved = false;
//                        });
//                        element.addEventListener('touchmove', (event) => {
//                            storytellerTouchMoved = true;
//                        });
//                        element.addEventListener('touchend', (event) => {
//                            if (storytellerTouchMoved || !document.getSelection().isCollapsed || event.changedTouches.length !== 1) return;
//
//                            event.bubbles = true
//                            event.clientX = event.changedTouches[0].clientX
//                            event.clientY = event.changedTouches[0].clientY
//                            const clone = new MouseEvent('click', event);
//                            event.stopImmediatePropagation();
//                            event.preventDefault();
//
//                            if (storytellerDoubleClickTimeout) {
//                                clearTimeout(storytellerDoubleClickTimeout);
//                                storytellerDoubleClickTimeout = null;
//                                storyteller.handleDoubleTap(fragment);
//                                return
//                            }
//
//                            storytellerDoubleClickTimeout = setTimeout(() => {
//                                storytellerDoubleClickTimeout = null;
//                                element.parentElement.dispatchEvent(clone);
//                            }, 350);
//                        })
//                    }
//                }
//
//                document.addEventListener('selectionchange', () => {
//                    if (document.getSelection().isCollapsed) {
//                        storyteller.handleSelectionCleared();
//                    }
//                });
//                """.trimIndent()
//            )
//        }
//
//        return this
//    }

//    @OptIn(InternalReadiumApi::class)
//    private suspend fun onLocatorChanged(locator: Locator) {
//        if (isPlaying) {
//            return
//        }
//
//        findOnPage(locator)
//
//        if (locator.href !== this.locator?.href) {
//            val bookId = this.bookId ?: return
//
//            val locator = this.locator ?: this.navigator?.currentLocator?.value ?: return
//            val fragments = bookService?.getFragments(bookId, locator) ?: return
//
//            val joinedFragments = fragments.joinToString { "\"${it.fragment}\"" }
//            val jsFragmentsArray = "[${joinedFragments}]"
//
//            navigator?.evaluateJavascript(
//                """
//                globalThis.storytellerFragments = ${jsFragmentsArray};
//                addDoubleTapListeners();
//            """.trimIndent()
//            )
//        }
//
//        Log.d("EpubView", "Navigated to ${locator.locations.position}")
//
//        val result = navigator?.evaluateJavascript(
//            """
//            (function() {
//                function isEntirelyOnScreen(element) {
//                    const rects = element.getClientRects();
//                    console.log(element.id, rects);
//                    return Array.from(rects).every((rect) => {
//                        const isVerticallyWithin = rect.bottom >= 0 && rect.top <= window.innerHeight;
//                        const isHorizontallyWithin = rect.right >= 0 && rect.left <= window.innerWidth;
//                        return isVerticallyWithin && isHorizontallyWithin;
//                    });
//                }
//
//                for (const fragment of globalThis.storytellerFragments) {
//                    const element = document.getElementById(fragment);
//                    if (!element) continue;
//                    if (isEntirelyOnScreen(element)) {
//                        return fragment;
//                    }
//                }
//
//                return null;
//            })();
//        """.trimIndent()
//        )
//        Log.d("EpubView", "result: $result")
//        if (result == null) {
//            return onLocatorChange(locator.toJSON().toMap())
//        }
//        val fragment = Json.decodeFromString<String?>(result)
//            ?: return onLocatorChange(locator.toJSON().toMap())
//
//        val fragmentsLocator =
//            locator.copy(locations = locator.locations.copy(fragments = listOf(fragment)))
//        onLocatorChange(fragmentsLocator.toJSON().toMap())
//
//        this.locator = locator
//    }

    override fun onPageChanged(pageIndex: Int, totalPages: Int, locator: Locator) {
        onPageChanged?.invoke(
            pageIndex.toDouble(),
            totalPages.toDouble(),
            locator.toNitroLocator()
        )
    }

    override fun onPageLoaded() {
        onPageLoaded?.invoke()
//        injectedJavascript?.let { injectJavascript(it) }
    }

    override fun onResourceLoaded(): String? {
        return injectedJavascript
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