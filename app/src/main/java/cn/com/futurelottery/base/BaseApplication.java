package cn.com.futurelottery.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.util.logging.Level;

import cn.com.futurelottery.utils.AppUtils;


/**
 * @author apple
 *
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initOkGo();
    }

    private void initOkGo() {
        //walle
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());

        //bugle
        CrashReport.initCrashReport(getApplicationContext(), Contacts.BUGLY_KEY, false);

        //umeng
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,Contacts.UMENG_KEY
                ,channel));
        String versionName = AppUtils.getAppVersionName();

        //initokgo
        HttpHeaders headers = new HttpHeaders();
        headers.put("channel", channel);
        headers.put("os", versionName);
        OkGo.getInstance()
                    .init(this)
                    .setCacheMode(CacheMode.NO_CACHE)
                    .addCommonHeaders(headers)
                    ;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
