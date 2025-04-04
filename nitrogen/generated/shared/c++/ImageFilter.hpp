///
/// ImageFilter.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#if __has_include(<NitroModules/NitroHash.hpp>)
#include <NitroModules/NitroHash.hpp>
#else
#error NitroModules cannot be found! Are you sure you installed NitroModules properly?
#endif
#if __has_include(<NitroModules/JSIConverter.hpp>)
#include <NitroModules/JSIConverter.hpp>
#else
#error NitroModules cannot be found! Are you sure you installed NitroModules properly?
#endif
#if __has_include(<NitroModules/NitroDefines.hpp>)
#include <NitroModules/NitroDefines.hpp>
#else
#error NitroModules cannot be found! Are you sure you installed NitroModules properly?
#endif

namespace margelo::nitro::nitroreadium {

  /**
   * An enum which can be represented as a JavaScript union (ImageFilter).
   */
  enum class ImageFilter {
    DARKEN      SWIFT_NAME(darken) = 0,
    INVERT      SWIFT_NAME(invert) = 1,
  } CLOSED_ENUM;

} // namespace margelo::nitro::nitroreadium

namespace margelo::nitro {

  using namespace margelo::nitro::nitroreadium;

  // C++ ImageFilter <> JS ImageFilter (union)
  template <>
  struct JSIConverter<ImageFilter> final {
    static inline ImageFilter fromJSI(jsi::Runtime& runtime, const jsi::Value& arg) {
      std::string unionValue = JSIConverter<std::string>::fromJSI(runtime, arg);
      switch (hashString(unionValue.c_str(), unionValue.size())) {
        case hashString("darken"): return ImageFilter::DARKEN;
        case hashString("invert"): return ImageFilter::INVERT;
        default: [[unlikely]]
          throw std::invalid_argument("Cannot convert \"" + unionValue + "\" to enum ImageFilter - invalid value!");
      }
    }
    static inline jsi::Value toJSI(jsi::Runtime& runtime, ImageFilter arg) {
      switch (arg) {
        case ImageFilter::DARKEN: return JSIConverter<std::string>::toJSI(runtime, "darken");
        case ImageFilter::INVERT: return JSIConverter<std::string>::toJSI(runtime, "invert");
        default: [[unlikely]]
          throw std::invalid_argument("Cannot convert ImageFilter to JS - invalid value: "
                                    + std::to_string(static_cast<int>(arg)) + "!");
      }
    }
    static inline bool canConvert(jsi::Runtime& runtime, const jsi::Value& value) {
      if (!value.isString()) {
        return false;
      }
      std::string unionValue = JSIConverter<std::string>::fromJSI(runtime, value);
      switch (hashString(unionValue.c_str(), unionValue.size())) {
        case hashString("darken"):
        case hashString("invert"):
          return true;
        default:
          return false;
      }
    }
  };

} // namespace margelo::nitro
