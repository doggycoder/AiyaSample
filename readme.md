# 工程及SDK说明
此工程为AiyaSDK 使用的示例工程。工程中AiyaSDK包含的功能以Jar包及so库的形式提供，在libs文件夹下。当前版本的AiyaSDK采用模块化设计，当前主要包括：Core、Gift、Track、Beauty及ShortVideo模块。其中Core模块为必须模块，其他模块为功能模块，依赖于Core模块。功能模块之间可以任意组合。
例如Gift+Core模块，可用于直播全屏礼物。Track+Core模块，可用于人脸检测及特征点对齐。Gift+Track+Core模块，可提供脸萌效果等等。

# 集成说明

AiyaSDK的集成与使用主要步骤为：
1. 进入[哎吖宝宝特效](http://www.bbtexiao.com/site/free)官网，提交接入申请，填入applicationId，获取appKey。**applicationId一定不要乱填，确保填入你要集成的项目的applicationId，否则无法通过认证。**
2. 将sdk库加入工程，当前请使用jar及so库进行集成，后续将提供jcenter集成方式。
3. AiyaSDK认证。对于所有模块的认证，流程都是一致的。每个模块都有一个入口类，应用中要使用某个模块的功能时，需要调用`AiyaEffects.registerComponent`方法，注册这个模块。先设置监听器，在接收INIT消息时，注册需要使用的模块。AUTH消息为认证结果，如果无法使用特效，很多时候是因为认证失败。特效注册后，认证成功之前默认为模块可用，所以无需等待认证结果，可放心执行其他任务。具体代码如下：
```java
//先设置监听器，后初始化
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
AiyaEffects.init(getApplicationContext(),"your app key");
```
4. 使用需要的模块的功能。以直播礼物为例，只需使用AiyaEffectTextureView即可。具体使用参考Javadoc。