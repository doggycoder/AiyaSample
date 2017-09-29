package com.aiyaapp.aiya.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.aiyaapp.aiya.AiyaGiftEffect;
import com.aiyaapp.aiya.Const;
import com.aiyaapp.aiya.R;
import com.aiyaapp.aiya.render.AiyaEffectTextureView;
import com.aiyaapp.aiya.render.AiyaMutilEffectView;
import com.aiyaapp.aiya.render.AnimListener;

/**
 * Created by aiya on 2017/9/21.
 */

public class GiftActivity extends AppCompatActivity {

    private AiyaMutilEffectView mGift;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        mGift= (AiyaMutilEffectView) findViewById(R.id.mGift);
        mGift.forbidChangeSizeWhenSurfaceRecreate(true);
        mGift.pauseIfSurfaceDestroyed(true);
        mGift.setEffect(1,"assets/gift/gaokongshiai/meta.json");
        mGift.setEffect(0,"assets/gift/shiwaitaoyuan/meta.json");

        mGift.setAnimListener(1,new AnimListener() {

            @Override
            public void onAnimEvent(int i, int i1, String s) {
                if(i== Const.MSG_TYPE_INFO){
                    if(i1== AiyaGiftEffect.MSG_STAT_EFFECTS_END){
                        Log.e("wuwang","播放完成");
                    }else if(i1==AiyaGiftEffect.MSG_STAT_EFFECTS_START){
                        Log.e("wuwang","播放开始");
                    }
                }else if(i==Const.MSG_TYPE_ERROR){
                    Log.e("wuwang","错误："+i+"/"+i1+"/"+s);
                }
            }
        });

        mGift.setAnimListener(2,new AnimListener() {

            @Override
            public void onAnimEvent(int i, int i1, String s) {
                if(i== Const.MSG_TYPE_INFO){
                    if(i1== AiyaGiftEffect.MSG_STAT_EFFECTS_END){
                        Log.e("wuwang","播放完成");
                    }else if(i1==AiyaGiftEffect.MSG_STAT_EFFECTS_START){
                        Log.e("wuwang","播放开始");
                    }
                }else if(i==Const.MSG_TYPE_ERROR){
                    Log.e("wuwang","错误："+i+"/"+i1+"/"+s);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGift.release();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.mBtnReplay:
                mGift.setEffect(1,"assets/gift/gaokongshiai/meta.json");
                break;
        }
    }
}
