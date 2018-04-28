package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.Popup;
import cn.com.futurelottery.ui.adapter.MyViewPagerAdapter;
import cn.com.futurelottery.ui.adapter.NoTouchViewPager;
import cn.com.futurelottery.ui.dialog.AdialogFragment;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.ui.fragment.CenterFragment;
import cn.com.futurelottery.ui.fragment.HomeFragment;
import cn.com.futurelottery.ui.fragment.LotteryFragment;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.StatusBarUtil;
import cn.com.futurelottery.utils.TimeUtils;
import cn.com.futurelottery.view.pagerBottomTab.NavigationController;
import cn.com.futurelottery.view.pagerBottomTab.PageNavigationView;
import cn.com.futurelottery.view.pagerBottomTab.item.BaseTabItem;
import cn.com.futurelottery.view.pagerBottomTab.item.NormalItemView;

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
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0,null);
    }

    private void initBottom() {
        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.mipmap.iv_normal_home, R.mipmap.iv_select_home, getResources().getString(R.string.buy_lottery)))
                .addItem(newItem(R.mipmap.iv_normal_lottery, R.mipmap.iv_select_lottery,  getResources().getString(R.string.open_lottery)))
                .addItem(newItem(R.mipmap.iv_normal_center, R.mipmap.iv_select_center,  getResources().getString(R.string.user_center)))
                .build();
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new LotteryFragment());
        list.add(new CenterFragment());
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
}
