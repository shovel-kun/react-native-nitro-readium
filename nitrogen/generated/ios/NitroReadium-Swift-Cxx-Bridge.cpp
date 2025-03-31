///
/// NitroReadium-Swift-Cxx-Bridge.cpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#include "NitroReadium-Swift-Cxx-Bridge.hpp"

// Include C++ implementation defined types
#include "HybridNitroReadiumSpecSwift.hpp"
#include "HybridPublicationSpecSwift.hpp"
#include "HybridReadiumModuleSpecSwift.hpp"
#include "NitroReadium-Swift-Cxx-Umbrella.hpp"

namespace margelo::nitro::nitroreadium::bridge::swift {

  // pragma MARK: std::function<void(const std::string& /* result */)>
  Func_void_std__string create_Func_void_std__string(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__string::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::string& result) mutable -> void {
      swiftClosure.call(result);
    };
  }
  
  // pragma MARK: std::function<void(const std::exception_ptr& /* error */)>
  Func_void_std__exception_ptr create_Func_void_std__exception_ptr(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__exception_ptr::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::exception_ptr& error) mutable -> void {
      swiftClosure.call(error);
    };
  }
  
  // pragma MARK: std::function<void(const std::optional<Locator>& /* result */)>
  Func_void_std__optional_Locator_ create_Func_void_std__optional_Locator_(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__optional_Locator_::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::optional<Locator>& result) mutable -> void {
      swiftClosure.call(result);
    };
  }
  
  // pragma MARK: std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpec>
  std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpec> create_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_(void* _Nonnull swiftUnsafePointer) {
    NitroReadium::HybridPublicationSpec_cxx swiftPart = NitroReadium::HybridPublicationSpec_cxx::fromUnsafe(swiftUnsafePointer);
    return std::make_shared<margelo::nitro::nitroreadium::HybridPublicationSpecSwift>(swiftPart);
  }
  void* _Nonnull get_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_(std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_ cppType) {
    std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpecSwift> swiftWrapper = std::dynamic_pointer_cast<margelo::nitro::nitroreadium::HybridPublicationSpecSwift>(cppType);
  #ifdef NITRO_DEBUG
    if (swiftWrapper == nullptr) [[unlikely]] {
      throw std::runtime_error("Class \"HybridPublicationSpec\" is not implemented in Swift!");
    }
  #endif
    NitroReadium::HybridPublicationSpec_cxx& swiftPart = swiftWrapper->getSwiftPart();
    return swiftPart.toUnsafe();
  }
  
  // pragma MARK: std::function<void(const std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpec>& /* result */)>
  Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_ create_Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__shared_ptr_margelo__nitro__nitroreadium__HybridPublicationSpec_::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::shared_ptr<margelo::nitro::nitroreadium::HybridPublicationSpec>& result) mutable -> void {
      swiftClosure.call(result);
    };
  }
  
  // pragma MARK: std::shared_ptr<margelo::nitro::nitroreadium::HybridReadiumModuleSpec>
  std::shared_ptr<margelo::nitro::nitroreadium::HybridReadiumModuleSpec> create_std__shared_ptr_margelo__nitro__nitroreadium__HybridReadiumModuleSpec_(void* _Nonnull swiftUnsafePointer) {
    NitroReadium::HybridReadiumModuleSpec_cxx swiftPart = NitroReadium::HybridReadiumModuleSpec_cxx::fromUnsafe(swiftUnsafePointer);
    return std::make_shared<margelo::nitro::nitroreadium::HybridReadiumModuleSpecSwift>(swiftPart);
  }
  void* _Nonnull get_std__shared_ptr_margelo__nitro__nitroreadium__HybridReadiumModuleSpec_(std__shared_ptr_margelo__nitro__nitroreadium__HybridReadiumModuleSpec_ cppType) {
    std::shared_ptr<margelo::nitro::nitroreadium::HybridReadiumModuleSpecSwift> swiftWrapper = std::dynamic_pointer_cast<margelo::nitro::nitroreadium::HybridReadiumModuleSpecSwift>(cppType);
  #ifdef NITRO_DEBUG
    if (swiftWrapper == nullptr) [[unlikely]] {
      throw std::runtime_error("Class \"HybridReadiumModuleSpec\" is not implemented in Swift!");
    }
  #endif
    NitroReadium::HybridReadiumModuleSpec_cxx& swiftPart = swiftWrapper->getSwiftPart();
    return swiftPart.toUnsafe();
  }
  
  // pragma MARK: std::function<void(const Locator& /* locator */)>
  Func_void_Locator create_Func_void_Locator(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_Locator::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const Locator& locator) mutable -> void {
      swiftClosure.call(locator);
    };
  }
  
  // pragma MARK: std::function<void(const std::optional<Selection>& /* selection */)>
  Func_void_std__optional_Selection_ create_Func_void_std__optional_Selection_(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__optional_Selection_::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::optional<Selection>& selection) mutable -> void {
      swiftClosure.call(selection);
    };
  }
  
  // pragma MARK: std::function<void(const DecorationActivatedEvent& /* event */)>
  Func_void_DecorationActivatedEvent create_Func_void_DecorationActivatedEvent(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_DecorationActivatedEvent::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const DecorationActivatedEvent& event) mutable -> void {
      swiftClosure.call(event);
    };
  }
  
  // pragma MARK: std::function<void(const TapEvent& /* event */)>
  Func_void_TapEvent create_Func_void_TapEvent(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_TapEvent::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const TapEvent& event) mutable -> void {
      swiftClosure.call(event);
    };
  }
  
  // pragma MARK: std::function<void(const DragEvent& /* event */)>
  Func_void_DragEvent create_Func_void_DragEvent(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_DragEvent::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const DragEvent& event) mutable -> void {
      swiftClosure.call(event);
    };
  }
  
  // pragma MARK: std::function<void(double /* page */, double /* totalPages */, const Locator& /* locator */)>
  Func_void_double_double_Locator create_Func_void_double_double_Locator(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_double_double_Locator::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](double page, double totalPages, const Locator& locator) mutable -> void {
      swiftClosure.call(page, totalPages, locator);
    };
  }
  
  // pragma MARK: std::function<void()>
  Func_void create_Func_void(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)]() mutable -> void {
      swiftClosure.call();
    };
  }
  
  // pragma MARK: std::function<void(const std::optional<std::string>& /* result */)>
  Func_void_std__optional_std__string_ create_Func_void_std__optional_std__string_(void* _Nonnull swiftClosureWrapper) {
    auto swiftClosure = NitroReadium::Func_void_std__optional_std__string_::fromUnsafe(swiftClosureWrapper);
    return [swiftClosure = std::move(swiftClosure)](const std::optional<std::string>& result) mutable -> void {
      swiftClosure.call(result);
    };
  }
  
  // pragma MARK: std::shared_ptr<margelo::nitro::nitroreadium::HybridNitroReadiumSpec>
  std::shared_ptr<margelo::nitro::nitroreadium::HybridNitroReadiumSpec> create_std__shared_ptr_margelo__nitro__nitroreadium__HybridNitroReadiumSpec_(void* _Nonnull swiftUnsafePointer) {
    NitroReadium::HybridNitroReadiumSpec_cxx swiftPart = NitroReadium::HybridNitroReadiumSpec_cxx::fromUnsafe(swiftUnsafePointer);
    return std::make_shared<margelo::nitro::nitroreadium::HybridNitroReadiumSpecSwift>(swiftPart);
  }
  void* _Nonnull get_std__shared_ptr_margelo__nitro__nitroreadium__HybridNitroReadiumSpec_(std__shared_ptr_margelo__nitro__nitroreadium__HybridNitroReadiumSpec_ cppType) {
    std::shared_ptr<margelo::nitro::nitroreadium::HybridNitroReadiumSpecSwift> swiftWrapper = std::dynamic_pointer_cast<margelo::nitro::nitroreadium::HybridNitroReadiumSpecSwift>(cppType);
  #ifdef NITRO_DEBUG
    if (swiftWrapper == nullptr) [[unlikely]] {
      throw std::runtime_error("Class \"HybridNitroReadiumSpec\" is not implemented in Swift!");
    }
  #endif
    NitroReadium::HybridNitroReadiumSpec_cxx& swiftPart = swiftWrapper->getSwiftPart();
    return swiftPart.toUnsafe();
  }

} // namespace margelo::nitro::nitroreadium::bridge::swift
