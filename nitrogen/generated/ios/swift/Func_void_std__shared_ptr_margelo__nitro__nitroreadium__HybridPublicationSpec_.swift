///
/// Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_.swift
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

import NitroModules

/**
 * Wraps a Swift `(_ value: (any HybridPublicationSpec)) -> Void` as a class.
 * This class can be used from C++, e.g. to wrap the Swift closure as a `std::function`.
 */
public final class Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_ {
  public typealias bridge = margelo.nitro.nitroreadium.bridge.swift

  private let closure: (_ value: (any HybridPublicationSpec)) -> Void

  public init(_ closure: @escaping (_ value: (any HybridPublicationSpec)) -> Void) {
    self.closure = closure
  }

  @inline(__always)
  public func call(value: bridge.std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_) -> Void {
    self.closure({ () -> HybridPublicationSpec in
      let __unsafePointer = bridge.get_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_(value)
      let __instance = HybridPublicationSpec_cxx.fromUnsafe(__unsafePointer)
      return __instance.getHybridPublicationSpec()
    }())
  }

  /**
   * Casts this instance to a retained unsafe raw pointer.
   * This acquires one additional strong reference on the object!
   */
  @inline(__always)
  public func toUnsafe() -> UnsafeMutableRawPointer {
    return Unmanaged.passRetained(self).toOpaque()
  }

  /**
   * Casts an unsafe pointer to a `Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_`.
   * The pointer has to be a retained opaque `Unmanaged<Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_>`.
   * This removes one strong reference from the object!
   */
  @inline(__always)
  public static func fromUnsafe(_ pointer: UnsafeMutableRawPointer) -> Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_ {
    return Unmanaged<Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_>.fromOpaque(pointer).takeRetainedValue()
  }
}
