package cn.com.futurelottery.base;

import android.app.Application;


/**
 * Created by RayYeung on 2016/8/8.
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
