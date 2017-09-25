package com.aiyaapp.aiya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aiyaapp.aiya.gift.GiftActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AiyaEffects.setEventListener(new IEventListener() {
            @Override
            public int onEvent(int i, int i1, String s) {
                if(i==Const.MSG_TYPE_INIT){
                    AiyaEffects.registerComponent(AiyaGiftEffect.class);
                }
                Log.e("wuwang","MSG(type/ret/info):"+i+"/"+i1+"/"+s);
                return 0;
            }
        });
        AiyaEffects.init(getApplicationContext(),"");
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.mBtnGift:
                startActivity(new Intent(this, GiftActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiyaEffects.deInit();
    }
}
