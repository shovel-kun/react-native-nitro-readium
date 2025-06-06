///
/// NitroFileSource.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import NitroModules

/**
 * Represents an instance of `NitroFileSource`, backed by a C++ struct.
 */
public typealias NitroFileSource = margelo.nitro.nitroreadium.NitroFileSource

public extension NitroFileSource {
  private typealias bridge = margelo.nitro.nitroreadium.bridge.swift

  /**
   * Create a new instance of `NitroFileSource`.
   */
  init(uri: String, initialLocation: String?) {
    self.init(std.string(uri), { () -> bridge.std__optional_std__string_ in
      if let __unwrappedValue = initialLocation {
        return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
      } else {
        return .init()
      }
    }())
  }

  var uri: String {
    @inline(__always)
    get {
      return String(self.__uri)
    }
    @inline(__always)
    set {
      self.__uri = std.string(newValue)
    }
  }
  
  var initialLocation: String? {
    @inline(__always)
    get {
      return { () -> String? in
        if let __unwrapped = self.__initialLocation.value {
          return String(__unwrapped)
        } else {
          return nil
        }
      }()
    }
    @inline(__always)
    set {
      self.__initialLocation = { () -> bridge.std__optional_std__string_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_std__string_(std.string(__unwrappedValue))
        } else {
          return .init()
        }
      }()
    }
  }
}
