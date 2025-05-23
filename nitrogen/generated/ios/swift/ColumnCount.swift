///
/// ColumnCount.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

/**
 * Represents the JS union `ColumnCount`, backed by a C++ enum.
 */
public typealias ColumnCount = margelo.nitro.nitroreadium.ColumnCount

public extension ColumnCount {
  /**
   * Get a ColumnCount for the given String value, or
   * return `nil` if the given value was invalid/unknown.
   */
  init?(fromString string: String) {
    switch string {
      case "auto":
        self = .auto
      case "one":
        self = .one
      case "two":
        self = .two
      default:
        return nil
    }
  }

  /**
   * Get the String value this ColumnCount represents.
   */
  var stringValue: String {
    switch self {
      case .auto:
        return "auto"
      case .one:
        return "one"
      case .two:
        return "two"
    }
  }
}
