///
/// JEpubPreferences.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#include <fbjni/fbjni.h>
#include "EpubPreferences.hpp"

#include "ColumnCount.hpp"
#include "ImageFilter.hpp"
#include "JColumnCount.hpp"
#include "JImageFilter.hpp"
#include "JReadingProgression.hpp"
#include "JSpread.hpp"
#include "JTextAlign.hpp"
#include "JTheme.hpp"
#include "ReadingProgression.hpp"
#include "Spread.hpp"
#include "TextAlign.hpp"
#include "Theme.hpp"
#include <optional>
#include <string>

namespace margelo::nitro::nitroreadium {

  using namespace facebook;

  /**
   * The C++ JNI bridge between the C++ struct "EpubPreferences" and the the Kotlin data class "EpubPreferences".
   */
  struct JEpubPreferences final: public jni::JavaClass<JEpubPreferences> {
  public:
    static auto constexpr kJavaDescriptor = "Lcom/margelo/nitro/nitroreadium/EpubPreferences;";

  public:
    /**
     * Convert this Java/Kotlin-based struct to the C++ struct EpubPreferences by copying all values to C++.
     */
    [[maybe_unused]]
    [[nodiscard]]
    EpubPreferences toCpp() const {
      static const auto clazz = javaClassStatic();
      static const auto fieldBackgroundColor = clazz->getField<jni::JString>("backgroundColor");
      jni::local_ref<jni::JString> backgroundColor = this->getFieldValue(fieldBackgroundColor);
      static const auto fieldColumnCount = clazz->getField<JColumnCount>("columnCount");
      jni::local_ref<JColumnCount> columnCount = this->getFieldValue(fieldColumnCount);
      static const auto fieldFontFamily = clazz->getField<jni::JString>("fontFamily");
      jni::local_ref<jni::JString> fontFamily = this->getFieldValue(fieldFontFamily);
      static const auto fieldFontSize = clazz->getField<jni::JDouble>("fontSize");
      jni::local_ref<jni::JDouble> fontSize = this->getFieldValue(fieldFontSize);
      static const auto fieldFontWeight = clazz->getField<jni::JDouble>("fontWeight");
      jni::local_ref<jni::JDouble> fontWeight = this->getFieldValue(fieldFontWeight);
      static const auto fieldHyphens = clazz->getField<jni::JBoolean>("hyphens");
      jni::local_ref<jni::JBoolean> hyphens = this->getFieldValue(fieldHyphens);
      static const auto fieldImageFilter = clazz->getField<JImageFilter>("imageFilter");
      jni::local_ref<JImageFilter> imageFilter = this->getFieldValue(fieldImageFilter);
      static const auto fieldLanguage = clazz->getField<jni::JString>("language");
      jni::local_ref<jni::JString> language = this->getFieldValue(fieldLanguage);
      static const auto fieldLetterSpacing = clazz->getField<jni::JDouble>("letterSpacing");
      jni::local_ref<jni::JDouble> letterSpacing = this->getFieldValue(fieldLetterSpacing);
      static const auto fieldLigatures = clazz->getField<jni::JBoolean>("ligatures");
      jni::local_ref<jni::JBoolean> ligatures = this->getFieldValue(fieldLigatures);
      static const auto fieldLineHeight = clazz->getField<jni::JDouble>("lineHeight");
      jni::local_ref<jni::JDouble> lineHeight = this->getFieldValue(fieldLineHeight);
      static const auto fieldPageMargins = clazz->getField<jni::JDouble>("pageMargins");
      jni::local_ref<jni::JDouble> pageMargins = this->getFieldValue(fieldPageMargins);
      static const auto fieldParagraphIndent = clazz->getField<jni::JDouble>("paragraphIndent");
      jni::local_ref<jni::JDouble> paragraphIndent = this->getFieldValue(fieldParagraphIndent);
      static const auto fieldParagraphSpacing = clazz->getField<jni::JDouble>("paragraphSpacing");
      jni::local_ref<jni::JDouble> paragraphSpacing = this->getFieldValue(fieldParagraphSpacing);
      static const auto fieldPublisherStyles = clazz->getField<jni::JBoolean>("publisherStyles");
      jni::local_ref<jni::JBoolean> publisherStyles = this->getFieldValue(fieldPublisherStyles);
      static const auto fieldReadingProgression = clazz->getField<JReadingProgression>("readingProgression");
      jni::local_ref<JReadingProgression> readingProgression = this->getFieldValue(fieldReadingProgression);
      static const auto fieldScroll = clazz->getField<jni::JBoolean>("scroll");
      jni::local_ref<jni::JBoolean> scroll = this->getFieldValue(fieldScroll);
      static const auto fieldSpread = clazz->getField<JSpread>("spread");
      jni::local_ref<JSpread> spread = this->getFieldValue(fieldSpread);
      static const auto fieldTextAlign = clazz->getField<JTextAlign>("textAlign");
      jni::local_ref<JTextAlign> textAlign = this->getFieldValue(fieldTextAlign);
      static const auto fieldTextColor = clazz->getField<jni::JString>("textColor");
      jni::local_ref<jni::JString> textColor = this->getFieldValue(fieldTextColor);
      static const auto fieldTextNormalization = clazz->getField<jni::JBoolean>("textNormalization");
      jni::local_ref<jni::JBoolean> textNormalization = this->getFieldValue(fieldTextNormalization);
      static const auto fieldTheme = clazz->getField<JTheme>("theme");
      jni::local_ref<JTheme> theme = this->getFieldValue(fieldTheme);
      static const auto fieldTypeScale = clazz->getField<jni::JDouble>("typeScale");
      jni::local_ref<jni::JDouble> typeScale = this->getFieldValue(fieldTypeScale);
      static const auto fieldVerticalText = clazz->getField<jni::JBoolean>("verticalText");
      jni::local_ref<jni::JBoolean> verticalText = this->getFieldValue(fieldVerticalText);
      static const auto fieldWordSpacing = clazz->getField<jni::JDouble>("wordSpacing");
      jni::local_ref<jni::JDouble> wordSpacing = this->getFieldValue(fieldWordSpacing);
      return EpubPreferences(
        backgroundColor != nullptr ? std::make_optional(backgroundColor->toStdString()) : std::nullopt,
        columnCount != nullptr ? std::make_optional(columnCount->toCpp()) : std::nullopt,
        fontFamily != nullptr ? std::make_optional(fontFamily->toStdString()) : std::nullopt,
        fontSize != nullptr ? std::make_optional(fontSize->value()) : std::nullopt,
        fontWeight != nullptr ? std::make_optional(fontWeight->value()) : std::nullopt,
        hyphens != nullptr ? std::make_optional(static_cast<bool>(hyphens->value())) : std::nullopt,
        imageFilter != nullptr ? std::make_optional(imageFilter->toCpp()) : std::nullopt,
        language != nullptr ? std::make_optional(language->toStdString()) : std::nullopt,
        letterSpacing != nullptr ? std::make_optional(letterSpacing->value()) : std::nullopt,
        ligatures != nullptr ? std::make_optional(static_cast<bool>(ligatures->value())) : std::nullopt,
        lineHeight != nullptr ? std::make_optional(lineHeight->value()) : std::nullopt,
        pageMargins != nullptr ? std::make_optional(pageMargins->value()) : std::nullopt,
        paragraphIndent != nullptr ? std::make_optional(paragraphIndent->value()) : std::nullopt,
        paragraphSpacing != nullptr ? std::make_optional(paragraphSpacing->value()) : std::nullopt,
        publisherStyles != nullptr ? std::make_optional(static_cast<bool>(publisherStyles->value())) : std::nullopt,
        readingProgression != nullptr ? std::make_optional(readingProgression->toCpp()) : std::nullopt,
        scroll != nullptr ? std::make_optional(static_cast<bool>(scroll->value())) : std::nullopt,
        spread != nullptr ? std::make_optional(spread->toCpp()) : std::nullopt,
        textAlign != nullptr ? std::make_optional(textAlign->toCpp()) : std::nullopt,
        textColor != nullptr ? std::make_optional(textColor->toStdString()) : std::nullopt,
        textNormalization != nullptr ? std::make_optional(static_cast<bool>(textNormalization->value())) : std::nullopt,
        theme != nullptr ? std::make_optional(theme->toCpp()) : std::nullopt,
        typeScale != nullptr ? std::make_optional(typeScale->value()) : std::nullopt,
        verticalText != nullptr ? std::make_optional(static_cast<bool>(verticalText->value())) : std::nullopt,
        wordSpacing != nullptr ? std::make_optional(wordSpacing->value()) : std::nullopt
      );
    }

