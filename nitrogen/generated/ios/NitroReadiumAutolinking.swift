///
/// NitroReadiumAutolinking.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

public final class NitroReadiumAutolinking {
  public typealias bridge = margelo.nitro.nitroreadium.bridge.swift

  /**
   * Creates an instance of a Swift class that implements `HybridNitroReadiumSpec`,
   * and wraps it in a Swift class that can directly interop with C++ (`HybridNitroReadiumSpec_cxx`)
   *
   * This is generated by Nitrogen and will initialize the class specified
   * in the `"autolinking"` property of `nitro.json` (in this case, `HybridNitroReadium`).
   */
  public static func createNitroReadium() -> bridge.std__shared_ptr_margelo__nitro__nitroreadium__HybridNitroReadiumSpec_ {
    let hybridObject = HybridNitroReadium()
    return { () -> bridge.std__shared_ptr_margelo__nitro__nitroreadium__HybridNitroReadiumSpec_ in
      let __cxxWrapped = hybridObject.getCxxWrapper()
      return __cxxWrapped.getCxxPart()
    }()
  }
  
  /**
   * Creates an instance of a Swift class that implements `HybridReadiumModuleSpec`,
   * and wraps it in a Swift class that can directly interop with C++ (`HybridReadiumModuleSpec_cxx`)
   *
   * This is generated by Nitrogen and will initialize the class specified
   * in the `"autolinking"` property of `nitro.json` (in this case, `HybridReadiumModule`).
   */
  public static func createReadiumModule() -> bridge.std__shared_ptr_margelo__nitro__nitroreadium__HybridReadiumModuleSpec_ {
    let hybridObject = HybridReadiumModule()
    return { () -> bridge.std__shared_ptr_margelo__nitro__nitroreadium__HybridReadiumModuleSpec_ in
      let __cxxWrapped = hybridObject.getCxxWrapper()
      return __cxxWrapped.getCxxPart()
    }()
  }
}
