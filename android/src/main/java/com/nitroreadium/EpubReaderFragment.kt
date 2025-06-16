/*
 * Copyright 2021 Readium Foundation. All rights reserved.
 * Use of this source code is governed by the BSD-style license
 * available in the top-level LICENSE file of the project.
 */

package com.nitroreadium

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.ColorInt
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.facebook.react.uimanager.PixelUtil.pxToDp
import kotlinx.coroutines.launch
import org.readium.r2.navigator.DecorableNavigator
import org.readium.r2.navigator.Decoration
import org.readium.r2.navigator.OverflowableNavigator
import org.readium.r2.navigator.SelectableNavigator
import org.readium.r2.navigator.VisualNavigator
import org.readium.r2.navigator.epub.*
import org.readium.r2.navigator.html.HtmlDecorationTemplate
import org.readium.r2.navigator.html.toCss
import org.readium.r2.navigator.input.DragEvent
import org.readium.r2.navigator.input.InputListener
import org.readium.r2.navigator.input.TapEvent
import org.readium.r2.navigator.util.BaseActionModeCallback
import org.readium.r2.navigator.util.DirectionalNavigationAdapter
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.epub.pageList
import com.margelo.nitro.nitroreadium.Locator as NitroLocator
import com.margelo.nitro.nitroreadium.Selection as NitroSelection
import com.margelo.nitro.nitroreadium.Rect as NitroRect
import com.margelo.nitro.nitroreadium.Point as NitroPoint
import com.margelo.nitro.nitroreadium.TapEvent as NitroTapEvent
import com.margelo.nitro.nitroreadium.DragEvent as NitroDragEvent
import com.margelo.nitro.nitroreadium.DragEventType as NitroDragEventType
import com.margelo.nitro.nitroreadium.EpubPreferences as NitroEpubPreferences
import com.margelo.nitro.nitroreadium.DecorationActivatedEvent as NitroDecorationActivatedEvent