  public:
    /**
     * Create a Java/Kotlin-based struct by copying all values from the given C++ struct to Java.
     */
    [[maybe_unused]]
    static jni::local_ref<JEpubPreferences::javaobject> fromCpp(const EpubPreferences& value) {
      return newInstance(
        value.backgroundColor.has_value() ? jni::make_jstring(value.backgroundColor.value()) : nullptr,
        value.columnCount.has_value() ? JColumnCount::fromCpp(value.columnCount.value()) : nullptr,
        value.fontFamily.has_value() ? jni::make_jstring(value.fontFamily.value()) : nullptr,
        value.fontSize.has_value() ? jni::JDouble::valueOf(value.fontSize.value()) : nullptr,
        value.fontWeight.has_value() ? jni::JDouble::valueOf(value.fontWeight.value()) : nullptr,
        value.hyphens.has_value() ? jni::JBoolean::valueOf(value.hyphens.value()) : nullptr,
        value.imageFilter.has_value() ? JImageFilter::fromCpp(value.imageFilter.value()) : nullptr,
        value.language.has_value() ? jni::make_jstring(value.language.value()) : nullptr,
        value.letterSpacing.has_value() ? jni::JDouble::valueOf(value.letterSpacing.value()) : nullptr,
        value.ligatures.has_value() ? jni::JBoolean::valueOf(value.ligatures.value()) : nullptr,
        value.lineHeight.has_value() ? jni::JDouble::valueOf(value.lineHeight.value()) : nullptr,
        value.pageMargins.has_value() ? jni::JDouble::valueOf(value.pageMargins.value()) : nullptr,
        value.paragraphIndent.has_value() ? jni::JDouble::valueOf(value.paragraphIndent.value()) : nullptr,
        value.paragraphSpacing.has_value() ? jni::JDouble::valueOf(value.paragraphSpacing.value()) : nullptr,
        value.publisherStyles.has_value() ? jni::JBoolean::valueOf(value.publisherStyles.value()) : nullptr,
        value.readingProgression.has_value() ? JReadingProgression::fromCpp(value.readingProgression.value()) : nullptr,
        value.scroll.has_value() ? jni::JBoolean::valueOf(value.scroll.value()) : nullptr,
        value.spread.has_value() ? JSpread::fromCpp(value.spread.value()) : nullptr,
        value.textAlign.has_value() ? JTextAlign::fromCpp(value.textAlign.value()) : nullptr,
        value.textColor.has_value() ? jni::make_jstring(value.textColor.value()) : nullptr,
        value.textNormalization.has_value() ? jni::JBoolean::valueOf(value.textNormalization.value()) : nullptr,
        value.theme.has_value() ? JTheme::fromCpp(value.theme.value()) : nullptr,
        value.typeScale.has_value() ? jni::JDouble::valueOf(value.typeScale.value()) : nullptr,
        value.verticalText.has_value() ? jni::JBoolean::valueOf(value.verticalText.value()) : nullptr,
        value.wordSpacing.has_value() ? jni::JDouble::valueOf(value.wordSpacing.value()) : nullptr
      );
    }
  };

} // namespace margelo::nitro::nitroreadium
