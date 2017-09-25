package com.aiyaapp.aiya.shadereffect;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by aiya on 2017/9/16.
 */

public class AiyaCutSceneEffect {

    public static final int PARALLAX_SLIDE = 0x0001;
    public static final int FRAME_DISPLAY = 0x0002;

    private long nativeId=0;
    private Context mContext;
    private int type;

    public AiyaCutSceneEffect(Context context,int type){
        mContext=context.getApplicationContext();
        this.type=type;
    }

    public AiyaCutSceneEffect(Context context){
        this(context,FRAME_DISPLAY);
    }

    public void create(int width,int height){
        if(nativeId==0){
            nativeId=createEffectObject(mContext,type,width,height);
        }
    }

    public void setTexture(int index,int texture,int width,int height){
        set(nativeId,"Picture"+index,texture,width,height);
    }

    public void setPicture(int index,String path){
        set(nativeId,"Picture"+index,path);
    }


    public void draw(){
        _draw(nativeId);
    }

    private static native int set(long id,String key,String value);

    private static native int set(long id,String key,int texture,int width,int height);

    private static native long createEffectObject(Context context,int type,int width,int height);

    private static native void _draw(long id);

    private static native void _release(long id);


    @Override
    protected void finalize() throws Throwable {
        _release(nativeId);
        super.finalize();
    }

    static {
        System.loadLibrary("ae3d");
        System.loadLibrary("AyCoreSdk");
        System.loadLibrary("AyShaderEffect");
    }

}