@OptIn(ExperimentalReadiumApi::class)
class EpubReaderFragment(
    private var locator: Locator,
    private val publication: Publication,
    private val customFonts: List<CustomFont>,
    private val listener: EpubView,
    private var turnPageOnTap: Boolean = false
) : VisualReaderFragment() {

    override lateinit var navigator: EpubNavigatorFragment
    //    val preferences: StateFlow<EpubPreferences>? = null

    var onLocatorChanged: ((NitroLocator) -> Unit) = {}
    var onSelection: ((NitroSelection?) -> Unit) = {}
    var onTap: ((NitroTapEvent) -> Unit)? = null
    var onDrag: ((NitroDragEvent) -> Unit)? = null
    var onDecorationActivated: ((NitroDecorationActivatedEvent) -> Unit)? = null
    private var _onPreferencesChanged: ((NitroEpubPreferences) -> Unit)? = null
    var onPreferencesChanged: ((NitroEpubPreferences) -> Unit)?
        get() = _onPreferencesChanged
        set(value) {
            _onPreferencesChanged = value
            // When callback is set, immediately send current value
            value?.invoke(navigator.settings.value.toNitroEpubPreferences())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        // if (savedInstanceState != null) {
        //     isSearchViewIconified = savedInstanceState.getBoolean(IS_SEARCH_VIEW_ICONIFIED)
        // }

        val epubNavigatorFactory = EpubNavigatorFactory(
            publication,
            EpubNavigatorFactory.Configuration(
                defaults = EpubDefaults(
                    publisherStyles = true
                ),
            )
        )

        childFragmentManager.fragmentFactory = epubNavigatorFactory
            .createFragmentFactory(
                initialLocator = locator,
                initialPreferences = listener.preferences,
                listener = listener,
                paginationListener = listener,
                messageListener = listener,
                configuration = EpubNavigatorFragment.Configuration {
                    // selectionActionModeCallback = customSelectionActionModeCallback

                    // Register the HTML templates for our custom decoration styles.
                    decorationTemplates[DecorationStyleAnnotationMark::class] =
                        annotationMarkTemplate()
                    decorationTemplates[DecorationStylePageNumber::class] = pageNumberTemplate()
                },
            )

        //        val editor = epubNavigatorFactory.createPreferencesEditor(listener.preferences)

        //        val readerData = model.readerInitData as? EpubReaderInitData ?: run {
        //            // We provide a dummy fragment factory  if the ReaderActivity is restored after the
        //            // app process was killed because the ReaderRepository is empty. In that case, finish
        //            // the activity as soon as possible and go back to the previous one.
        //            childFragmentManager.fragmentFactory = EpubNavigatorFragment.createDummyFactory()
        //            super.onCreate(savedInstanceState)
        //            requireActivity().finish()
        //            return
        //        }

        //        childFragmentManager.fragmentFactory =
        //            readerData.navigatorFactory.createFragmentFactory(
        ////                initialLocator = readerData.initialLocation,
        ////                initialPreferences = readerData.preferencesManager.preferences.value,
        ////                listener = model,
        //                configuration = EpubNavigatorFragment.Configuration {
        //                    // To customize the text selection menu.
        ////                    selectionActionModeCallback = customSelectionActionModeCallback
        //
        //                    // App assets which will be accessible from the EPUB resources.
        //                    // You can use simple glob patterns, such as "images/.*" to allow several
        //                    // assets in one go.
        //                    servedAssets = listOf(
        //                        // For the custom font Literata.
        //                        "fonts/.*",
        //                        // Icon for the annotation side mark, see [annotationMarkTemplate].
        //                        "annotation-icon.svg"
        //                    )
        //
        //                    // Register the HTML templates for our custom decoration styles.
        ////                    decorationTemplates[DecorationStyleAnnotationMark::class] = annotationMarkTemplate()
        ////                    decorationTemplates[DecorationStylePageNumber::class] = pageNumberTemplate()
        //
        //                    // Declare a custom font family for reflowable EPUBs.
        ////                    addFontFamilyDeclaration(FontFamily.LITERATA) {
        ////                        addFontFace {
        ////                            addSource("fonts/Literata-VariableFont_opsz,wght.ttf")
        ////                            setFontStyle(FontStyle.NORMAL)
        ////                            // Literata is a variable font family, so we can provide a font weight range.
        ////                            setFontWeight(200..900)
        ////                        }
        ////                        addFontFace {
        ////                            addSource("fonts/Literata-Italic-VariableFont_opsz,wght.ttf")
        ////                            setFontStyle(FontStyle.ITALIC)
        ////                            setFontWeight(200..900)
        ////                        }
        ////                    }
        //                }
        //            )

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (savedInstanceState == null) {
            childFragmentManager.commitNow {
                add(
                    R.id.fragment_reader_container,
                    EpubNavigatorFragment::class.java,
                    Bundle(),
                    NAVIGATOR_FRAGMENT_TAG
                )
            }
        }
        navigator =
            childFragmentManager.findFragmentByTag(NAVIGATOR_FRAGMENT_TAG) as EpubNavigatorFragment

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (navigator as DecorableNavigator)

        // Order matters, VisualNavigator must be added before OverflowableNavigator as the latter
        // will consume the tap events.
        (navigator as VisualNavigator).apply {
            addInputListener(object : InputListener {
                override fun onTap(event: TapEvent): Boolean {
                    // Log.d("EpubReaderFragment", "onTap ${onTap}")
                    onTap?.invoke(
                        NitroTapEvent(
                            event.point.x.pxToDp().toDouble(),
                            event.point.y.pxToDp().toDouble(),
                        )
                    )
                    return super.onTap(event)
                }

                override fun onDrag(event: DragEvent): Boolean {
                    onDrag?.invoke(
                        NitroDragEvent(
                            when (event.type) {
                                DragEvent.Type.Start -> NitroDragEventType.START
                                DragEvent.Type.Move -> NitroDragEventType.MOVE
                                DragEvent.Type.End -> NitroDragEventType.END
                            },
                            NitroPoint(
                                event.start.x.pxToDp().toDouble(),
                                event.start.y.pxToDp().toDouble(),
                            ),
                            NitroPoint(
                                event.offset.x.pxToDp().toDouble(),
                                event.offset.y.pxToDp().toDouble(),
                            )
                        )
                    )
                    return super.onDrag(event)
                }
            })
        }

        Log.d("Hi", "turnPageOnTap: $turnPageOnTap")
        if (turnPageOnTap) {
            (navigator as OverflowableNavigator).apply {
                // This will automatically turn pages when tapping the screen edges or arrow keys.
                addInputListener(DirectionalNavigationAdapter(this))
            }
        }

        setupObservers()

        //        @Suppress("Unchecked_cast")
        //        (model.settings as UserPreferencesViewModel<EpubSettings, EpubPreferences>)
        //            .bind(navigator, viewLifecycleOwner)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Display page number labels if the book contains a `page-list` navigation document.
                (navigator as? DecorableNavigator)?.applyPageNumberDecorations()
            }
        }

        //        val menuHost: MenuHost = requireActivity()
        //
        //        menuHost.addMenuProvider(
        //            object : MenuProvider {
        //                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        //                    menuSearch = menu.findItem(R.id.search).apply {
        //                        isVisible = true
        //                        menuSearchView = actionView as SearchView
        //                    }
        //
        //                    connectSearch()
        //                    if (!isSearchViewIconified) menuSearch.expandActionView()
        //                }
        //
        //                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        //                    when (menuItem.itemId) {
        //                        R.id.search -> {
        //                            return true
        //                        }
        //                        android.R.id.home -> {
        //                            menuSearch.collapseActionView()
        //                            return true
        //                        }
        //                    }
        //                    return false
        //                }
        //            },
        //            viewLifecycleOwner
        //        )
    }

    /**
     * Will display margin labels next to page numbers in an EPUB publication with a `page-list`
     * navigation document.
     *
     * See http://kb.daisy.org/publishing/docs/navigation/pagelist.html
     */
    private suspend fun DecorableNavigator.applyPageNumberDecorations() {
        val decorations = publication.pageList
            .mapIndexedNotNull { index, link ->
                val label = link.title ?: return@mapIndexedNotNull null
                val locator = publication.locatorFromLink(link) ?: return@mapIndexedNotNull null

                Decoration(
                    id = "page-$index",
                    locator = locator,
                    style = DecorationStylePageNumber(label = label)
                )
            }

        applyDecorations(decorations, "pageNumbers")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    //    private fun connectSearch() {
    //        menuSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
    //
    //            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
    //                if (isSearchViewIconified) { // It is not a state restoration.
    //                    showSearchFragment()
    //                }
    //
    //                isSearchViewIconified = false
    //                return true
    //            }
    //
    //            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
    //                isSearchViewIconified = true
    //                childFragmentManager.popBackStack()
    //                menuSearchView.clearFocus()
    //
    //                return true
    //            }
    //        })

    //        menuSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    //
    //            override fun onQueryTextSubmit(query: String): Boolean {
    //                model.search(query)
    //                menuSearchView.clearFocus()
    //
    //                return false
    //            }
    //
    //            override fun onQueryTextChange(s: String): Boolean {
    //                return false
    //            }
    //        })

    //        menuSearchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).setOnClickListener {
    //            menuSearchView.requestFocus()
    //            model.cancelSearch()
    //            menuSearchView.setQuery("", false)
    //
    //            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
    //                this.view,
    //                0
    //            )
    //        }
    //    }

    //    private fun showSearchFragment() {
    //        childFragmentManager.commit {
    //            childFragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG)?.let { remove(it) }
    //            add(
    //                R.id.fragment_reader_container,
    //                SearchFragment::class.java,
    //                Bundle(),
    //                SEARCH_FRAGMENT_TAG
    //            )
    //            hide(navigator)
    //            addToBackStack(SEARCH_FRAGMENT_TAG)
    //        }
    //    }


    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navigator.currentLocator
                    .collect {
                        onLocatorChanged.invoke(it.toNitroLocator())
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navigator.settings.collect {
                    onPreferencesChanged?.invoke(it.toNitroEpubPreferences())
                }
            }
        }

        (navigator as? DecorableNavigator)
            ?.addDecorationListener("user-annotations", listener)
    }

    val customSelectionActionModeCallback: ActionMode.Callback by lazy { SelectionActionModeCallback() }

    private inner class SelectionActionModeCallback : BaseActionModeCallback() {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            viewLifecycleOwner.lifecycleScope.launch {
                (navigator as? SelectableNavigator)?.currentSelection()?.let { selection ->
                    onSelection.invoke(
                        NitroSelection(
                            selection.locator.toNitroLocator(),
                            selection.rect?.let { rect ->
                                NitroRect(
                                    rect.left.pxToDp().toDouble(),
                                    rect.top.pxToDp().toDouble(),
                                    rect.right.pxToDp().toDouble(),
                                    rect.bottom.pxToDp().toDouble()
                                )
                            }
                        )
                    )
                }
            }
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            Log.i("EpubReaderFragment", "onActionItemClicked $mode $item")
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            onSelection.invoke(null)
        }
    }

    companion object {
        private const val NAVIGATOR_FRAGMENT_TAG = "navigator"
    }
}

