///
/// HybridReadiumModuleSpec.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#if __has_include(<NitroModules/HybridObject.hpp>)
#include <NitroModules/HybridObject.hpp>
#else
#error NitroModules cannot be found! Are you sure you installed NitroModules properly?
#endif

// Forward declaration of `HybridPublicationSpec` to properly resolve imports.
namespace margelo::nitro::nitroreadium { class HybridPublicationSpec; }

#include <NitroModules/Promise.hpp>
#include <memory>
#include "HybridPublicationSpec.hpp"
#include <string>

namespace margelo::nitro::nitroreadium {

  using namespace margelo::nitro;

  /**
   * An abstract base class for `ReadiumModule`
   * Inherit this class to create instances of `HybridReadiumModuleSpec` in C++.
   * You must explicitly call `HybridObject`'s constructor yourself, because it is virtual.
   * @example
   * ```cpp
   * class HybridReadiumModule: public HybridReadiumModuleSpec {
   * public:
   *   HybridReadiumModule(...): HybridObject(TAG) { ... }
   *   // ...
   * };
   * ```
   */
  class HybridReadiumModuleSpec: public virtual HybridObject {
    public:
      // Constructor
      explicit HybridReadiumModuleSpec(): HybridObject(TAG) { }

      // Destructor
      ~HybridReadiumModuleSpec() override = default;

    public:
      // Properties
      

    public:
      // Methods
      virtual double add(double a, double b) = 0;
      virtual std::shared_ptr<Promise<std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpec>>> openPublication(const std::string& absoluteUrl) = 0;

    protected:
      // Hybrid Setup
      void loadHybridMethods() override;

    protected:
      // Tag for logging
      static constexpr auto TAG = "ReadiumModule";
  };

} // namespace margelo::nitro::nitroreadium
