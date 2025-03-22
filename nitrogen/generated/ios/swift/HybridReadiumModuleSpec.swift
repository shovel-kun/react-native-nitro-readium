///
/// HybridReadiumModuleSpec.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import Foundation
import NitroModules

/// See ``HybridReadiumModuleSpec``
public protocol HybridReadiumModuleSpec_protocol: HybridObject {
  // Properties
  

  // Methods
  func add(a: Double, b: Double) throws -> Double
  func getManifest(absoluteUrl: String) throws -> Promise<String>
}

/// See ``HybridReadiumModuleSpec``
public class HybridReadiumModuleSpec_base {
  private weak var cxxWrapper: HybridReadiumModuleSpec_cxx? = nil
  public func getCxxWrapper() -> HybridReadiumModuleSpec_cxx {
  #if DEBUG
    guard self is HybridReadiumModuleSpec else {
      fatalError("`self` is not a `HybridReadiumModuleSpec`! Did you accidentally inherit from `HybridReadiumModuleSpec_base` instead of `HybridReadiumModuleSpec`?")
    }
  #endif
    if let cxxWrapper = self.cxxWrapper {
      return cxxWrapper
    } else {
      let cxxWrapper = HybridReadiumModuleSpec_cxx(self as! HybridReadiumModuleSpec)
      self.cxxWrapper = cxxWrapper
      return cxxWrapper
    }
  }
}

/**
 * A Swift base-protocol representing the ReadiumModule HybridObject.
 * Implement this protocol to create Swift-based instances of ReadiumModule.
 * ```swift
 * class HybridReadiumModule : HybridReadiumModuleSpec {
 *   // ...
 * }
 * ```
 */
public typealias HybridReadiumModuleSpec = HybridReadiumModuleSpec_protocol & HybridReadiumModuleSpec_base
