///
/// JHybridNitroReadiumStateUpdater.cpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#include "JHybridNitroReadiumStateUpdater.hpp"
#include "views/HybridNitroReadiumComponent.hpp"
#include <NitroModules/NitroDefines.hpp>
#include <NitroModules/JNISharedPtr.hpp>

namespace margelo::nitro::nitroreadium::views {

using namespace facebook;
using ConcreteStateData = react::ConcreteState<HybridNitroReadiumState>;

void JHybridNitroReadiumStateUpdater::updateViewProps(jni::alias_ref<jni::JClass> /* class */,
                                           jni::alias_ref<JHybridNitroReadiumSpec::javaobject> javaView,
                                           jni::alias_ref<react::StateWrapperImpl::javaobject> stateWrapper) {
  JHybridNitroReadiumSpec* view = javaView->cthis();
  std::shared_ptr<const react::State> state = stateWrapper->cthis()->getState();
  auto concreteState = std::dynamic_pointer_cast<const ConcreteStateData>(state);
  const HybridNitroReadiumState& data = concreteState->getData();
  const std::optional<HybridNitroReadiumProps>& maybeProps = data.getProps();
  if (!maybeProps.has_value()) {
    // Props aren't set yet!
    throw std::runtime_error("HybridNitroReadiumState's data doesn't contain any props!");
  }
  const HybridNitroReadiumProps& props = maybeProps.value();
  if (props.absolutePath.isDirty) {
    view->setAbsolutePath(props.absolutePath.value);
    // TODO: Set isDirty = false
  }
  if (props.locator.isDirty) {
    view->setLocator(props.locator.value);
    // TODO: Set isDirty = false
  }
  if (props.preferences.isDirty) {
    view->setPreferences(props.preferences.value);
    // TODO: Set isDirty = false
  }
  if (props.decorations.isDirty) {
    view->setDecorations(props.decorations.value);
    // TODO: Set isDirty = false
  }
  if (props.injectedJavascript.isDirty) {
    view->setInjectedJavascript(props.injectedJavascript.value);
    // TODO: Set isDirty = false
  }
  if (props.injectedJavascriptTarget.isDirty) {
    view->setInjectedJavascriptTarget(props.injectedJavascriptTarget.value);
    // TODO: Set isDirty = false
  }
  if (props.onLocatorChanged.isDirty) {
    view->setOnLocatorChanged(props.onLocatorChanged.value);
    // TODO: Set isDirty = false
  }
  if (props.onSelection.isDirty) {
    view->setOnSelection(props.onSelection.value);
    // TODO: Set isDirty = false
  }
  if (props.onDecorationActivated.isDirty) {
    view->setOnDecorationActivated(props.onDecorationActivated.value);
    // TODO: Set isDirty = false
  }
  if (props.onTap.isDirty) {
    view->setOnTap(props.onTap.value);
    // TODO: Set isDirty = false
  }
  if (props.onDrag.isDirty) {
    view->setOnDrag(props.onDrag.value);
    // TODO: Set isDirty = false
  }
  if (props.onPageChanged.isDirty) {
    view->setOnPageChanged(props.onPageChanged.value);
    // TODO: Set isDirty = false
  }
  if (props.onPageLoaded.isDirty) {
    view->setOnPageLoaded(props.onPageLoaded.value);
    // TODO: Set isDirty = false
  }
  if (props.onMessage.isDirty) {
    view->setOnMessage(props.onMessage.value);
    // TODO: Set isDirty = false
  }

  // Update hybridRef if it changed
  if (props.hybridRef.isDirty) {
    // hybridRef changed - call it with new this
    const auto& maybeFunc = props.hybridRef.value;
    if (maybeFunc.has_value()) {
      auto shared = JNISharedPtr::make_shared_from_jni<JHybridNitroReadiumSpec>(jni::make_global(javaView));
      maybeFunc.value()(shared);
    }
    // TODO: Set isDirty = false
  }
}

} // namespace margelo::nitro::nitroreadium::views
