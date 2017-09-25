package com.aiyaapp.aiya.shadereffect;

/**
 * {@link AiyaShaderEffect} 为Shader特效功能的入口类，此类主要用于Shader特效功能模块的注册以及提供功能实现的基本方法。
 * Shader特效功能具体使用参考{@link com.aiyaapp.aiya.shadereffect.filter.CoolFilterFactory}
 */

public class AiyaShaderEffect{

    public static final int TYPE_NONE=0;            //  0——基本特效，直接绘制纹理
    public static final int TYPE_SPIRIT_FREED=1;    //  1——灵魂出窍
    public static final int TYPE_SHAKE=2;           //	2——抖动
    public static final int TYPE_BLACK_MAGIC=3;     //  3——Black Magic
    public static final int TYPE_VIRTUAL_MIRROR=4;  //  4——虚拟镜像
    public static final int TYPE_FLUORESCENCE=5;    //	5——荧光
    public static final int TYPE_TIME_TUNNEL=6;     //  6——时光隧道
    public static final int TYPE_DYSPHORIA =7;      //  7——躁动
    public static final int TYPE_FINAL_ZELIG=8;     //	8——终极变色
    public static final int TYPE_SPLIT_SCREEN=9;    //	9——动感分屏
    public static final int TYPE_HALLUCINATION=10;  //  10——幻觉
    public static final int TYPE_SEVENTYS=11;       //  11——70s
    public static final int TYPE_ROLL_UP=12;        //  12——炫酷转动
    public static final int TYPE_FOUR_SCREEN=13;    //  13——四分屏
    public static final int TYPE_THREE_SCREEN=14;   //  14——三分屏
    public static final int TYPE_BLACK_WHITE_TWINKLE=15;   //  15——黑白闪烁

    private static long releaseTime=0;

    private AiyaShaderEffect(){
    }

    public static native long createEffectObject(int type);

    public static native int setOptions(long id,String key,float value);

    public static native int drawEffect(long id,int textureId,int width,int height);

    public static native int destroyEffect(long id);

    public static int release(){
        releaseTime=System.currentTimeMillis();
        return _release();
    }

    public static long getLastTime(){
        return releaseTime;
    }

    private static native int _release();

    public static native long requestId();

    static {
        System.loadLibrary("ShortVideoEffects");
        System.loadLibrary("AyCoreSdk");
        System.loadLibrary("Soil");
        System.loadLibrary("AyShaderEffect");
    }

}
