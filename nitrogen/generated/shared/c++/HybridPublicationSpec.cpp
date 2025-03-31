///
/// HybridPublicationSpec.cpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#include "HybridPublicationSpec.hpp"

namespace margelo::nitro::nitroreadium {

  void HybridPublicationSpec::loadHybridMethods() {
    // load base methods/properties
    HybridObject::loadHybridMethods();
    // load custom methods/properties
    registerHybrids(this, [](Prototype& prototype) {
      prototype.registerHybridGetter("manifest", &HybridPublicationSpec::getManifest);
      prototype.registerHybridSetter("manifest", &HybridPublicationSpec::setManifest);
      prototype.registerHybridGetter("metadata", &HybridPublicationSpec::getMetadata);
      prototype.registerHybridSetter("metadata", &HybridPublicationSpec::setMetadata);
      prototype.registerHybridGetter("tableOfContents", &HybridPublicationSpec::getTableOfContents);
      prototype.registerHybridSetter("tableOfContents", &HybridPublicationSpec::setTableOfContents);
      prototype.registerHybridGetter("images", &HybridPublicationSpec::getImages);
      prototype.registerHybridSetter("images", &HybridPublicationSpec::setImages);
      prototype.registerHybridMethod("cover", &HybridPublicationSpec::cover);
    });
  }

} // namespace margelo::nitro::nitroreadium
