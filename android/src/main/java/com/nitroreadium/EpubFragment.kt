package com.nitroreadium

// class SelectionActionModeCallback(private val epubView: EpubView) : BaseActionModeCallback() {
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
// }
//
// @SuppressLint("ViewConstructor")
// @OptIn(ExperimentalReadiumApi::class)
// class EpubFragment(
//    private var locator: Locator,
//    private val publication: Publication,
//    private val customFonts: List<CustomFont>,
//    private val listener: EpubView
// ) : Fragment(R.layout.fragment_reader) {
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
// }
