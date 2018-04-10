package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.ui.adapter.MyViewPagerAdapter;
import cn.com.futurelottery.ui.adapter.NoTouchViewPager;
import cn.com.futurelottery.ui.fragment.HomeFragment;
import cn.com.futurelottery.ui.fragment.LotteryFragment;
import cn.com.futurelottery.ui.fragment.MeFragment;
import cn.com.futurelottery.view.pagerBottomTab.NavigationController;
import cn.com.futurelottery.view.pagerBottomTab.PageNavigationView;
import cn.com.futurelottery.view.pagerBottomTab.item.BaseTabItem;
import cn.com.futurelottery.view.pagerBottomTab.item.NormalItemView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab)
    PageNavigationView tab;
    @BindView(R.id.app_item)
    NoTouchViewPager appItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottom();
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
        list.add(new MeFragment());
        appItem.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), list));
        //自动适配ViewPager页面切换
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
