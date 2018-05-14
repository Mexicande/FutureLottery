package cn.com.futurelottery.ui.activity.arrange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.ui.fragment.DoubleBallCommonFragment;
import cn.com.futurelottery.ui.fragment.DoubleBallDuplexFragment;
import cn.com.futurelottery.ui.fragment.FragmentController;
import cn.com.futurelottery.ui.fragment.arrange.Line3CommonFragment;
import cn.com.futurelottery.ui.fragment.arrange.Line3SixFragment;
import cn.com.futurelottery.ui.fragment.arrange.Line3ThreeCompoundFragment;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

public class Line3Activity extends BaseActivity {

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
    private int isShow;
    private ArrayList<String> omitsZu = new ArrayList<>();
    private ArrayList<String> omitsHundred = new ArrayList<>();
    private ArrayList<String> omitsTen = new ArrayList<>();
    private ArrayList<String> omitsUnit = new ArrayList<>();
    ArrayList<MenuItem> list = new ArrayList<>();
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private Line3CommonFragment line3CommonFragment;
    private Line3ThreeCompoundFragment line3ThreeCompoundFragment;
    private Line3SixFragment line3SixFragment;

    @Override
    protected void setTitle() {
        tvTitle.setText("排列3直选");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_line3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitlePop();
        initView();
        setListener();
        getMissData();
    }

    private void setTopTitlePop() {
        slideUp = new SlideUp.Builder(slideView)
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withAutoSlideDuration(1)
                .withStartState(SlideUp.State.HIDDEN)
                .build();

        mlist.add(new MenuItem(1, "直选"));
        mlist.add(new MenuItem(0, "组三复式"));
        mlist.add(new MenuItem(0, "组六"));

        list.add(new MenuItem(0, "走势图"));
        list.add(new MenuItem(0, "显示遗漏"));
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

    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10),3));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item,mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);

        line3CommonFragment=new Line3CommonFragment();
        line3ThreeCompoundFragment=new Line3ThreeCompoundFragment();
        line3SixFragment = new Line3SixFragment();
        mFragmentList.add(line3CommonFragment);
        mFragmentList.add(line3ThreeCompoundFragment);
        mFragmentList.add(line3SixFragment);
        switchPages(0);
    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = mFragmentList.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = mFragmentList.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = mFragmentList.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
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
                switchPages(position);
                slideUp.hide();
                switch (position){
                    case 0:
                        tvTitle.setText("排列3直选");
                        break;
                    case 1:
                        tvTitle.setText("排列3组三复式");
                        break;
                    case 2:
                        tvTitle.setText("排列3组六");
                        break;
                }
                RoteteUtils.rotateArrow(ivArrow, flag);
                flag = !flag;
            }
        });
        mtopRightMenu.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {
                switch (position) {
                    case 0:

                        break;
                    case 1:
                        if (isShow==0){
                            list.set(1,new MenuItem(0, "隐藏遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow=1;
                            line3CommonFragment.showOmit(omitsHundred,omitsTen,omitsUnit);
                            line3ThreeCompoundFragment.showOmit(omitsZu);
                            line3SixFragment.showOmit(omitsZu);
                        }else {
                            list.set(1,new MenuItem(0, "显示遗漏"));
                            mtopRightMenu.addMenuItems(list);
                            isShow=0;
                            line3CommonFragment.unShowOmit();
                            line3ThreeCompoundFragment.unShowOmit();
                            line3SixFragment.unShowOmit();
                        }
                        break;
                    default:
                        break;
                }

            }
        });

    }

    //获取遗漏数据
    private void getMissData() {
        if (!DeviceUtil.IsNetWork(this)){
            ToastUtils.showToast("网络异常，请检查网络");
            return;
        }

        JSONObject jo1 = new JSONObject();
        try {
            jo1.put("type","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //直选遗漏
        ApiService.GET_SERVICE(Api.Line.miss, this, jo1, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONObject("data");

                    JSONArray ja3 = jo.getJSONArray(Contacts.Arrange.hundred);
                    for(int i=0;i<ja3.length();i++){
                        omitsHundred.add(ja3.getString(i));
                    }
                    JSONArray ja4 = jo.getJSONArray(Contacts.Arrange.ten);
                    for(int i=0;i<ja4.length();i++){
                        omitsTen.add(ja4.getString(i));
                    }
                    JSONArray ja5 = jo.getJSONArray(Contacts.Arrange.individual);
                    for(int i=0;i<ja5.length();i++){
                        omitsUnit.add(ja5.getString(i));
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

        JSONObject jo2 = new JSONObject();
        try {
            jo2.put("type","3");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //组三组六遗漏
        ApiService.GET_SERVICE(Api.Line.miss, this, jo2, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject jo = data.getJSONObject("data");

                    JSONArray ja5 = jo.getJSONArray("group");
                    for(int i=0;i<ja5.length();i++){
                        omitsZu.add(ja5.getString(i));
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

    @OnClick({R.id.iv_menu,R.id.layout_title,R.id.layout_top_back,R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.layout_top_back:
                finish();
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
}
