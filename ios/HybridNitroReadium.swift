import Foundation
import UIKit
import NitroModules

class HybridNitroReadium : HybridNitroReadiumSpec {
    
    // UIView
    var view: UIView = UIView()

    // Props
    var nitroSource: NitroFileSource = NitroFileSource("hi", nil)
    
    var locator: Locator?
    
    var preferences: EpubPreferences?
    
    var decorations: [Decoration]?
    
    var injectedJavascriptOnResourcesLoad: String?
    
    var injectedJavascriptOnPageLoad: String?
    
    var onLocatorChanged: ((Locator) -> Void)?
    
    var onSelection: ((Selection?) -> Void)?
    
    var onDecorationActivated: ((DecorationActivatedEvent) -> Void)?
    
    var onTap: ((TapEvent) -> Void)?
    
    var onDrag: ((DragEvent) -> Void)?
    
    var onPageChanged: ((Double, Double, Locator) -> Void)?
    
    var onPageLoaded: (() -> Void)?
    
    var onPreferencesChanged: ((EpubPreferences) -> Void)?
    
    var onMessage: ((String) -> Void)?
    
    func evaluateJavascript(script: String) throws -> Promise<String?> {
        return Promise.async { "hi" }
    }
    
    func injectJavascript(script: String) throws {
        return
    }
    
    func go(locator: Locator) throws {
        return
    }
    
    func clearSelection() throws {
        return
    }
    
    func getSettings() throws -> EpubPreferences {
        let dummyEpubPreferences = EpubPreferences(
            backgroundColor: "#FFFFFF",
            columnCount: .auto,
            fontFamily: "Helvetica",
            fontSize: 16.0,
            fontWeight: 400.0,
            hyphens: false,
            imageFilter: nil,
            language: "en",
            letterSpacing: 1.0,
            ligatures: true,
            lineHeight: 1.5,
            pageMargins: 1.0,
            paragraphIndent: 1.0,
            paragraphSpacing: 1.0,
            publisherStyles: true,
            readingProgression: .ltr,
            scroll: false,
            spread: .auto,
            textAlign: .justify,
            textColor: "#000000",
            textNormalization: true,
            theme: .light,
            typeScale: 1.0,
            verticalText: false,
            wordSpacing: 1.0
        )
        return dummyEpubPreferences
    }
}
