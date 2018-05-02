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
import com.google.gson.Gson;
import com.mancj.slideup.SlideUp;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.PlayList;
import cn.com.futurelottery.presenter.CompetitionSelectType;
import cn.com.futurelottery.ui.fragment.football.halffootball.HalfFragment;
import cn.com.futurelottery.ui.fragment.football.scorefootball.ScoreFragment;
import cn.com.futurelottery.ui.fragment.football.scorefootball.ScoreSizeFragment;
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
    ArrayList<String>fivePlay=new ArrayList<>();
    private WinAndLoseFragment mWinAndLoseFragment;
    private ConWinAndFragment mConWinAndFragment;
    private SizeFragment mSizeFragment;
    private HalfFragment mHalfFragment;
    private ScoreFragment mScoreFragment;

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
        fivePlay.add("意甲");
        fivePlay.add("德甲");
        fivePlay.add("法甲");
        fivePlay.add("英超");
        fivePlay.add("西甲");
        /**
         * 赛事列表
         */
        for(int i = 0; i < mFragmentList.size(); i++) {
            Fragment fragment = mFragmentList.get(i);
            if(fragment!=null && fragment.isAdded()&&fragment.isVisible()) {
                switch (i){
                    case 0:
                        int currentItem = mWinAndLoseFragment.getCurrentItem();
                        if(currentItem==0){
                            getSelectPlay(Api.FOOTBALL.FT001,1);
                        }else {
                            getSelectPlay(Api.FOOTBALL.FT001,0);
                        }
                        break;
                    case 1:
                        int currentConWinAnd = mConWinAndFragment.getCurrentItem();
                        if(currentConWinAnd==0){
                            getSelectPlay(Api.FOOTBALL.FT001,1);
                        }else {
                            getSelectPlay(Api.FOOTBALL.FT001,0);
                        }
                        break;
                    case 2:
                        getSelectPlay(Api.FOOTBALL.FT002,1);
                        break;
                    case 3:
                        int currentSize = mSizeFragment.getCurrentItem();
                        if(currentSize==0){
                            getSelectPlay(Api.FOOTBALL.FT001,1);
                        }else {
                            getSelectPlay(Api.FOOTBALL.FT001,0);
                        }
                        break;
                    case 4:
                        int currentHalf = mHalfFragment.getCurrentItem();
                        if(currentHalf==0){
                            getSelectPlay(Api.FOOTBALL.FT001,1);
                        }else {
                            getSelectPlay(Api.FOOTBALL.FT001,0);

                        }
                        break;
                    default:
                        break;
                }

            }
        }

    }

    private void getSelectPlay(String i,int index) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pass_rules",index);
            jsonObject.put("play_rules",i);
        } catch (JSONException e) {


        }
        ApiService.GET_SERVICE(Api.FootBall_Api.PAY_LIST, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson=new Gson();
                PlayList playList = gson.fromJson(data.toString(), PlayList.class);
                List<PlayList.DataBean> palyList= playList.getData();
                mSrceenList.clear();
                int number = 0;
                for(PlayList.DataBean s:palyList){
                    MenuItem menuItem=new MenuItem();
                    menuItem.setContent(s.getLeague());
                    menuItem.setCount(s.getCount());
                    mSrceenList.add(menuItem);
                    number+=s.getCount();
                }
                tv_Session.setText(String.valueOf(number));
                mBottomTRMenuAdapter.setNewData(mSrceenList);
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }

    /**
     * 筛选
     */
    private void getPlayList() {
        List<MenuItem> data = mBottomTRMenuAdapter.getData();
        StringBuilder sb=new StringBuilder();
        for(MenuItem s:data){
            if(s.getIcon()==3){
                sb.append(s.getContent()).append(",");
            }
        }
        String str = sb.toString();
        if(str.length()==0){
            ToastUtils.showToast("筛选结果为空,请重新筛选比赛");
        }else {
            String substring = str.substring(0, str.length() - 1);
            for(int i = 0; i < mFragmentList.size(); i++) {
                Fragment fragment = mFragmentList.get(i);
                if (fragment != null && fragment.isAdded() && fragment.isVisible()) {
                    switch (i){
                        case 0:
                            int currentItem = mWinAndLoseFragment.getCurrentItem();
                            if(currentItem==0){
                                EventBus.getDefault().post(new CompetitionSelectType(1,substring));
                            }else {
                                EventBus.getDefault().post(new CompetitionSelectType(2,substring));
                            }
                            break;
                        case 1:
                            int currentConWinAnd = mConWinAndFragment.getCurrentItem();
                            if(currentConWinAnd==0){
                                EventBus.getDefault().post(new CompetitionSelectType(3,substring));
                            }else {
                                EventBus.getDefault().post(new CompetitionSelectType(4,substring));
                            }
                            break;
                        case 2:
                            EventBus.getDefault().post(new CompetitionSelectType(5,substring));
                            break;
                        case 3:
                            int currentSize = mSizeFragment.getCurrentItem();
                            if(currentSize==0){
                                EventBus.getDefault().post(new CompetitionSelectType(7,substring));
                            }else {
                                EventBus.getDefault().post(new CompetitionSelectType(8,substring));
                            }
                            break;
                        case 4:
                            int currentHalf = mHalfFragment.getCurrentItem();
                            if(currentHalf==0){
                                EventBus.getDefault().post(new CompetitionSelectType(9,substring));
                            }else {
                                EventBus.getDefault().post(new CompetitionSelectType(10,substring));
                            }
                            break;
                        default:
                            break;
                    }

                }
            }
        }
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
                switchPages(position);

            }
        });
    }

    private RecyclerView mBootRecycler;
    private TextView tv_all,tv_unAll,tv_FiveAll,tv_Session;
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
        tv_Session=view.findViewById(R.id.session);
        tv_unAll=view.findViewById(R.id.tv_un_all);
        tv_FiveAll=view.findViewById(R.id.five_all);
        mSuperCancel=view.findViewById(R.id.super_cancel);
        mSuperSure=view.findViewById(R.id.super_sure);

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
                mBottomTRMenuAdapter.notifyItemChanged(position);
                List<MenuItem> data = mBottomTRMenuAdapter.getData();
                int number=0;
                for(MenuItem s:data){
                    if(s.getIcon()==3){
                        number+=s.getCount();
                    }
                }
                tv_Session.setText(String.valueOf(number));
            }
        });
        tv_FiveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(MenuItem s:mSrceenList){
                    s.setIcon(0);
                }
                int number=0;
                for(int i=0;i<fivePlay.size();i++){
                    for(MenuItem s:mSrceenList){
                        if(s.getContent().equals(fivePlay.get(i))){
                            s.setIcon(3);
                            number+=s.getCount();
                        }
                    }
                }
                mBottomTRMenuAdapter.notifyDataSetChanged();
                tv_Session.setText(""+number);

            }
        });
        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MenuItem s:mSrceenList){
                    s.setIcon(3);
                }
                mBottomTRMenuAdapter.notifyDataSetChanged();

                List<MenuItem> data = mBottomTRMenuAdapter.getData();
                int number=0;
                for(MenuItem s:data){
                    number+=s.getCount();
                }
                tv_Session.setText(String.valueOf(number));
            }
        });
        tv_unAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=0;
                for(MenuItem s:mSrceenList){
                    if(s.getIcon()==3){
                        s.setIcon(0);
                    }else {
                        s.setIcon(3);
                        number+=s.getCount();
                    }
                }

                tv_Session.setText(number+"");
                mBottomTRMenuAdapter.notifyDataSetChanged();
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
                getPlayList();
                mBottomSheetDialog.dismiss();
            }
        });
    }

    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(10),3));
        mTRMenuAdapter = new TRMenuAdapter(R.layout.football_menu_item,mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);

         mWinAndLoseFragment=new WinAndLoseFragment();
         mConWinAndFragment=new ConWinAndFragment();
         mSizeFragment=new SizeFragment();
         mHalfFragment=new HalfFragment();
        mScoreFragment=new ScoreFragment();
        mFragmentList.add(mWinAndLoseFragment);
        mFragmentList.add(mConWinAndFragment);
        mFragmentList.add(mScoreFragment);
        mFragmentList.add(mSizeFragment);
        mFragmentList.add(mHalfFragment);
        switchPages(0);

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
                if(!mBottomSheetDialog.isShowing()) {
                    getDate();
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
            fragmentTransaction.add(R.id.foot_fragment, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
