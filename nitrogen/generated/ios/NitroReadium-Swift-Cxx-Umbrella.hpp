///
/// NitroReadium-Swift-Cxx-Umbrella.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

// Forward declarations of C++ defined types
// Forward declaration of `ColumnCount` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class ColumnCount; }
// Forward declaration of `DecorationActivatedEvent` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct DecorationActivatedEvent; }
// Forward declaration of `DecorationStyle` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct DecorationStyle; }
// Forward declaration of `DecorationType` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class DecorationType; }
// Forward declaration of `Decoration` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Decoration; }
// Forward declaration of `DragEventType` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class DragEventType; }
// Forward declaration of `DragEvent` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct DragEvent; }
// Forward declaration of `EpubPreferences` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct EpubPreferences; }
// Forward declaration of `HybridNitroReadiumSpec` to properly resolve imports.
namespace margelo::nitro::nitroreadium { class HybridNitroReadiumSpec; }
// Forward declaration of `HybridPublicationSpec` to properly resolve imports.
namespace margelo::nitro::nitroreadium { class HybridPublicationSpec; }
// Forward declaration of `HybridReadiumModuleSpec` to properly resolve imports.
namespace margelo::nitro::nitroreadium { class HybridReadiumModuleSpec; }
// Forward declaration of `ImageFilter` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class ImageFilter; }
// Forward declaration of `Locations` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Locations; }
// Forward declaration of `Locator` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Locator; }
// Forward declaration of `NitroFileSource` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct NitroFileSource; }
// Forward declaration of `Point` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Point; }
// Forward declaration of `ReadingProgression` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class ReadingProgression; }
// Forward declaration of `Rect` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Rect; }
// Forward declaration of `Selection` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Selection; }
// Forward declaration of `Spread` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class Spread; }
// Forward declaration of `TapEvent` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct TapEvent; }
// Forward declaration of `TextAlign` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class TextAlign; }
// Forward declaration of `TextObject` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct TextObject; }
// Forward declaration of `Theme` to properly resolve imports.
namespace margelo::nitro::nitroreadium { enum class Theme; }

// Include C++ defined types
#include "ColumnCount.hpp"
#include "Decoration.hpp"
#include "DecorationActivatedEvent.hpp"
#include "DecorationStyle.hpp"
#include "DecorationType.hpp"
#include "DragEvent.hpp"
#include "DragEventType.hpp"
#include "EpubPreferences.hpp"
#include "HybridNitroReadiumSpec.hpp"
#include "HybridPublicationSpec.hpp"
#include "HybridReadiumModuleSpec.hpp"
#include "ImageFilter.hpp"
#include "Locations.hpp"
#include "Locator.hpp"
#include "NitroFileSource.hpp"
#include "Point.hpp"
#include "ReadingProgression.hpp"
#include "Rect.hpp"
#include "Selection.hpp"
#include "Spread.hpp"
#include "TapEvent.hpp"
#include "TextAlign.hpp"
#include "TextObject.hpp"
#include "Theme.hpp"
#include <NitroModules/Promise.hpp>
#include <NitroModules/Result.hpp>
#include <exception>
#include <functional>
#include <memory>
#include <optional>
#include <string>
#include <vector>

// C++ helpers for Swift
#include "NitroReadium-Swift-Cxx-Bridge.hpp"

// Common C++ types used in Swift
#include <NitroModules/ArrayBufferHolder.hpp>
#include <NitroModules/AnyMapHolder.hpp>
#include <NitroModules/RuntimeError.hpp>
#include <NitroModules/DateToChronoDate.hpp>

// Forward declarations of Swift defined types
// Forward declaration of `HybridNitroReadiumSpec_cxx` to properly resolve imports.
namespace NitroReadium { class HybridNitroReadiumSpec_cxx; }
// Forward declaration of `HybridPublicationSpec_cxx` to properly resolve imports.
namespace NitroReadium { class HybridPublicationSpec_cxx; }
// Forward declaration of `HybridReadiumModuleSpec_cxx` to properly resolve imports.
namespace NitroReadium { class HybridReadiumModuleSpec_cxx; }

// Include Swift defined types
#if __has_include("NitroReadium-Swift.h")
// This header is generated by Xcode/Swift on every app build.
// If it cannot be found, make sure the Swift module's name (= podspec name) is actually "NitroReadium".
#include "NitroReadium-Swift.h"
// Same as above, but used when building with frameworks (`use_frameworks`)
#elif __has_include(<NitroReadium/NitroReadium-Swift.h>)
#include <NitroReadium/NitroReadium-Swift.h>
#else
#error NitroReadium's autogenerated Swift header cannot be found! Make sure the Swift module's name (= podspec name) is actually "NitroReadium", and try building the app first.
#endif
