package com.aiyaapp.aiya.shadereffect.filter;

import android.util.Log;

import com.aiyaapp.aiya.shadereffect.AiyaShaderEffect;

import java.util.HashMap;
import java.util.Map;

/**
 * Shader特效基类
 */
public abstract class NativeCoolFilter{

    protected int type=0;
    protected long id=0;
    protected HashMap<String,Float> map;
    private boolean mapChanged=false;
    protected long createTime=-1;

    public NativeCoolFilter(int type){
        this.type=type;
    }

    protected void set(String key,float value){
        if(map==null){
            map=new HashMap<>();
        }
        map.put(key,value);
        mapChanged=true;
    }

    @Override
    protected void finalize() throws Throwable {
        AiyaShaderEffect.destroyEffect(id);
        super.finalize();
    }

    public int draw(int texture, int width, int height){
       return draw(texture, width, height,0);
    }

    public int draw(int texture,int width,int height,int flag){
        if(createTime<AiyaShaderEffect.getLastTime()){
            id=0;
            createTime=AiyaShaderEffect.getLastTime();
        }
        if(id==0){
            id = AiyaShaderEffect.createEffectObject(type);
            Log.e("wuwang","create effect id::"+id);
        }
        if(mapChanged){
            for (Map.Entry<String,Float> entry : map.entrySet()) {
                AiyaShaderEffect.setOptions(id,entry.getKey(), entry.getValue());
            }
            mapChanged=false;
        }
        return AiyaShaderEffect.drawEffect(id,texture,width,height);
    }

    /**
     * 连续多少帧
     * @param frame 大于或等于3
     */
    void _setConsecutiveFrame(int frame){
        set("LastTime",frame);
    }

    /**
     * 扩大率
     * @param ratio 0.0-0.8之间
     */
    void _setPhantasmsScale(float ratio){
        set("MaxScalingRatio",ratio);
    }

    void _setFrameWidth(int width){
        set("FrameWidth",width);
    }

    void _setFrameHeight(int height){
        set("FrameHeight",height);
    }

    /**
     * 设置尺寸，black magic
     * @param scale 1.0f-5.0f
     */
    void _setScale(float scale){
        set("Scale",scale);
    }

    /**
     * 设置转动速度
     * @param step 1-100之间
     */
    void _setRollStep(int step){
        set("RollStep",step);
    }

    /**
     * 设置分屏个数
     * @param size 1-5
     */
    void _setSplitSize(int size){
        set("SpliteSize",size);
    }

    /**
     * 设置是否滚动屏幕
     * @param roll
     */
    void _setRollScreen(boolean roll){
        set("RollScreen",roll?1:0);
    }

    void _setClockWise(boolean wise){
        set("ClockWise",wise?1:0);
    }

}
