package cn.com.futurelottery.ui.fragment.football.conwinandlose;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.FooterListener;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.presenter.FooterAllEvent;
import cn.com.futurelottery.presenter.FooterOneEvent;
import cn.com.futurelottery.ui.adapter.MyViewPagerAdapter;
import cn.com.futurelottery.ui.adapter.NoTouchViewPager;
import cn.com.futurelottery.ui.fragment.football.winandlose.AllPassFragment;
import cn.com.futurelottery.ui.fragment.football.winandlose.OnePassFragment;


/**
 *
 * A simple {@link Fragment} subclass.
 * @author apple
 *  让球胜平负
 *
 */
public class ConWinAndFragment extends BaseFragment{


    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    NoTouchViewPager viewPager;
    @BindView(R.id.bottom_result_clear_tv)
    ImageView bottomResultClearTv;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private List<String> mDataList = new ArrayList<>();
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private int AllNumber = 0;
    private int OneNumber = 0;
    public ConWinAndFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_con_win_and;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
    }
    private void initFragment() {
        mDataList.add("过关 (至少选两场)");
        mDataList.add("单关 (猜一场,奖金固定)");
        tvSelect.setText("请至少选择2场比赛");

        mFragmentList.add(new ConAllPassFragment());
        mFragmentList.add(new ConOnePassFragment());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager(), mFragmentList);
        viewPager.setAdapter(myViewPagerAdapter);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

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

                        if (i == 0) {
                            if (AllNumber == 0) {
                                tvSelect.setText("请至少选择2场比赛");
                            } else {
                                tvSelect.setText("已选择" + AllNumber + "场");
                            }
                        } else if (i == 1) {
                            if (OneNumber == 0) {
                                tvSelect.setText("请至少选择1场比赛");
                            } else {
                                tvSelect.setText("已选择" + OneNumber + "场");
                            }

                        }

                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setLineHeight(3);
                indicator.setColors(getResources().getColor(R.color.red_ball));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerPadding(UIUtil.dip2px(BaseApplication.getInstance(), 15));
        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });

    }
    /**
     * 单关已选场次Nu更新
     * @param event
     */
    @Subscribe
    public void onOne(FooterOneEvent event) {
        if(event.getType()==3){
            AllNumber = event.getmMeeage();
            if (event.getmMeeage() != 0) {
                tvSelect.setText("已选择" + AllNumber + "场");
            } else {
                tvSelect.setText("请至少选择2场比赛");
            }
        }else if(event.getType()==4){
            OneNumber = event.getmMeeage();
            if (event.getmMeeage() != 0) {
                tvSelect.setText("已选择" + String.valueOf(OneNumber) + "场");
            } else {
                tvSelect.setText("请至少选择1场比赛");
            }
        }
    }

    @OnClick({R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_result_clear_tv:
                //清除
                int currentItem = viewPager.getCurrentItem();
                if(currentItem==0){
                    EventBus.getDefault().post(new FootCleanType(3));
                }else {
                    EventBus.getDefault().post(new FootCleanType(4));
                }
                break;
            case R.id.bottom_result_next_btn:
                int index = viewPager.getCurrentItem();
                if(index==0){
                    EventBus.getDefault().post(new FootSureType(3));
                }else {
                    EventBus.getDefault().post(new FootSureType(4));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
