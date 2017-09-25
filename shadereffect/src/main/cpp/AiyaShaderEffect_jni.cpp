//
// Created by aiya on 2017/8/12.
//
#include <jni.h>
#include <assert.h>
#include "ShortVideoInterface.h"
#include "Log.h"
#include "AAiyaComponent.h"
#include "Observer.h"
#include "AE3dInterface.h"
#include "NdkTools.h"


#define AYEFFECTS_JAVA "com/aiyaapp/aiya/shadereffect/AiyaShaderEffect"

#define AYEFFECTS_CUTSCENE_JAVA "com/aiyaapp/aiya/shadereffect/AiyaCutSceneEffect"

#ifdef __cplusplus
extern "C" {
#endif

//todo 记录loadSystemLibrary的时间，如果外部未调用check时，默认是可以使用的三分钟，超过三分钟就不能使用

static AAiyaComponent component;

using namespace AYSDK::EFFECT;

jlong createEffect(JNIEnv * env, jclass clazz,jint type){
    Log::d("create effect : %d",type);
    return (jlong)AYSDK::AY_Effects_Create(type);
}

jint setOptions(JNIEnv * env,jclass clazz,jlong id,jstring key,jfloat value){
    int ret = -1;
    char *_key = (char *)env->GetStringUTFChars(key, 0);
    float _value = value;
    ret = AYSDK::AY_Effects_Set((AYSDK::TPointer)id, _key, _value);
    env->ReleaseStringUTFChars(key, _key);
    return ret;
}

jint drawEffect(JNIEnv * env,jclass clazz,jlong id,jint texture,jint w,jint h){
    if(!component.isForbid())
        return AYSDK::AY_Effects_Draw((AYSDK::TPointer)id, texture, w, h);
    return ObserverMsg::MSG_ERR_FUNC_FORBIDDEN;
}

jlong ayRequestId(JNIEnv * env, jclass clazz){
    return (jlong) &component;
}

jint destroyEffect(JNIEnv * env,jclass clazz,jlong id){
    return AYSDK::AY_Effects_Destroy((AYSDK::TPointer)id);
}

jint release(JNIEnv * env, jclass clazz){
    return AYSDK::AY_Effects_Finalize();
}



jlong csCreateEffect(JNIEnv * env , jclass clazz,jobject context,jint type,jint width,jint height){
    static std::shared_ptr<AE3d> obj;
    obj=AE3d::Create((AYSDK::EFFECT::AE3d::EType) type, width, height);
    obj->set("AssetManager",NdkTools::getAssetsManager(env,context));
    obj->initialize();
    return (jlong) obj.get();
}

jint csSetPath(JNIEnv * env,jclass clazz,jlong id,jstring key,jstring value){
    const char * k=env->GetStringUTFChars(key,JNI_FALSE);
    const char * v=env->GetStringUTFChars(value,JNI_FALSE);
    int ret=((AE3d *)id)->set(k,v);
    env->ReleaseStringUTFChars(key,k);
    env->ReleaseStringUTFChars(value,v);
    return ret;
}

jint csSetTexture(JNIEnv * env,jclass clazz,jlong id,jstring key,jint textureId,jint width,jint height){
    const char * k=env->GetStringUTFChars(key,JNI_FALSE);
    int ret=((AE3d *)id)->set(std::string(k), (unsigned int) textureId, width, height, AE3d::Format::RGBA);
    env->ReleaseStringUTFChars(key,k);
    return ret;
}

void csDraw(JNIEnv * env , jclass clazz,jlong id){
    ((AE3d *)id)->draw();
}

void csRelease(JNIEnv * env , jclass clazz,jlong id){
    ((AE3d *)id)->finialize();
}

static JNINativeMethod g_methods[]={
        {"createEffectObject",    "(I)J",                       (void *)createEffect},
        {"setOptions",            "(JLjava/lang/String;F)I",    (void *)setOptions},
        {"drawEffect",            "(JIII)I",                    (void *)drawEffect},
        {"destroyEffect",         "(J)I",                       (void *)destroyEffect},
        {"_release",              "()I",                        (void *)release},
        {"requestId",             "()J",                        (void *)ayRequestId},
};

static JNINativeMethod g_cutscene_methods[]={
        {"createEffectObject",    "(Landroid/content/Context;III)J", (void *)csCreateEffect},
        {"_draw",                 "(J)V",                            (void *)csDraw},
        {"_release",              "(J)V",                            (void *)csRelease},
        {"set",                   "(JLjava/lang/String;Ljava/lang/String;)I",      (void *)csSetPath},
        {"set",                   "(JLjava/lang/String;III)I",                        (void *)csSetTexture},
};


JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JNIEnv* env = nullptr;

    if (vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK) {
        return JNI_ERR;
    }
    assert(env != nullptr);
    jclass clazz=env->FindClass(AYEFFECTS_JAVA);
    env->RegisterNatives(clazz, g_methods, (int) (sizeof(g_methods) / sizeof((g_methods)[0])));

    jclass clazz2=env->FindClass(AYEFFECTS_CUTSCENE_JAVA);
    env->RegisterNatives(clazz2, g_cutscene_methods, (int) (sizeof(g_cutscene_methods) / sizeof((g_cutscene_methods)[0])));

    return JNI_VERSION_1_4;
}

JNIEXPORT void JNI_OnUnload(JavaVM *jvm, void *reserved){
    //todo try ayDeInit here
}

#ifdef __cplusplus
}
#endif
