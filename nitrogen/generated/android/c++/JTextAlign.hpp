///
/// JTextAlign.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include "TextAlign.hpp"

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * The C++ JNI bridge between the C++ enum "TextAlign" and the the Kotlin enum "TextAlign".
   */
  struct JTextAlign final: public jni::JavaClass<JTextAlign> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/TextAlign;";

  public:
    /**
     * Convert this Java/Kotlin-based enum to the C++ enum TextAlign.
     */
    [[maybe_unused]]
    [[nodiscard]]
    TextAlign toCpp() const {
      static const auto clazz = javaClassStatic();
      static const auto fieldOrdinal = clazz->getField<int>("_ordinal");
      int ordinal = this->getFieldValue(fieldOrdinal);
      return static_cast<TextAlign>(ordinal);
    }

  public:
    /**
     * Create a Java/Kotlin-based enum with the given C++ enum's value.
     */
    [[maybe_unused]]
    static jni::alias_ref<JTextAlign> fromCpp(TextAlign value) {
      static const auto clazz = javaClassStatic();
      static const auto fieldCENTER = clazz->getStaticField<JTextAlign>("CENTER");
      static const auto fieldJUSTIFY = clazz->getStaticField<JTextAlign>("JUSTIFY");
      static const auto fieldSTART = clazz->getStaticField<JTextAlign>("START");
      static const auto fieldEND = clazz->getStaticField<JTextAlign>("END");
      static const auto fieldLEFT = clazz->getStaticField<JTextAlign>("LEFT");
      static const auto fieldRIGHT = clazz->getStaticField<JTextAlign>("RIGHT");
      
      switch (value) {
        case TextAlign::CENTER:
          return clazz->getStaticFieldValue(fieldCENTER);
        case TextAlign::JUSTIFY:
          return clazz->getStaticFieldValue(fieldJUSTIFY);
        case TextAlign::START:
          return clazz->getStaticFieldValue(fieldSTART);
        case TextAlign::END:
          return clazz->getStaticFieldValue(fieldEND);
        case TextAlign::LEFT:
          return clazz->getStaticFieldValue(fieldLEFT);
        case TextAlign::RIGHT:
          return clazz->getStaticFieldValue(fieldRIGHT);
        default:
          std::string stringValue = std::to_string(static_cast<int>(value));
          throw std::invalid_argument("Invalid enum value (" + stringValue + "!");
      }
    }
  };

} // namespace margelo::nitro::nitroreadium
