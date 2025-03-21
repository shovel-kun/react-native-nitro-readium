#include <jni.h>
#include "NitroReadiumOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::nitroreadium::initialize(vm);
}
