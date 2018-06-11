package com.xinhe.haoyuncaipiao.base;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import com.xinhe.haoyuncaipiao.utils.AppUtils;
import com.xinhe.haoyuncaipiao.utils.SPUtils;


/**
 * @author apple
 *
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    public String mobile;
    public String token;
    public String amount;
    public String userName;
    public String integral;
    public String channel;
    public String versionName;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //是否登录
        getLogin();
        initOkGo();
    }

    private void initOkGo() {
        //walle
        channel = WalleChannelReader.getChannel(this.getApplicationContext());

        //bugle
        CrashReport.initCrashReport(getApplicationContext(), Contacts.BUGLY_KEY, false);

        //umeng
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,Contacts.UMENG_KEY
                ,channel));
        versionName = AppUtils.getAppVersionName();
        AVOSCloud.initialize(this, Contacts.LEAN_ID, Contacts.LEAN_KEY);

    }

    private void getLogin() {

            mobile= (String) SPUtils.get(this,Contacts.MOBILE,"");
            token= (String) SPUtils.get(this,Contacts.TOKEN,"");
            amount= (String) SPUtils.get(this,Contacts.AMOUNT,"");
            userName= (String) SPUtils.get(this,Contacts.NICK,"");
            integral= (String) SPUtils.get(this,Contacts.INTEGRAL,"");
        //initokgo
        HttpHeaders headers = new HttpHeaders();
        headers.put("channel", channel);
        headers.put("os", versionName);
        headers.put(Contacts.TOKEN, token);
        OkGo.getInstance()
                .init(this)
                .setCacheMode(CacheMode.NO_CACHE)
                .addCommonHeaders(headers);
    }


    public static BaseApplication getInstance() {
        return instance;
    }
}
