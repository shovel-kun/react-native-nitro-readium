import NitroModules

class HybridPublication : HybridPublicationSpec {
    var manifest: String = ""
    
    var metadata: String = ""

    var tableOfContents: String = ""

    var images: String = ""
    
    func cover() throws -> NitroModules.Promise<String> {
        Promise.async {
            "hi"
        }
    }
    
    func locatorFromLink(link: String) throws -> Locator? {
        return nil
    }
    
    func locate(locator: Locator) throws -> NitroModules.Promise<Locator?> {
        Promise.async {
            nil
        }
    }
    
    func locateProgression(progression: Double) throws -> NitroModules.Promise<Locator?> {
        Promise.async {
            nil
        }
    }
}
