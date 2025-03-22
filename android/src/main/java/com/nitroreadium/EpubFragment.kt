package com.nitroreadium

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commitNow
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.readium.r2.navigator.Decoration
import org.readium.r2.navigator.OverflowableNavigator
import org.readium.r2.navigator.VisualNavigator
import org.readium.r2.navigator.epub.EpubDefaults
import org.readium.r2.navigator.epub.EpubNavigatorFactory
import org.readium.r2.navigator.epub.EpubNavigatorFragment
import org.readium.r2.navigator.epub.EpubPreferences
import org.readium.r2.navigator.epub.css.Color
import org.readium.r2.navigator.epub.css.FontStyle
import org.readium.r2.navigator.epub.css.FontWeight
import org.readium.r2.navigator.epub.css.RsProperties
import org.readium.r2.navigator.html.HtmlDecorationTemplates
import org.readium.r2.navigator.input.InputListener
import org.readium.r2.navigator.input.TapEvent
import org.readium.r2.navigator.preferences.FontFamily
import org.readium.r2.navigator.util.BaseActionModeCallback
import org.readium.r2.navigator.util.DirectionalNavigationAdapter
import org.readium.r2.shared.ExperimentalReadiumApi
import org.readium.r2.shared.InternalReadiumApi
import org.readium.r2.shared.extensions.toMap
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import org.readium.r2.shared.publication.html.cssSelector
import kotlin.math.ceil

//class SelectionActionModeCallback(private val epubView: EpubView) : BaseActionModeCallback() {
//    @OptIn(InternalReadiumApi::class)
//    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//        val activity: FragmentActivity? = epubView.appContext.currentActivity as FragmentActivity?
//        activity?.lifecycleScope?.launch {
//            val selection = epubView.navigator?.currentSelection() ?: return@launch
//            selection.rect?.let {
//                val x = ceil(it.centerX() / epubView.resources.displayMetrics.density).toInt()
//                val y = ceil(it.top / epubView.resources.displayMetrics.density).toInt() - 16
//                epubView.onSelection(
//                    mapOf(
//                        "locator" to selection.locator.toJSON().toMap(),
//                        "x" to x,
//                        "y" to y
//                    )
//                )
//            }
//        }
//
//        return true
//    }
//}
//
//@SuppressLint("ViewConstructor")
//@OptIn(ExperimentalReadiumApi::class)
//class EpubFragment(
//    private var locator: Locator,
//    private val publication: Publication,
//    private val customFonts: List<CustomFont>,
//    private val listener: EpubView
//) : Fragment(R.layout.fragment_reader) {
//    var navigator: EpubNavigatorFragment? = null
//    private lateinit var navigatorFragment: Fragment
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        childFragmentManager.fragmentFactory = EpubNavigatorFactory(
//            publication,
//            EpubNavigatorFactory.Configuration(
//                defaults = EpubDefaults(
//                    publisherStyles = true
//                ),
//            ),
//        ).createFragmentFactory(
//            locator,
//            listener = listener,
//            configuration = EpubNavigatorFragment.Configuration {
////                servedAssets = listOf(
////                    "fonts/OpenDyslexic.otf",
////                    "fonts/Literata_500Medium.ttf"
////                )
////
////                addFontFamilyDeclaration(FontFamily("OpenDyslexic")) {
////                    addFontFace {
////                        addSource("fonts/OpenDyslexic-Regular.ttf")
////                        setFontStyle(FontStyle.NORMAL)
////                        setFontWeight(FontWeight.NORMAL)
////                    }
////
////                    addFontFace {
////                        addSource("fonts/OpenDyslexic-Bold.ttf")
////                        setFontStyle(FontStyle.NORMAL)
////                        setFontWeight(FontWeight.BOLD)
////                    }
////
////                    addFontFace {
////                        addSource("fonts/OpenDyslexic-Bold-Italic.ttf")
////                        setFontStyle(FontStyle.ITALIC)
////                        setFontWeight(FontWeight.BOLD)
////                    }
////
////                    addFontFace {
////                        addSource("fonts/OpenDyslexic-Italic.ttf")
////                        setFontStyle(FontStyle.ITALIC)
////                        setFontWeight(FontWeight.NORMAL)
////                    }
////                }
////
////                addFontFamilyDeclaration(FontFamily("Literata")) {
////                    addFontFace {
////                        addSource("fonts/Literata_500Medium.ttf")
////                        setFontStyle(FontStyle.NORMAL)
////                        setFontWeight(FontWeight.NORMAL)
////                    }
////                }
//
//                customFonts.forEach {
//                    addFontFamilyDeclaration(FontFamily(it.name)) {
//                        addFontFace {
//                            addSource(it.uri)
//                            setFontStyle(FontStyle.NORMAL)
//                            setFontWeight(FontWeight.NORMAL)
//                        }
//                    }
//                }
//
//                selectionActionModeCallback = SelectionActionModeCallback(listener)
//
//                registerJavascriptInterface("storyteller") {
////                    listener.setupUserScript()
//                }
//            },
//            initialPreferences = listener.preferences,
//        )
//
//        Log.i("EpubFragment", "EpubFragment created")
//
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Log.i("EpubFragment", "onViewCreated")
//
//        val navigatorFragmentTag = getString(R.string.epub_navigator_tag)
//
////        navigatorFragment = navigator as Fragment
////
////        (navigator as OverflowableNavigator).apply {
////            // This will automatically turn pages when tapping the screen edges or arrow keys.
////            addInputListener(DirectionalNavigationAdapter(this))
////        }
////
////        (navigator as VisualNavigator).apply {
////            addInputListener(object : InputListener {
////                override fun onTap(event: TapEvent): Boolean {
////                    Log.i("EpubFragment", "onTap")
////                    requireActivity().toggleSystemUi()
////                    return true
////                }
////            })
////        }
//
//        if (savedInstanceState == null) {
//            childFragmentManager.commitNow {
//                setReorderingAllowed(true)
//                add(
//                    R.id.fragment_reader_container,
//                    EpubNavigatorFragment::class.java,
//                    Bundle(),
//                    navigatorFragmentTag
//                )
//            }
//        }
//        navigator =
//            childFragmentManager.findFragmentByTag(navigatorFragmentTag) as EpubNavigatorFragment
//    }
//}
