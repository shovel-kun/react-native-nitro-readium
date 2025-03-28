///
/// HybridPublicationSpec.hpp
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



#include <string>
#include <NitroModules/Promise.hpp>

namespace margelo::nitro::nitroreadium {

  using namespace margelo::nitro;

  /**
   * An abstract base class for `Publication`
   * Inherit this class to create instances of `HybridPublicationSpec` in C++.
   * You must explicitly call `HybridObject`'s constructor yourself, because it is virtual.
   * @example
   * ```cpp
   * class HybridPublication: public HybridPublicationSpec {
   * public:
   *   HybridPublication(...): HybridObject(TAG) { ... }
   *   // ...
   * };
   * ```
   */
  class HybridPublicationSpec: public virtual HybridObject {
    public:
      // Constructor
      explicit HybridPublicationSpec(): HybridObject(TAG) { }

      // Destructor
      ~HybridPublicationSpec() override = default;

    public:
      // Properties
      virtual std::string getManifest() = 0;
      virtual void setManifest(const std::string& manifest) = 0;
      virtual std::string getTableOfContents() = 0;
      virtual void setTableOfContents(const std::string& tableOfContents) = 0;
      virtual std::string getImages() = 0;
      virtual void setImages(const std::string& images) = 0;

    public:
      // Methods
      virtual std::shared_ptr<Promise<std::string>> cover() = 0;

    protected:
      // Hybrid Setup
      void loadHybridMethods() override;

    protected:
      // Tag for logging
      static constexpr auto TAG = "Publication";
  };

} // namespace margelo::nitro::nitroreadium
