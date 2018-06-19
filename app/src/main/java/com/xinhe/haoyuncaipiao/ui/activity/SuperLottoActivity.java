package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mancj.slideup.SlideUp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.ui.fragment.FragmentController;
import com.xinhe.haoyuncaipiao.ui.fragment.SuperLottoCommonFragment;
import com.xinhe.haoyuncaipiao.ui.fragment.SuperLottoDuplexFragment;
import com.xinhe.haoyuncaipiao.utils.CommonUtil;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.MenuDecoration;
import com.xinhe.haoyuncaipiao.utils.RoteteUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.topRightMenu.MenuItem;
import com.xinhe.haoyuncaipiao.view.topRightMenu.OnTopRightMenuItemClickListener;
import com.xinhe.haoyuncaipiao.view.topRightMenu.TRMenuAdapter;
import com.xinhe.haoyuncaipiao.view.topRightMenu.TopRightMenu;

/**
 * 大乐透
 */

public class SuperLottoActivity extends BaseActivity {


    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.layoutGo)
    LinearLayout layoutGo;
    @BindView(R.id.slideView)
    LinearLayout slideView;
    private boolean flag = false;
    private SlideUp slideUp;
    private TRMenuAdapter mTRMenuAdapter;
    private TopRightMenu mtopRightMenu;
    final ArrayList<MenuItem> mlist = new ArrayList<>();
    private FragmentController instance;
    private int isShow;
    private SuperLottoCommonFragment commonFragment;
    private SuperLottoDuplexFragment duplexFragment;
    private ArrayList<String> omitsRed=new ArrayList<>();
    private ArrayList<String> omitsBlue=new ArrayList<>();
    ArrayList<MenuItem> list = new ArrayList<>();
    ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.title_activity_super_lotto);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_super_lotto;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitlePop();
        initView();
        setListener();
        getMissData();
    }


    //获取遗漏数据
    private void getMissData() {
        if (!DeviceUtil.IsNetWork(this)){
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }
        //遗漏
        ApiService.GET_SERVICE(Api.Super_Lotto.GET_MISS, this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONObject("data");
                    JSONArray ja1 = jo.getJSONArray(Contacts.RED);
                    for(int i=0;i<ja1.length();i++){
                        omitsRed.add(ja1.getString(i));
                    }
                    JSONArray ja2 = jo.getJSONArray(Contacts.BLU);
                    for(int i=0;i<ja2.length();i++){
                        omitsBlue.add(ja2.getString(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }


    @OnClick({R.id.layout_top_back, R.id.iv_menu, R.id.layout_title, R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.iv_menu:
                mtopRightMenu.showAsDropDown(ivMenu, -50, 0);
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


    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(40),2));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.item_constellation_layout,mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);
        commonFragment=new SuperLottoCommonFragment();
        duplexFragment=new SuperLottoDuplexFragment();
        mFragmentList.add(commonFragment);
        mFragmentList.add(duplexFragment);
        instance = FragmentController.getInstance(this, R.id.fragment, true, mFragmentList);
        instance.showFragment(0);
    }

    private void setTopTitlePop() {
        //下拉选择
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

        mlist.add(new MenuItem(1, "标准选号"));
        mlist.add(new MenuItem(0, "胆拖选号"));

        list.add(new MenuItem(0, "多期机选"));
        list.add(new MenuItem(0, "显示遗漏"));
        list.add(new MenuItem(0, "近期开奖"));
        list.add(new MenuItem(0, "走势图"));
        list.add(new MenuItem(0, "玩法说明"));
        //右上菜单
        mtopRightMenu = new TopRightMenu(this);
        mtopRightMenu.addMenuItems(list);
        mtopRightMenu.setWidth(230)
                .setHeight(350)
                .setShowIcon(false)
                .setShowAnimationStyle(true)
                .setAnimationStyle(R.style.TopRightMenu_Anim)
                .setShowBackground(true)
                .setArrowPosition(CommonUtil.dip2px(55f));


    }

    private void setListener() {

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
                switch (position){
                    case 0:
                        tvTitle.setText(R.string.title_activity_super_lotto);
                        break;
                    case 1:
                        tvTitle.setText(R.string.title_activity_super_lotto_dan);
                        break;
                    default:
                        break;
                }
                RoteteUtils.rotateArrow(ivArrow, flag);
                flag = !flag;
                instance.showFragment(position);
            }
        });
        mtopRightMenu.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {
                switch (position) {
                    case 0://多期机选
                        Intent intent=new Intent(SuperLottoActivity.this,AutoSelectActivity.class);
                        intent.putExtra("intentType",1);
                        startActivity(intent);
                        break;
                    case 1:
                        if (isShow==0){
                            list.set(1,new MenuItem(0, "隐藏遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow=1;
                            commonFragment.showOmit(omitsRed,omitsBlue);
                            duplexFragment.showOmit(omitsRed,omitsBlue);
                        }else {
                            list.set(1,new MenuItem(0, "显示遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow=0;
                            commonFragment.unShowOmit();
                            duplexFragment.unShowOmit();
                        }
                        break;
                    case 2://开奖信息
                        Intent intent2 = new Intent(SuperLottoActivity.this, LotteryInformationActivity.class);
                        intent2.putExtra("type", "dlt");
                        startActivity(intent2);
                        break;
                    case 3://走势图
                        Intent intent1=new Intent(SuperLottoActivity.this, WebViewActivity.class);
                        intent1.putExtra("url",Contacts.H5.dlt);
                        intent1.putExtra("title","大乐透走势图");
                        startActivity(intent1);
                        break;
                    case 4://玩法说明
                        Intent intent4=new Intent(SuperLottoActivity.this, WebViewActivity.class);
                        intent4.putExtra("url","http://p96a3nm36.bkt.clouddn.com/dlt.jpg");
                        intent4.putExtra("title","大乐透玩法说明");
                        startActivity(intent4);
                        break;
                }

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }
}
