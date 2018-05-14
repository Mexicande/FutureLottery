package cn.com.futurelottery.ui.activity;

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
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

/**
 * 双色球
 *
 * @author apple
 */
public class DoubleBallActivity extends BaseActivity {

    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    ArrayList<MenuItem> list = new ArrayList<>();
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.slideView)
    LinearLayout slideView;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    private boolean flag = false;
    private SlideUp slideUp;
    private TRMenuAdapter mTRMenuAdapter;
    private TopRightMenu mtopRightMenu;
    final ArrayList<MenuItem> mlist = new ArrayList<>();
    private FragmentController instance;
    private int isShow;
    private DoubleBallCommonFragment commonFragment;
    private DoubleBallDuplexFragment duplexFragment;
    private ArrayList<String> omitsRed=new ArrayList<>();
    private ArrayList<String> omitsBlue=new ArrayList<>();

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.double_title);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_double_ball;
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
        ApiService.GET_SERVICE(Api.Double_Ball.GET_MISS, this, new JSONObject(), new OnRequestDataListener() {
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }


    @OnClick({R.id.iv_menu, R.id.layout_title, R.id.layout_top_back, R.id.layoutGo})
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
            default:
                break;
        }
    }

    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(40),2));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.item_constellation_layout,mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);
        commonFragment=new DoubleBallCommonFragment();
        duplexFragment=new DoubleBallDuplexFragment();
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
                instance.showFragment(position);
                slideUp.hide();
                switch (position){
                    case 0:
                        tvTitle.setText(R.string.double_title);
                        break;
                    case 1:
                        tvTitle.setText(R.string.doubleBile_title);
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
                    case 0:
                        Intent intent=new Intent(DoubleBallActivity.this,AutoSelectActivity.class);
                        intent.putExtra("intentType",0);
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
                    default:
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
