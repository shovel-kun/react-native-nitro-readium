///
/// JReadingProgression.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include "ReadingProgression.hpp"

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * The C++ JNI bridge between the C++ enum "ReadingProgression" and the the Kotlin enum "ReadingProgression".
   */
  struct JReadingProgression final: public jni::JavaClass<JReadingProgression> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/ReadingProgression;";

  public:
    /**
     * Convert this Java/Kotlin-based enum to the C++ enum ReadingProgression.
     */
    [[maybe_unused]]
    [[nodiscard]]
    ReadingProgression toCpp() const {
      static const auto clazz = javaClassStatic();
      static const auto fieldOrdinal = clazz->getField<int>("_ordinal");
      int ordinal = this->getFieldValue(fieldOrdinal);
      return static_cast<ReadingProgression>(ordinal);
    }

  public:
    /**
     * Create a Java/Kotlin-based enum with the given C++ enum's value.
     */
    [[maybe_unused]]
    static jni::alias_ref<JReadingProgression> fromCpp(ReadingProgression value) {
      static const auto clazz = javaClassStatic();
      static const auto fieldLTR = clazz->getStaticField<JReadingProgression>("LTR");
      static const auto fieldRTL = clazz->getStaticField<JReadingProgression>("RTL");
      
      switch (value) {
        case ReadingProgression::LTR:
          return clazz->getStaticFieldValue(fieldLTR);
        case ReadingProgression::RTL:
          return clazz->getStaticFieldValue(fieldRTL);
        default:
          std::string stringValue = std::to_string(static_cast<int>(value));
          throw std::invalid_argument("Invalid enum value (" + stringValue + "!");
      }
    }
  };

} // namespace margelo::nitro::nitroreadium
