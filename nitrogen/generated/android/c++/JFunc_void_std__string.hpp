///
/// JFunc_void_std__string.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include <functional>

#include <functional>
#include <string>

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * Represents the Java/Kotlin callback `(message: String) -> Unit`.
   * This can be passed around between C++ and Java/Kotlin.
   */
  struct JFunc_void_std__string: public jni::JavaClass<JFunc_void_std__string> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/Func_void_std__string;";

  public:
    /**
     * Invokes the function this `JFunc_void_std__string` instance holds through JNI.
     */
    void invoke(const std::string& message) const {
      static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<jni::JString> /* message */)>("invoke");
      method(self(), jni::make_jstring(message));
    }
  };

  /**
   * An implementation of Func_void_std__string that is backed by a C++ implementation (using `std::function<...>`)
   */
  struct JFunc_void_std__string_cxx final: public jni::HybridClass<JFunc_void_std__string_cxx, JFunc_void_std__string> {
  public:
    static jni::local_ref<JFunc_void_std__string::javaobject> fromCpp(const std::function<void(const std::string& /* message */)>& func) {
      return JFunc_void_std__string_cxx::newObjectCxxArgs(func);
    }

  public:
    /**
     * Invokes the C++ `std::function<...>` this `JFunc_void_std__string_cxx` instance holds.
     */
    void invoke_cxx(jni::alias_ref<jni::JString> message) {
      _func(message->toStdString());
    }

  public:
    [[nodiscard]]
    inline const std::function<void(const std::string& /* message */)>& getFunction() const {
      return _func;
    }

  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/Func_void_std__string_cxx;";
    static void registerNatives() {
      registerHybrid({makeNativeMethod("invoke_cxx", JFunc_void_std__string_cxx::invoke_cxx)});
    }

  private:
    explicit JFunc_void_std__string_cxx(const std::function<void(const std::string& /* message */)>& func): _func(func) { }

  private:
    friend HybridBase;
    std::function<void(const std::string& /* message */)> _func;
  };

} // namespace margelo::nitro::nitroreadium
