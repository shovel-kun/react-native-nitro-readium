///
/// JPoint.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include "Point.hpp"



namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * The C++ JNI bridge between the C++ struct "Point" and the the Kotlin data class "Point".
   */
  struct JPoint final: public jni::JavaClass<JPoint> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/Point;";

  public:
    /**
     * Convert this Java/Kotlin-based struct to the C++ struct Point by copying all values to C++.
     */
    [[maybe_unused]]
    [[nodiscard]]
    Point toCpp() const {
      static const auto clazz = javaClassStatic();
      static const auto fieldX = clazz->getField<double>("x");
      double x = this->getFieldValue(fieldX);
      static const auto fieldY = clazz->getField<double>("y");
      double y = this->getFieldValue(fieldY);
      return Point(
        x,
        y
      );
    }

  public:
    /**
     * Create a Java/Kotlin-based struct by copying all values from the given C++ struct to Java.
     */
    [[maybe_unused]]
    static jni::local_ref<JPoint::javaobject> fromCpp(const Point& value) {
      return newInstance(
        value.x,
        value.y
      );
    }
  };

} // namespace margelo::nitro::nitroreadium
