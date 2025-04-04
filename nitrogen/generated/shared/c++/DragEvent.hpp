///
/// DragEvent.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

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

// Forward declaration of `DragEventType` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class DragEventType; }
// Forward declaration of `Point` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Point; }

#include "DragEventType.hpp"
#include "Point.hpp"

namespace margelo::nitro::nitroreadium {

  /**
   * A struct which can be represented as a JavaScript object (DragEvent).
   */
  struct DragEvent {
  public:
    DragEventType type     SWIFT_PRIVATE;
    Point start     SWIFT_PRIVATE;
    Point end     SWIFT_PRIVATE;

  public:
    DragEvent() = default;
    explicit DragEvent(DragEventType type, Point start, Point end): type(type), start(start), end(end) {}
  };

} // namespace margelo::nitro::nitroreadium

namespace margelo::nitro {

  using namespace margelo::nitro::nitroreadium;

  // C++ DragEvent <> JS DragEvent (object)
  template <>
  struct JSIConverter<DragEvent> final {
    static inline DragEvent fromJSI(jsi::Runtime& runtime, const jsi::Value& arg) {
      jsi::Object obj = arg.asObject(runtime);
      return DragEvent(
        JSIConverter<DragEventType>::fromJSI(runtime, obj.getProperty(runtime, "type")),
        JSIConverter<Point>::fromJSI(runtime, obj.getProperty(runtime, "start")),
        JSIConverter<Point>::fromJSI(runtime, obj.getProperty(runtime, "end"))
      );
    }
    static inline jsi::Value toJSI(jsi::Runtime& runtime, const DragEvent& arg) {
      jsi::Object obj(runtime);
      obj.setProperty(runtime, "type", JSIConverter<DragEventType>::toJSI(runtime, arg.type));
      obj.setProperty(runtime, "start", JSIConverter<Point>::toJSI(runtime, arg.start));
      obj.setProperty(runtime, "end", JSIConverter<Point>::toJSI(runtime, arg.end));
      return obj;
    }
    static inline bool canConvert(jsi::Runtime& runtime, const jsi::Value& value) {
      if (!value.isObject()) {
        return false;
      }
      jsi::Object obj = value.getObject(runtime);
      if (!JSIConverter<DragEventType>::canConvert(runtime, obj.getProperty(runtime, "type"))) return false;
      if (!JSIConverter<Point>::canConvert(runtime, obj.getProperty(runtime, "start"))) return false;
      if (!JSIConverter<Point>::canConvert(runtime, obj.getProperty(runtime, "end"))) return false;
      return true;
    }
  };

} // namespace margelo::nitro
