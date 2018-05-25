package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.Popup;
import cn.com.futurelottery.ui.adapter.MyViewPagerAdapter;
import cn.com.futurelottery.ui.adapter.NoTouchViewPager;
import cn.com.futurelottery.ui.dialog.AdialogFragment;
import cn.com.futurelottery.ui.fragment.CenterFragment;
import cn.com.futurelottery.ui.fragment.ChippedFragment;
import cn.com.futurelottery.ui.fragment.HomeFragment;
import cn.com.futurelottery.ui.fragment.LotteryFragment;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.StatusBarUtil;
import cn.com.futurelottery.utils.TimeUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.pagerBottomTab.NavigationController;
import cn.com.futurelottery.view.pagerBottomTab.PageNavigationView;
import cn.com.futurelottery.view.pagerBottomTab.item.BaseTabItem;
import cn.com.futurelottery.view.pagerBottomTab.item.NormalItemView;
import cn.com.futurelottery.view.update.AppUpdateUtils;
import cn.com.futurelottery.view.update.CProgressDialogUtils;
import cn.com.futurelottery.view.update.OkGoUpdateHttpUtil;
import cn.com.futurelottery.view.update.UpdateAppBean;
import cn.com.futurelottery.view.update.UpdateAppManager;
import cn.com.futurelottery.view.update.UpdateCallback;

/**
 * @author apple
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tab)
    PageNavigationView tab;
    @BindView(R.id.app_item)
    NoTouchViewPager appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottom();
        aPopupDialog();
        update();
    }

    private void aPopupDialog() {
        ApiService.GET_SERVICE(Api.GET_POPUP, this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson=new Gson();
                    Type bannerType=new TypeToken<ArrayList<Popup>>(){}.getType();
                    ArrayList<Popup> jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    if(jsonArray.get(0)!=null&&!jsonArray.get(0).getImg().isEmpty()){
                        long advertTime = (long) SPUtils.get(MainActivity.this,"AdvertTime",1111111111111L);
                        boolean today = TimeUtils.isToday(advertTime);
                        if (!today) {
                            AdialogFragment adialogFragment= AdialogFragment.newInstance(jsonArray.get(0));
                            adialogFragment.show(getSupportFragmentManager(),"adialogFragment");
                            long timeMillis = System.currentTimeMillis();
                            SPUtils.put(MainActivity.this,"AdvertTime", timeMillis);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 114,null);
    }
    private void initBottom() {
        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.mipmap.iv_normal_home, R.mipmap.iv_select_home, getResources().getString(R.string.buy_lottery)))
                .addItem(newItem(R.mipmap.iv_normal_chipped, R.mipmap.iv_select_chipped, getResources().getString(R.string.chipped)))
                .addItem(newItem(R.mipmap.iv_normal_lottery, R.mipmap.iv_select_lottery,  getResources().getString(R.string.open_lottery)))
                .addItem(newItem(R.mipmap.iv_normal_center, R.mipmap.iv_select_center,  getResources().getString(R.string.user_center)))
                .build();
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new ChippedFragment());
        list.add(new LotteryFragment());
        list.add(new CenterFragment());
        appItem.setOffscreenPageLimit(list.size());
        appItem.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list));
        navigationController.setupWithViewPager(appItem);
    }
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView onlyIconItemView = new NormalItemView(this);
        onlyIconItemView.initialize(drawable, checkedDrawable, text);
        onlyIconItemView.setTextDefaultColor(getResources().getColor(R.color.color_666));
        onlyIconItemView.setTextCheckedColor(getResources().getColor(R.color.colorPrimary));
        return onlyIconItemView;
    }


    /**
     * 版本升级
     */
    private int NewVersionCode=0;
    public void update() {
        NewVersionCode = AppUpdateUtils.getVersionCode(this);
        Map<String, String> params = new HashMap<String, String>();
        params.put("qudao", "luck");
        params.put("channel", "tencent");
        new UpdateAppManager
                .Builder()
                .setActivity(this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(Api.VERSION)
                //以下设置，都是可选
                .setPost(true)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                .hideDialogOnDownloading(false)
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                //为按钮，进度条设置颜色。
                //.setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {

                        UpdateAppBean updateAppBean = new UpdateAppBean();

                        try {
                            JSONObject jsonObject1 = new JSONObject(json);
                            if (0==jsonObject1.getInt("error_code")){
                                JSONObject jsonObject = jsonObject1.getJSONObject("data");
                                int size = jsonObject.getInt("size");
                                Double i = (double) size / 1024;
                                DecimalFormat df = new DecimalFormat("0.0");
                                String format = df.format(i);
                                int versioncode = jsonObject.getInt("versioncode");

                                String update="No";
                                if(versioncode>NewVersionCode){
                                    update="Yes";
                                }
                                updateAppBean
                                        //（必须）是否更新Yes,No
                                        .setUpdate(update)
                                        //（必须）新版本号，
                                        .setNewVersion(jsonObject.getString("versionname"))
                                        //（必须）下载地址
                                        .setApkFileUrl(jsonObject.getString("url"))
                                        //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
                                        //（必须）更新内容
                                        .setUpdateLog(jsonObject.getString("updatecontent"))
                                        //大小，不设置不显示大小，可以不设置
                                        .setTargetSize(String.valueOf(format)+"M")
                                        //是否强制更新，可以不设置
                                        .setConstraint((jsonObject.getInt("is_force")==1)?true:false)
                                        //设置md5，可以不设置
                                        .setNewMd5(jsonObject.getString("md5"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }
                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(MainActivity.this);

                    }
                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(MainActivity.this);

                    }
                });



    }



    private long mLastBackTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            ToastUtils.showToast("再按一次退出");
        }
    }


}
