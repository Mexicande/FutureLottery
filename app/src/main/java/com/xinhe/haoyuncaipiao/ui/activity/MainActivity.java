package com.xinhe.haoyuncaipiao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meituan.android.walle.WalleChannelReader;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.ui.adapter.NoTouchViewPager;
import com.xinhe.haoyuncaipiao.ui.dialog.AdialogFragment;
import com.xinhe.haoyuncaipiao.ui.fragment.CenterFragment;
import com.xinhe.haoyuncaipiao.ui.fragment.HomeFragment;
import com.xinhe.haoyuncaipiao.ui.fragment.LotteryFragment;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.StatusBarUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.pagerBottomTab.PageNavigationView;
import com.xinhe.haoyuncaipiao.view.pagerBottomTab.item.NormalItemView;
import com.xinhe.haoyuncaipiao.view.update.CProgressDialogUtils;
import com.xinhe.haoyuncaipiao.view.update.UpdateAppBean;
import com.xinhe.haoyuncaipiao.view.update.UpdateAppManager;

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
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.model.Popup;
import com.xinhe.haoyuncaipiao.ui.adapter.MyViewPagerAdapter;
import com.xinhe.haoyuncaipiao.ui.fragment.ChippedFragment;
import com.xinhe.haoyuncaipiao.utils.TimeUtils;
import com.xinhe.haoyuncaipiao.view.pagerBottomTab.NavigationController;
import com.xinhe.haoyuncaipiao.view.pagerBottomTab.item.BaseTabItem;
import com.xinhe.haoyuncaipiao.view.update.AppUpdateUtils;
import com.xinhe.haoyuncaipiao.view.update.OkGoUpdateHttpUtil;
import com.xinhe.haoyuncaipiao.view.update.UpdateCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.SettingService;

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
        //版本更新
        update();
        //权限管理
        checkMyPermission();
    }

    /**
     * 权限管理
     */
    private void checkMyPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .rationale((requestCode, rationale) ->
                        // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(this, rationale).show())
                .callback(listener)
                .start();
    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 200) {
                // TODO ...
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {
                // 是否有不再提示并拒绝的权限。
                if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {

                    // 第三种：自定义dialog样式。
                    SettingService settingService = AndPermission.defineSettingDialog(MainActivity.this, 400);
                    // 你的dialog点击了确定调用：
                    settingService.execute();
                    // 你的dialog点击了取消调用：
                    settingService.cancel();
                }else {
                    ToastUtils.showToast("权限被拒绝");
                }
            }
        }
    };

    private void aPopupDialog() {
        ApiService.GET_SERVICE(Api.GET_POPUP, this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray bannerArray = data.getJSONArray("data");
                    Gson gson=new Gson();
                    Type bannerType=new TypeToken<ArrayList<Popup>>(){}.getType();
                    ArrayList<Popup> jsonArray = gson.fromJson(bannerArray.toString(), bannerType);
                    if(jsonArray.size()>0&&jsonArray.get(0)!=null&&!jsonArray.get(0).getImg().isEmpty()){
                        long advertTime = (long) SPUtils.get(MainActivity.this,"AdvertTime",1111111111111L);
                        boolean today = TimeUtils.isToday(advertTime);
                        if (!today) {
                            AdialogFragment adialogFragment= AdialogFragment.newInstance(jsonArray.get(0));
                            adialogFragment.show(getSupportFragmentManager(),"adialogFragment");
                            long timeMillis = System.currentTimeMillis();
                            SPUtils.put(MainActivity.this,"AdvertTime", timeMillis);
                        }
                    }
                } catch (Exception e) {
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
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());

        Map<String, String> params = new HashMap<String, String>();
        params.put("qudao", "luck");
        params.put("channel", channel);
        new UpdateAppManager.Builder()
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
                                        .setConstraint(jsonObject.getBoolean("isForce"))
                                        //设置md5，可以不设置
                                        .setNewMd5(jsonObject.getString("md5"));
                            }

                        } catch (Exception e) {
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
                       // CProgressDialogUtils.showProgressDialog(MainActivity.this);
                    }
                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        //CProgressDialogUtils.cancelProgressDialog(MainActivity.this);

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
