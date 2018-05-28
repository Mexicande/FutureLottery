package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.base.BaseApplication;
import com.xinhe.haoyuncaipiao.ui.fragment.redpacket.RedPacketUsableFragment;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.MyViewPagerAdapter;
import com.xinhe.haoyuncaipiao.ui.adapter.NoTouchViewPager;
import com.xinhe.haoyuncaipiao.ui.fragment.redpacket.RedPacketDisableFragment;

public class RedPacketActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    NoTouchViewPager viewPager;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mDataList = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private CommonNavigator commonNavigator;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_red_packet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initViews();
        //切换
        initMagicIndicator();
    }

    public void resetRedpacketCount(String count){
        mDataList.remove(0);
        mDataList.add(0,count);
        commonNavigator.notifyDataSetChanged();
    }

    private void initMagicIndicator() {
        mDataList.add("可用(0)");
        mDataList.add("用完/过期");
        mFragmentList.add(new RedPacketUsableFragment());
        mFragmentList.add(new RedPacketDisableFragment());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        viewPager.setAdapter(myViewPagerAdapter);

        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(i));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.color_333));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.red_ball));
                simplePagerTitleView.setTextSize(13);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(i);
                        viewPager.setCurrentItem(i);

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setLineHeight(3);
                indicator.setLineWidth(UIUtil.dip2px(context, 90));
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setColors(getResources().getColor(R.color.red_ball));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        //分割线
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(BaseApplication.getInstance(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
    }

    private void initViews() {
       tvTitle.setText("红包");
       questionMarkIv.setVisibility(View.VISIBLE);
    }

    private void getData() {

    }

    @OnClick({R.id.layout_top_back, R.id.question_mark_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.question_mark_iv:
                break;
        }
    }
}
