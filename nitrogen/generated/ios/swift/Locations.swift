///
/// Locations.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import NitroModules

/**
 * Represents an instance of `Locations`, backed by a C++ struct.
 */
public typealias Locations = margelo.nitro.nitroreadium.Locations

public extension Locations {
  private typealias bridge = margelo.nitro.nitroreadium.bridge.swift

  /**
   * Create a new instance of `Locations`.
   */
  init(fragments: [String]?, progression: Double?, position: Double?, totalProgression: Double?) {
    self.init({ () -> bridge.std__optional_std__vector_std__string__ in
      if let __unwrappedValue = fragments {
        return bridge.create_std__optional_std__vector_std__string__({ () -> bridge.std__vector_std__string_ in
          var __vector = bridge.create_std__vector_std__string_(__unwrappedValue.count)
          for __item in __unwrappedValue {
            __vector.push_back(std.string(__item))
          }
          return __vector
        }())
      } else {
        return .init()
      }
    }(), { () -> bridge.std__optional_double_ in
      if let __unwrappedValue = progression {
        return bridge.create_std__optional_double_(__unwrappedValue)
      } else {
        return .init()
      }
    }(), { () -> bridge.std__optional_double_ in
      if let __unwrappedValue = position {
        return bridge.create_std__optional_double_(__unwrappedValue)
      } else {
        return .init()
      }
    }(), { () -> bridge.std__optional_double_ in
      if let __unwrappedValue = totalProgression {
        return bridge.create_std__optional_double_(__unwrappedValue)
      } else {
        return .init()
      }
    }())
  }

  var fragments: [String]? {
    @inline(__always)
    get {
      return { () -> [String]? in
        if let __unwrapped = self.__fragments.value {
          return __unwrapped.map({ __item in String(__item) })
        } else {
          return nil
        }
      }()
    }
    @inline(__always)
    set {
      self.__fragments = { () -> bridge.std__optional_std__vector_std__string__ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_std__vector_std__string__({ () -> bridge.std__vector_std__string_ in
            var __vector = bridge.create_std__vector_std__string_(__unwrappedValue.count)
            for __item in __unwrappedValue {
              __vector.push_back(std.string(__item))
            }
            return __vector
          }())
        } else {
          return .init()
        }
      }()
    }
  }
  
  var progression: Double? {
    @inline(__always)
    get {
      return self.__progression.value
    }
    @inline(__always)
    set {
      self.__progression = { () -> bridge.std__optional_double_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_double_(__unwrappedValue)
        } else {
          return .init()
        }
      }()
    }
  }
  
  var position: Double? {
    @inline(__always)
    get {
      return self.__position.value
    }
    @inline(__always)
    set {
      self.__position = { () -> bridge.std__optional_double_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_double_(__unwrappedValue)
        } else {
          return .init()
        }
      }()
    }
  }
  
  var totalProgression: Double? {
    @inline(__always)
    get {
      return self.__totalProgression.value
    }
    @inline(__always)
    set {
      self.__totalProgression = { () -> bridge.std__optional_double_ in
        if let __unwrappedValue = newValue {
          return bridge.create_std__optional_double_(__unwrappedValue)
        } else {
          return .init()
        }
      }()
    }
  }
}
