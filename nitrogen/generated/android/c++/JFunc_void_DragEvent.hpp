///
/// JFunc_void_DragEvent.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include <functional>

#include <functional>
#include "DragEvent.hpp"
#include "JDragEvent.hpp"
#include "DragEventType.hpp"
#include "JDragEventType.hpp"
#include "Point.hpp"
#include "JPoint.hpp"

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * Represents the Java/Kotlin callback `(event: DragEvent) -> Unit`.
   * This can be passed around between C++ and Java/Kotlin.
   */
  struct JFunc_void_DragEvent: public jni::JavaClass<JFunc_void_DragEvent> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/Func_void_DragEvent;";

  public:
    /**
     * Invokes the function this `JFunc_void_DragEvent` instance holds through JNI.
     */
    void invoke(const DragEvent& event) const {
      static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<JDragEvent> /* event */)>("invoke");
      method(self(), JDragEvent::fromCpp(event));
    }
  };

  /**
   * An implementation of Func_void_DragEvent that is backed by a C++ implementation (using `std::function<...>`)
   */
  struct JFunc_void_DragEvent_cxx final: public jni::HybridClass<JFunc_void_DragEvent_cxx, JFunc_void_DragEvent> {
  public:
    static jni::local_ref<JFunc_void_DragEvent::javaobject> fromCpp(const std::function<void(const DragEvent& /* event */)>& func) {
      return JFunc_void_DragEvent_cxx::newObjectCxxArgs(func);
    }

  public:
    /**
     * Invokes the C++ `std::function<...>` this `JFunc_void_DragEvent_cxx` instance holds.
     */
    void invoke_cxx(jni::alias_ref<JDragEvent> event) {
      _func(event->toCpp());
    }

  public:
    [[nodiscard]]
    inline const std::function<void(const DragEvent& /* event */)>& getFunction() const {
      return _func;
    }

  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/Func_void_DragEvent_cxx;";
    static void registerNatives() {
      registerHybrid({makeNativeMethod("invoke_cxx", JFunc_void_DragEvent_cxx::invoke_cxx)});
    }

  private:
    explicit JFunc_void_DragEvent_cxx(const std::function<void(const DragEvent& /* event */)>& func): _func(func) { }

  private:
    friend HybridBase;
    std::function<void(const DragEvent& /* event */)> _func;
  };

} // namespace margelo::nitro::nitroreadium