// Examples of HTML templates for custom Decoration Styles.

/**
 * This Decorator Style will display a tinted "pen" icon in the page margin to show that a highlight
 * has an associated note.
 *
 * Note that the icon is served from the app assets folder.
 */
private fun annotationMarkTemplate(@ColorInt defaultTint: Int = Color.YELLOW): HtmlDecorationTemplate {
    val className = "testapp-annotation-mark"
    val iconUrl = checkNotNull(EpubNavigatorFragment.assetUrl("annotation-icon.svg"))
    return HtmlDecorationTemplate(
        layout = HtmlDecorationTemplate.Layout.BOUNDS,
        width = HtmlDecorationTemplate.Width.PAGE,
        element = { decoration ->
            val style = decoration.style as? DecorationStyleAnnotationMark
            val tint = style?.tint ?: defaultTint
            // Using `data-activable=1` prevents the whole decoration container from being
            // clickable. Only the icon will respond to activation events.
            """
            <div><div data-activable="1" class="$className" style="background-color: ${tint.toCss()} !important"/></div>"
            """
        },
        stylesheet = """
            .$className {
                float: left;
                margin-left: 8px;
                width: 30px;
                height: 30px;
                border-radius: 50%;
                background: url('$iconUrl') no-repeat center;
                background-size: auto 50%;
                opacity: 0.8;
            }
            """
    )
}

/**
 * This Decoration Style is used to display the page number labels in the margins, when a book
 * provides a `page-list`. The label is stored in the [DecorationStylePageNumber] itself.
 *
 * See http://kb.daisy.org/publishing/docs/navigation/pagelist.html
 */
private fun pageNumberTemplate(): HtmlDecorationTemplate {
    val className = "testapp-page-number"
    return HtmlDecorationTemplate(
        layout = HtmlDecorationTemplate.Layout.BOUNDS,
        width = HtmlDecorationTemplate.Width.PAGE,
        element = { decoration ->
            val style = decoration.style as? DecorationStylePageNumber

            // Using `var(--RS__backgroundColor)` is a trick to use the same background color as
            // the Readium theme. If we don't set it directly inline in the HTML, it might be
            // forced transparent by Readium CSS.
            """
            <div><span class="$className" style="background-color: var(--RS__backgroundColor) !important">${style?.label}</span></div>"
            """
        },
        stylesheet = """
            .$className {
                float: left;
                margin-left: 8px;
                padding: 0px 4px 0px 4px;
                border: 1px solid;
                border-radius: 20%;
                box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px, rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;
                opacity: 0.8;
            }
            """
    )
}