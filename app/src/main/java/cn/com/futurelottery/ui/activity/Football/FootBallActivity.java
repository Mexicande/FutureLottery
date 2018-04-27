package cn.com.futurelottery.ui.activity.Football;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mancj.slideup.SlideUp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.DialogListener;
import cn.com.futurelottery.ui.fragment.FragmentController;
import cn.com.futurelottery.ui.fragment.football.halffootball.HalfFragment;
import cn.com.futurelottery.ui.fragment.football.ScoreFragment;
import cn.com.futurelottery.ui.fragment.football.conwinandlose.ConWinAndFragment;
import cn.com.futurelottery.ui.fragment.football.sizefootball.SizeFragment;
import cn.com.futurelottery.ui.fragment.football.winandlose.WinAndLoseFragment;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.MenuDecoration;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.supertextview.SuperButton;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;
import cn.com.futurelottery.view.topRightMenu.TRMenuAdapter;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

/**
 * @author apple
 *         足彩
 */
public class FootBallActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.foot_fragment)
    FrameLayout fragment;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.layoutGo)
    LinearLayout layoutGo;
    @BindView(R.id.slideView)
    LinearLayout slideView;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    private boolean flag = false;
    private SlideUp slideUp;
    final ArrayList<MenuItem> mlist = new ArrayList<>();
    private ArrayList<MenuItem> list = new ArrayList<>();
    private TRMenuAdapter mTRMenuAdapter,mBottomTRMenuAdapter;
    private TopRightMenu mtopRightMenu;
    BottomSheetDialog mBottomSheetDialog;
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    FragmentController instance;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTitle.setText("胜平负");
        initBottomSheetDialog();
        setTopTitlePop();
        initView();
        setListener();
    }

    private void getDate() {
        /**
         * 赛事列表
         */
        for(int i = 0; i < mFragmentList.size(); i++) {
            Fragment fragment = mFragmentList.get(i);
            if(fragment!=null && fragment.isAdded()&&fragment.isVisible()) {

            }
        }

        //ApiService.GET_SERVICE(Api.FootBall_Api.PayList,this,);
        instance.hideFragments();


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
                MenuItem item = mlist.get(position);
                tvTitle.setText(item.getContent());
                RoteteUtils.rotateArrow(ivArrow, flag);
                flag = !flag;
                instance.showFragment(position);

            }
        });
    }

    private RecyclerView mBootRecycler;
    private TextView tv_all,tv_unAll,tv_FiveAll;
    final ArrayList<MenuItem> mSrceenList = new ArrayList<>();
    private SuperButton mSuperCancel,mSuperSure;
    /**
     * 筛选
     */
    private void initBottomSheetDialog() {

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.screen_bootom_sheet_layout, null, false);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);




        mBootRecycler=view.findViewById(R.id.bottom_recyclerView);
        tv_all=view.findViewById(R.id.tv_all);
        tv_unAll=view.findViewById(R.id.tv_un_all);
        tv_FiveAll=view.findViewById(R.id.five_all);
        mSuperCancel=view.findViewById(R.id.super_cancel);
        mSuperSure=view.findViewById(R.id.super_sure);


        mSrceenList.add(new MenuItem(0, "英冠"));
        mSrceenList.add(new MenuItem(0, "英甲"));
        mSrceenList.add(new MenuItem(0, "比分"));
        mSrceenList.add(new MenuItem(0, "K联赛"));
        mSrceenList.add(new MenuItem(0, "欧冠杯"));
        mSrceenList.add(new MenuItem(0, "瑞超"));
        mSrceenList.add(new MenuItem(0, "巴西杯"));

        mBootRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mBootRecycler.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10),3));
        mBottomTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item,mSrceenList);
        mBootRecycler.setAdapter(mBottomTRMenuAdapter);
        mBottomTRMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MenuItem menuItem = mSrceenList.get(position);
                if(menuItem.getIcon()==3){
                    menuItem.setIcon(0);
                }else {
                    menuItem.setIcon(3);
                }
                mSrceenList.set(position,menuItem);
                mBottomTRMenuAdapter.setData(position,menuItem);
                mBottomTRMenuAdapter.notifyItemChanged(position);
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MenuItem s:mSrceenList){
                    s.setIcon(3);
                }
                mBottomTRMenuAdapter.setNewData(mSrceenList);
            }
        });
        tv_unAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MenuItem s:mSrceenList){
                    s.setIcon(0);
                }
                mBottomTRMenuAdapter.setNewData(mSrceenList);
            }
        });
        mSuperCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        mSuperSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //筛选
                ToastUtils.showToast("筛选");
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10),3));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item,mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);
        mFragmentList.add(new WinAndLoseFragment());
        mFragmentList.add(new ConWinAndFragment());
        mFragmentList.add(new ScoreFragment());
        mFragmentList.add(new SizeFragment());
        mFragmentList.add(new HalfFragment());
         instance = FragmentController.getInstance(this, R.id.foot_fragment, true, mFragmentList);
        instance.showFragment(0);


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

        mlist.add(new MenuItem(1, "胜平负"));
        mlist.add(new MenuItem(0, "让球胜平负"));
        mlist.add(new MenuItem(0, "比分"));
        mlist.add(new MenuItem(0, "总进球"));
        mlist.add(new MenuItem(0, "半全场"));


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

        mtopRightMenu.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {

            }
        });


    }

    @OnClick({R.id.layout_top_back, R.id.iv_screen, R.id.iv_menu, R.id.layout_title,
            R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.iv_screen:
                getDate();
                if(!mBottomSheetDialog.isShowing()) {
                    mBottomSheetDialog.show();
                }else {
                    mBottomSheetDialog.dismiss();
                }
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
                slideUp.hide();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
