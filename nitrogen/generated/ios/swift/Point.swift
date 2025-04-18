///
/// Point.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import NitroModules

/**
 * Represents an instance of `Point`, backed by a C++ struct.
 */
public typealias Point = margelo.nitro.nitroreadium.Point

public extension Point {
  private typealias bridge = margelo.nitro.nitroreadium.bridge.swift

  /**
   * Create a new instance of `Point`.
   */
  init(x: Double, y: Double) {
    self.init(x, y)
  }

  var x: Double {
    @inline(__always)
    get {
      return self.__x
    }
    @inline(__always)
    set {
      self.__x = newValue
    }
  }
  
  var y: Double {
    @inline(__always)
    get {
      return self.__y
    }
    @inline(__always)
    set {
      self.__y = newValue
    }
  }
}
