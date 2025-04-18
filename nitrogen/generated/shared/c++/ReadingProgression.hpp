///
/// ReadingProgression.hpp
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
   * An enum which can be represented as a JavaScript union (ReadingProgression).
   */
  enum class ReadingProgression {
    LTR      SWIFT_NAME(ltr) = 0,
    RTL      SWIFT_NAME(rtl) = 1,
  } CLOSED_ENUM;

} // namespace margelo::nitro::nitroreadium

namespace margelo::nitro {

  using namespace margelo::nitro::nitroreadium;

  // C++ ReadingProgression <> JS ReadingProgression (union)
  template <>
  struct JSIConverter<ReadingProgression> final {
    static inline ReadingProgression fromJSI(jsi::Runtime& runtime, const jsi::Value& arg) {
      std::string unionValue = JSIConverter<std::string>::fromJSI(runtime, arg);
      switch (hashString(unionValue.c_str(), unionValue.size())) {
        case hashString("ltr"): return ReadingProgression::LTR;
        case hashString("rtl"): return ReadingProgression::RTL;
        default: [[unlikely]]
          throw std::invalid_argument("Cannot convert \"" + unionValue + "\" to enum ReadingProgression - invalid value!");
      }
    }
    static inline jsi::Value toJSI(jsi::Runtime& runtime, ReadingProgression arg) {
      switch (arg) {
        case ReadingProgression::LTR: return JSIConverter<std::string>::toJSI(runtime, "ltr");
        case ReadingProgression::RTL: return JSIConverter<std::string>::toJSI(runtime, "rtl");
        default: [[unlikely]]
          throw std::invalid_argument("Cannot convert ReadingProgression to JS - invalid value: "
                                    + std::to_string(static_cast<int>(arg)) + "!");
      }
    }
    static inline bool canConvert(jsi::Runtime& runtime, const jsi::Value& value) {
      if (!value.isString()) {
        return false;
      }
      std::string unionValue = JSIConverter<std::string>::fromJSI(runtime, value);
      switch (hashString(unionValue.c_str(), unionValue.size())) {
        case hashString("ltr"):
        case hashString("rtl"):
          return true;
        default:
          return false;
      }
    }
  };

} // namespace margelo::nitro
