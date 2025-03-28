///
/// NitroReadiumOnLoad.cpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#ifndef BUILDING_NITROREADIUM_WITH_GENERATED_CMAKE_PROJECT
#error NitroReadiumOnLoad.cpp is not being built with the autogenerated CMakeLists.txt project. Is a different CMakeLists.txt building this?
#endif

#include "NitroReadiumOnLoad.hpp"

#include <jni.h>
#include <fbjni/fbjni.h>
#include <NitroModules/HybridObjectRegistry.hpp>

#include "JHybridPublicationSpec.hpp"
#include "JHybridReadiumModuleSpec.hpp"
#include "JHybridNitroReadiumSpec.hpp"
#include "JFunc_void_Locator.hpp"
#include "JFunc_void_std__optional_Selection_.hpp"
#include "JFunc_void_DecorationActivatedEvent.hpp"
#include "JFunc_void_TapEvent.hpp"
#include "JFunc_void_DragEvent.hpp"
#include "JFunc_void_double_double_Locator.hpp"
#include "JFunc_void.hpp"
#include "JFunc_void_std__string.hpp"
#include "views/JHybridNitroReadiumStateUpdater.hpp"
#include <NitroModules/JNISharedPtr.hpp>
#include <NitroModules/DefaultConstructableObject.hpp>

namespace margelo::nitro::nitroreadium {

int initialize(JavaVM* vm) {
  using namespace margelo::nitro;
  using namespace margelo::nitro::nitroreadium;
  using namespace facebook;

  return facebook::jni::initialize(vm, [] {
    // Register native JNI methods
    margelo::nitro::nitroreadium::JHybridPublicationSpec::registerNatives();
    margelo::nitro::nitroreadium::JHybridReadiumModuleSpec::registerNatives();
    margelo::nitro::nitroreadium::JHybridNitroReadiumSpec::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_Locator_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_std__optional_Selection__cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_DecorationActivatedEvent_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_TapEvent_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_DragEvent_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_double_double_Locator_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_cxx::registerNatives();
    margelo::nitro::nitroreadium::JFunc_void_std__string_cxx::registerNatives();
    margelo::nitro::nitroreadium::views::JHybridNitroReadiumStateUpdater::registerNatives();

    // Register Nitro Hybrid Objects
    HybridObjectRegistry::registerHybridObjectConstructor(
      "NitroReadium",
      []() -> std::shared_ptr<HybridObject> {
        static DefaultConstructableObject<JHybridNitroReadiumSpec::javaobject> object("com/nitroreadium/HybridNitroReadium");
        auto instance = object.create();
        auto globalRef = jni::make_global(instance);
        return JNISharedPtr::make_shared_from_jni<JHybridNitroReadiumSpec>(globalRef);
      }
    );
    HybridObjectRegistry::registerHybridObjectConstructor(
      "ReadiumModule",
      []() -> std::shared_ptr<HybridObject> {
        static DefaultConstructableObject<JHybridReadiumModuleSpec::javaobject> object("com/nitroreadium/HybridReadiumModule");
        auto instance = object.create();
        auto globalRef = jni::make_global(instance);
        return JNISharedPtr::make_shared_from_jni<JHybridReadiumModuleSpec>(globalRef);
      }
    );
    HybridObjectRegistry::registerHybridObjectConstructor(
      "Publication",
      []() -> std::shared_ptr<HybridObject> {
        static DefaultConstructableObject<JHybridPublicationSpec::javaobject> object("com/nitroreadium/HybridPublication");
        auto instance = object.create();
        auto globalRef = jni::make_global(instance);
        return JNISharedPtr::make_shared_from_jni<JHybridPublicationSpec>(globalRef);
      }
    );
  });
}

} // namespace margelo::nitro::nitroreadium
