///
/// HybridNitroReadiumSpec.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <NitroModules/JHybridObject.hpp>
#include <fbjni/fbjni.h>
#include "HybridNitroReadiumSpec.hpp"




namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  class JHybridNitroReadiumSpec: public jni::HybridClass<JHybridNitroReadiumSpec, JHybridObject>,
                                 public virtual HybridNitroReadiumSpec {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/HybridNitroReadiumSpec;";
    static jni::local_ref<jhybriddata> initHybrid(jni::alias_ref<jhybridobject> jThis);
    static void registerNatives();

  protected:
    // C++ constructor (called from Java via `initHybrid()`)
    explicit JHybridNitroReadiumSpec(jni::alias_ref<jhybridobject> jThis) :
      HybridObject(HybridNitroReadiumSpec::TAG),
      _javaPart(jni::make_global(jThis)) {}

  public:
    ~JHybridNitroReadiumSpec() override {
      // Hermes GC can destroy JS objects on a non-JNI Thread.
      jni::ThreadScope::WithClassLoader([&] { _javaPart.reset(); });
    }

  public:
    size_t getExternalMemorySize() noexcept override;

  public:
    inline const jni::global_ref<JHybridNitroReadiumSpec::javaobject>& getJavaPart() const noexcept {
      return _javaPart;
    }

  public:
    // Properties
    NitroFileSource getNitroSource() override;
    void setNitroSource(const NitroFileSource& nitroSource) override;
    std::optional<Locator> getLocator() override;
    void setLocator(const std::optional<Locator>& locator) override;
    std::optional<EpubPreferences> getPreferences() override;
    void setPreferences(const std::optional<EpubPreferences>& preferences) override;
    std::optional<std::vector<Decoration>> getDecorations() override;
    void setDecorations(const std::optional<std::vector<Decoration>>& decorations) override;
    std::optional<std::string> getInjectedJavascriptOnResourcesLoad() override;
    void setInjectedJavascriptOnResourcesLoad(const std::optional<std::string>& injectedJavascriptOnResourcesLoad) override;
    std::optional<std::string> getInjectedJavascriptOnPageLoad() override;
    void setInjectedJavascriptOnPageLoad(const std::optional<std::string>& injectedJavascriptOnPageLoad) override;
    std::optional<std::function<void(const Locator& /* locator */)>> getOnLocatorChanged() override;
    void setOnLocatorChanged(const std::optional<std::function<void(const Locator& /* locator */)>>& onLocatorChanged) override;
    std::optional<std::function<void(const std::optional<Selection>& /* selection */)>> getOnSelection() override;
    void setOnSelection(const std::optional<std::function<void(const std::optional<Selection>& /* selection */)>>& onSelection) override;
    std::optional<std::function<void(const DecorationActivatedEvent& /* event */)>> getOnDecorationActivated() override;
    void setOnDecorationActivated(const std::optional<std::function<void(const DecorationActivatedEvent& /* event */)>>& onDecorationActivated) override;
    std::optional<std::function<void(const TapEvent& /* event */)>> getOnTap() override;
    void setOnTap(const std::optional<std::function<void(const TapEvent& /* event */)>>& onTap) override;
    std::optional<std::function<void(const DragEvent& /* event */)>> getOnDrag() override;
    void setOnDrag(const std::optional<std::function<void(const DragEvent& /* event */)>>& onDrag) override;
    std::optional<std::function<void(double /* page */, double /* totalPages */, const Locator& /* locator */)>> getOnPageChanged() override;
    void setOnPageChanged(const std::optional<std::function<void(double /* page */, double /* totalPages */, const Locator& /* locator */)>>& onPageChanged) override;
    std::optional<std::function<void()>> getOnPageLoaded() override;
    void setOnPageLoaded(const std::optional<std::function<void()>>& onPageLoaded) override;
    std::optional<std::function<void(const std::string& /* message */)>> getOnMessage() override;
    void setOnMessage(const std::optional<std::function<void(const std::string& /* message */)>>& onMessage) override;

  public:
    // Methods
    std::shared_ptr<Promise<std::optional<std::string>>> evaluateJavascript(const std::string& script) override;
    void injectJavascript(const std::string& script) override;
    void go(const Locator& locator) override;
    void clearSelection() override;

  private:
    friend HybridBase;
    using HybridBase::HybridBase;
    jni::global_ref<JHybridNitroReadiumSpec::javaobject> _javaPart;
  };

} // namespace margelo::nitro::nitroreadium
