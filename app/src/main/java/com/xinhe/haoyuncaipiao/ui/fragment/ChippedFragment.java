package com.xinhe.haoyuncaipiao.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.slideup.SlideUp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.Chipped;
import com.xinhe.haoyuncaipiao.model.TogetherTop;
import com.xinhe.haoyuncaipiao.ui.activity.DoubleBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.Football.FootBallActivity;
import com.xinhe.haoyuncaipiao.ui.activity.SuperLottoActivity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line5Activity;
import com.xinhe.haoyuncaipiao.ui.activity.arrange.Lottery3DActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.ChippedAdapter;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.RoteteUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.topRightMenu.MenuItem;
import com.xinhe.haoyuncaipiao.view.topRightMenu.TRMenuAdapter;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.ui.activity.arrange.Line3Activity;
import com.xinhe.haoyuncaipiao.ui.activity.chipped.ChippedDetailActivity;
import com.xinhe.haoyuncaipiao.utils.CommonUtil;
import com.xinhe.haoyuncaipiao.utils.MenuDecoration;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

/**
 * 合买
 */
public class ChippedFragment extends BaseFragment {


    @BindView(R.id.iv_screen)
    ImageView ivScreen;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.chipped_start_ll)
    LinearLayout chippedStartLl;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.chipped_rv)
    RecyclerView chippedRv;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.layoutGo)
    LinearLayout layoutGo;
    @BindView(R.id.slideView)
    LinearLayout slideView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SlideUp slideUp;
    final ArrayList<MenuItem> mlist = new ArrayList<>();
    private boolean flag = false;
    private TRMenuAdapter mTRMenuAdapter;
    private ArrayList<TogetherTop> togetherTops=new ArrayList<>();
    private String[] kinds = new String[]{"战绩", "进度", "总额", "截止"};
    private int[] cliclRecords = new int[]{1,0, 0, 0};
    private List<String> mDataList = Arrays.asList(kinds);
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();
    private Chipped chipped;
    private ArrayList<Chipped.DataProduct.InfoProduct> chippeds=new ArrayList<>();
    private ChippedAdapter adapter;
    private int order=1;
    private int type=1;
    //0第一次不用显示，1显示
    private int isShowpro=0;
    private String lotid="all";
    private KProgressHUD hud;
    //标记是否显示提示
    private int isTip;


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_chipped;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        //顶部选择
        setTopTitlePop();
        initView();
        setView();
        //切换
        initMagicIndicator();
        setListener();
    }

    private void getData() {
        /**
         * 顶部的种类选择
         */
        mlist.clear();
        JSONObject jsonObject=new JSONObject();
        ApiService.GET_SERVICE(Api.Together.top, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    Type bannerType=new TypeToken<ArrayList<TogetherTop>>(){}.getType();
                    //加全部
                    TogetherTop togetherTop=new TogetherTop();
                    togetherTop.setLotid("all");
                    togetherTop.setTitle("全部彩种");

                    togetherTops.add(togetherTop);
                    togetherTops.addAll((ArrayList<TogetherTop>) gson.fromJson(data.getJSONArray("data").toString(), bannerType));
                    for (int i=0;i<togetherTops.size();i++){
                        if (i==0){
                            mlist.add(new MenuItem(1, togetherTops.get(i).getTitle()));
                        }else {
                            mlist.add(new MenuItem(0, togetherTops.get(i).getTitle()));
                        }

                    }
                    mTRMenuAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
        getChippedList(type,order,lotid,0);
    }

    /**
     * 获取合买列表
     */
    private void getChippedList(int type, int order, String lotid, final int startPosition) {
        if (!DeviceUtil.IsNetWork(getContext())){
            ToastUtils.showToast("网络异常");
            return;
        }
        if (isShowpro==1&&startPosition==0){
            hud = KProgressHUD.create(getContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("加载中...")
                    .setDimAmount(0.5f)
                    .show();
        }
        //标记
        isShowpro=1;

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("order",order);
            jsonObject.put("limit_begin",startPosition);
            jsonObject.put("limit_num",20);
            jsonObject.put("lotid",lotid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Together.list, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    chipped=gson.fromJson(data.toString(), Chipped.class);
                    ArrayList<Chipped.DataProduct.InfoProduct> info = chipped.getData().getInfo();
                    if (null==info||info.size()==0){
                        if (startPosition==0){
                            chippeds.clear();
                            adapter.notifyDataSetChanged();
                            if (isTip!=0){
                                ToastUtils.showToast("数据为空");
                            }else {
                                isTip=1;
                            }
                        }else {
                            ToastUtils.showToast("已经到底了");
                        }
                    }else {
                        if (0==startPosition){
                            chippeds.clear();
                        }
                        chippeds.addAll(info);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null!=hud){
                    hud.dismiss();
                }
                //停止刷新、加载更多
                if (refreshLayout.isLoading()){
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                if (null!=hud){
                    hud.dismiss();
                }
                //停止刷新、加载更多
                if (refreshLayout.isLoading()){
                    refreshLayout.finishLoadMore();
                }
                if (refreshLayout.isRefreshing()){
                    refreshLayout.finishRefresh();
                }
            }
        });
    }

    private void setView() {
        //顶部种类选择
        trmRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 3));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item, mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);
        //合买列表
        adapter=new ChippedAdapter(chippeds);
        chippedRv.setLayoutManager(new LinearLayoutManager(getContext()));
        chippedRv.setAdapter(adapter);
    }

    private void setListener() {
        //标题
        mTRMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuItem menuItem = mlist.get(position);
                menuItem.setIcon(1);
                mlist.set(position, menuItem);
                mTRMenuAdapter.notifyItemChanged(position);
                for (int i = 0; i < mlist.size(); i++) {
                    if (i != position) {
                        if (mlist.get(i).getIcon() == 1) {
                            MenuItem menuItem1 = mlist.get(i);
                            menuItem1.setIcon(0);
                            mlist.set(i, menuItem1);
                            mTRMenuAdapter.notifyItemChanged(i);
                        }
                    }
                }
                slideUp.hide();
                MenuItem item = mlist.get(position);
                tvTitle.setText(item.getContent());
                RoteteUtils.rotateArrow(ivArrow, flag);
                flag = !flag;

                lotid=togetherTops.get(position).getLotid();
                getChippedList(type,order,lotid,0);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())){
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishRefresh();
                    return;
                }
                if (togetherTops.size()==0){
                    getData();
                }else {
                    getChippedList(type,order,lotid,0);
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (!DeviceUtil.IsNetWork(getContext())){
                    ToastUtils.showToast("网络异常");
                    refreshLayout.finishLoadMore();
                    return;
                }
                getChippedList(type,order,lotid,chippeds.size());
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getContext(), ChippedDetailActivity.class);
                Chipped.DataProduct.InfoProduct clickChipped = chippeds.get(position);
                intent.putExtra("together_id",clickChipped.getTogether_id());
                intent.putExtra("lotid",clickChipped.getLotid());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        // toolbar不占据状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LinearLayout barLl = (LinearLayout) getView().findViewById(R.id.chipped_top);
            barLl.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) barLl.getLayoutParams();
            ll.height = getStatusBarHeight();
            ll.width = LinearLayout.LayoutParams.MATCH_PARENT;
            barLl.setLayoutParams(ll);
        }
    }

    //初始化切换按钮
    private void initMagicIndicator() {
        final CommonNavigator commonNavigator = new CommonNavigator(getActivity());
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
                simplePagerTitleView.setCompoundDrawablePadding(-70);
                if (cliclRecords[i]==1){
                    simplePagerTitleView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.chipped_descending_order,0);
                }else if (cliclRecords[i]==2){
                    simplePagerTitleView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.chipped_ascending_order,0);
                }else {
                    simplePagerTitleView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.chipped_default_order,0);
                }
                simplePagerTitleView.setPadding(0, 0, 70, 0);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragmentContainerHelper.handlePageSelected(i);

                        if (cliclRecords[i]==0){
                            cliclRecords[i]=1;
                            order=1;
                        }else if (cliclRecords[i]==1){
                            cliclRecords[i]=2;
                            order=2;
                        }else {
                            cliclRecords[i]=1;
                            order=1;
                        }

                        //其他归零
                        for (int j=0;j<cliclRecords.length;j++){
                            if (j!=i){
                                cliclRecords[j]=0;
                            }
                        }
                        switch (i){
                            case 0:
                                type=1;
                                getChippedList(type,order,lotid,0);
                                break;
                            case 1:
                                type=2;
                                getChippedList(type,order,lotid,0);
                                break;
                            case 2:
                                type=3;
                                getChippedList(type,order,lotid,0);
                                break;
                            case 3:
                                type=4;
                                getChippedList(type,order,lotid,0);
                                break;
                        }
                        commonNavigator.notifyDataSetChanged();
                    }
                });
                return simplePagerTitleView;
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
//        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        titleContainer.setDividerPadding(UIUtil.dip2px(BaseApplication.getInstance(), 15));
//        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);

    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @OnClick({R.id.tv_menu, R.id.layout_title,R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_menu:
                showTipDialog();
                break;
            case R.id.layout_title:
                RoteteUtils.rotateArrow(ivArrow, flag);
                flag = !flag;
                if (slideUp.isVisible()) {
                    slideUp.hide();
                } else {
                    slideUp.show();
                }
                break;
            case R.id.layoutGo:
                if (slideUp.isVisible()) {
                    RoteteUtils.rotateArrow(ivArrow, flag);
                    flag = !flag;
                    slideUp.hide();
                }
                break;
        }
    }

    /**
     * 合买选择
     */
    private void showTipDialog() {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(getContext(), R.style.CustomDialog).create();
        alertDialog1.setCancelable(true);
        alertDialog1.setCanceledOnTouchOutside(true);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        window1.setContentView(R.layout.chipped_choose);
        RecyclerView recyclerView = window1.findViewById(R.id.choose_rv);
        //顶部种类选择
        ArrayList<MenuItem> list = new ArrayList<>();
        list.addAll(mlist);
        //移除第一位全部彩种
        list.remove(0);
        for (int i=0;i<list.size();i++){
            list.get(i).setIcon(0);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10), 3));
        TRMenuAdapter mAdapter = new TRMenuAdapter(R.layout.football_menu_item, list);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (togetherTops.get(position+1).getLotid()){
                    case "ssq":
                        ActivityUtils.startActivity(DoubleBallActivity.class);
                        break;
                    case "dlt":
                        ActivityUtils.startActivity(SuperLottoActivity.class);
                        break;
                    case "ftb":
                        ActivityUtils.startActivity(FootBallActivity.class);
                        break;
                    case "p3":
                        ActivityUtils.startActivity(Line3Activity.class);
                        break;
                    case "p5":
                        ActivityUtils.startActivity(Line5Activity.class);
                        break;
                    case "3d":
                        ActivityUtils.startActivity(Lottery3DActivity.class);
                        break;
                }
                alertDialog1.dismiss();
            }
        });
    }


    /**
     * title菜单
     */
    private void setTopTitlePop() {
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

    }

}
