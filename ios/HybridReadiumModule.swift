import NitroModules

class HybridReadiumModule : HybridReadiumModuleSpec {
    func add(a: Double, b: Double) throws -> Double {
        return 1
    }
    
    func openPublication(absoluteUrl: String) throws -> NitroModules.Promise<(any HybridPublicationSpec)?> {
        Promise.async {
            nil
        }
    }
}
