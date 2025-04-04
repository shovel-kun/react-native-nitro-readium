///
/// JHybridPublicationSpec.cpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#include "JHybridPublicationSpec.hpp"

// Forward declaration of `Locator` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Locator; }
// Forward declaration of `Locations` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct Locations; }
// Forward declaration of `TextObject` to properly resolve imports.
namespace margelo::nitro::nitroreadium { struct TextObject; }

#include <string>
#include <NitroModules/Promise.hpp>
#include <NitroModules/JPromise.hpp>
#include <optional>
#include "Locator.hpp"
#include "JLocator.hpp"
#include "Locations.hpp"
#include "JLocations.hpp"
#include <vector>
#include "TextObject.hpp"
#include "JTextObject.hpp"

namespace margelo::nitro::nitroreadium {

  jni::local_ref<JHybridPublicationSpec::jhybriddata> JHybridPublicationSpec::initHybrid(jni::alias_ref<jhybridobject> jThis) {
    return makeCxxInstance(jThis);
  }

  void JHybridPublicationSpec::registerNatives() {
    registerHybrid({
      makeNativeMethod("initHybrid", JHybridPublicationSpec::initHybrid),
    });
  }

  size_t JHybridPublicationSpec::getExternalMemorySize() noexcept {
    static const auto method = javaClassStatic()->getMethod<jlong()>("getMemorySize");
    return method(_javaPart);
  }

  // Properties
  std::string JHybridPublicationSpec::getManifest() {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<jni::JString>()>("getManifest");
    auto __result = method(_javaPart);
    return __result->toStdString();
  }
  void JHybridPublicationSpec::setManifest(const std::string& manifest) {
    static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<jni::JString> /* manifest */)>("setManifest");
    method(_javaPart, jni::make_jstring(manifest));
  }
  std::string JHybridPublicationSpec::getMetadata() {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<jni::JString>()>("getMetadata");
    auto __result = method(_javaPart);
    return __result->toStdString();
  }
  void JHybridPublicationSpec::setMetadata(const std::string& metadata) {
    static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<jni::JString> /* metadata */)>("setMetadata");
    method(_javaPart, jni::make_jstring(metadata));
  }
  std::string JHybridPublicationSpec::getTableOfContents() {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<jni::JString>()>("getTableOfContents");
    auto __result = method(_javaPart);
    return __result->toStdString();
  }
  void JHybridPublicationSpec::setTableOfContents(const std::string& tableOfContents) {
    static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<jni::JString> /* tableOfContents */)>("setTableOfContents");
    method(_javaPart, jni::make_jstring(tableOfContents));
  }
  std::string JHybridPublicationSpec::getImages() {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<jni::JString>()>("getImages");
    auto __result = method(_javaPart);
    return __result->toStdString();
  }
  void JHybridPublicationSpec::setImages(const std::string& images) {
    static const auto method = javaClassStatic()->getMethod<void(jni::alias_ref<jni::JString> /* images */)>("setImages");
    method(_javaPart, jni::make_jstring(images));
  }

  // Methods
  std::shared_ptr<Promise<std::string>> JHybridPublicationSpec::cover() {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<JPromise::javaobject>()>("cover");
    auto __result = method(_javaPart);
    return [&]() {
      auto __promise = Promise<std::string>::create();
      __result->cthis()->addOnResolvedListener([=](const jni::alias_ref<jni::JObject>& __boxedResult) {
        auto __result = jni::static_ref_cast<jni::JString>(__boxedResult);
        __promise->resolve(__result->toStdString());
      });
      __result->cthis()->addOnRejectedListener([=](const jni::alias_ref<jni::JThrowable>& __throwable) {
        jni::JniException __jniError(__throwable);
        __promise->reject(std::make_exception_ptr(__jniError));
      });
      return __promise;
    }();
  }
  std::optional<Locator> JHybridPublicationSpec::locatorFromLink(const std::string& link) {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<JLocator>(jni::alias_ref<jni::JString> /* link */)>("locatorFromLink");
    auto __result = method(_javaPart, jni::make_jstring(link));
    return __result != nullptr ? std::make_optional(__result->toCpp()) : std::nullopt;
  }
  std::shared_ptr<Promise<std::optional<Locator>>> JHybridPublicationSpec::locate(const Locator& locator) {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<JPromise::javaobject>(jni::alias_ref<JLocator> /* locator */)>("locate");
    auto __result = method(_javaPart, JLocator::fromCpp(locator));
    return [&]() {
      auto __promise = Promise<std::optional<Locator>>::create();
      __result->cthis()->addOnResolvedListener([=](const jni::alias_ref<jni::JObject>& __boxedResult) {
        auto __result = jni::static_ref_cast<JLocator>(__boxedResult);
        __promise->resolve(__result != nullptr ? std::make_optional(__result->toCpp()) : std::nullopt);
      });
      __result->cthis()->addOnRejectedListener([=](const jni::alias_ref<jni::JThrowable>& __throwable) {
        jni::JniException __jniError(__throwable);
        __promise->reject(std::make_exception_ptr(__jniError));
      });
      return __promise;
    }();
  }
  std::shared_ptr<Promise<std::optional<Locator>>> JHybridPublicationSpec::locateProgression(double progression) {
    static const auto method = javaClassStatic()->getMethod<jni::local_ref<JPromise::javaobject>(double /* progression */)>("locateProgression");
    auto __result = method(_javaPart, progression);
    return [&]() {
      auto __promise = Promise<std::optional<Locator>>::create();
      __result->cthis()->addOnResolvedListener([=](const jni::alias_ref<jni::JObject>& __boxedResult) {
        auto __result = jni::static_ref_cast<JLocator>(__boxedResult);
        __promise->resolve(__result != nullptr ? std::make_optional(__result->toCpp()) : std::nullopt);
      });
      __result->cthis()->addOnRejectedListener([=](const jni::alias_ref<jni::JThrowable>& __throwable) {
        jni::JniException __jniError(__throwable);
        __promise->reject(std::make_exception_ptr(__jniError));
      });
      return __promise;
    }();
  }

} // namespace margelo::nitro::nitroreadium
