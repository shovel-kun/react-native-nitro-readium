///
/// HybridReadiumModuleSpec.kt
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

package com.margelo.nitro.nitroreadium

import androidx.annotation.Keep
import com.facebook.jni.HybridData
import com.facebook.proguard.annotations.DoNotStrip
import com.margelo.nitro.core.*

/**
 * A Kotlin class representing the ReadiumModule HybridObject.
 * Implement this abstract class to create Kotlin-based instances of ReadiumModule.
 */
@DoNotStrip
@Keep
@Suppress(
  "KotlinJniMissingFunction", "unused",
  "RedundantSuppression", "RedundantUnitReturnType", "SimpleRedundantLet",
  "LocalVariableName", "PropertyName", "PrivatePropertyName", "FunctionName"
)
abstract class HybridReadiumModuleSpec: HybridObject() {
  @DoNotStrip
  private var mHybridData: HybridData = initHybrid()

  init {
    super.updateNative(mHybridData)
  }

  override fun updateNative(hybridData: HybridData) {
    mHybridData = hybridData
    super.updateNative(hybridData)
  }

  // Properties
  

  // Methods
  @DoNotStrip
  @Keep
  abstract fun add(a: Double, b: Double): Double
  
  @DoNotStrip
  @Keep
  abstract fun getManifest(absoluteUrl: String): Promise<String>

  private external fun initHybrid(): HybridData

  companion object {
    private const val TAG = "HybridReadiumModuleSpec"
  }
}
