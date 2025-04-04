///
/// JDecorationStyle.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include "DecorationStyle.hpp"

#include "DecorationType.hpp"
#include "JDecorationType.hpp"
#include <string>

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * The C++ JNI bridge between the C++ struct "DecorationStyle" and the the Kotlin data class "DecorationStyle".
   */
  struct JDecorationStyle final: public jni::JavaClass<JDecorationStyle> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/DecorationStyle;";

  public:
    /**
     * Convert this Java/Kotlin-based struct to the C++ struct DecorationStyle by copying all values to C++.
     */
    [[maybe_unused]]
    [[nodiscard]]
    DecorationStyle toCpp() const {
      static const auto clazz = javaClassStatic();
      static const auto fieldType = clazz->getField<JDecorationType>("type");
      jni::local_ref<JDecorationType> type = this->getFieldValue(fieldType);
      static const auto fieldTint = clazz->getField<jni::JString>("tint");
      jni::local_ref<jni::JString> tint = this->getFieldValue(fieldTint);
      return DecorationStyle(
        type->toCpp(),
        tint->toStdString()
      );
    }

  public:
    /**
     * Create a Java/Kotlin-based struct by copying all values from the given C++ struct to Java.
     */
    [[maybe_unused]]
    static jni::local_ref<JDecorationStyle::javaobject> fromCpp(const DecorationStyle& value) {
      return newInstance(
        JDecorationType::fromCpp(value.type),
        jni::make_jstring(value.tint)
      );
    }
  };

} // namespace margelo::nitro::nitroreadium
