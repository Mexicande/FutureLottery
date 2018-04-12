package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mancj.slideup.SlideUp;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.utils.CommonUtil;
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
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.trm_recyclerview)
    RecyclerView trmRecyclerview;
    @BindView(R.id.slideView)
    LinearLayout slideView;
    private boolean flag = false;
    private SlideUp slideUp;
    private TRMenuAdapter mTRMenuAdapter;
    private TopRightMenu  mtopRightMenu;
    final ArrayList<MenuItem> mlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitlePop();
        initView();
        setListener();
    }



    @Override
    public int getLayoutResource() {
        return R.layout.activity_double_ball;
    }

    @OnClick({R.id.iv_menu, R.id.layout_title, R.id.layout_top_back,R.id.layoutGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_menu:
                mtopRightMenu.showAsDropDown(ivMenu,-50,0);
                break;
            case R.id.layout_title:
                if (flag) {
                    RoteteUtils.rotateArrow(ivArrow, true);
                    flag = false;
                } else {
                    RoteteUtils.rotateArrow(ivArrow, false);
                    flag = true;
                }
                if(slideUp.isVisible()){
                    slideUp.hide();
                }else {
                    slideUp.show();
                }
                break;
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.layoutGo:
                slideUp.hide();
                break;
            default:
                break;
        }
    }
    private void initView() {
        trmRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        trmRecyclerview.addItemDecoration(new MenuDecoration(CommonUtil.dip2px(40)));
        mTRMenuAdapter = new TRMenuAdapter(mlist);
        trmRecyclerview.setAdapter(mTRMenuAdapter);

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
                mlist.set(position,menuItem);
                mTRMenuAdapter.notifyItemChanged(position);
                for(int i=0;i<mlist.size();i++){
                    if(i!=position){
                        if(mlist.get(i).getIcon()==1){
                            MenuItem menuItem1 = mlist.get(i);
                            menuItem1.setIcon(0);
                            mlist.set(i,menuItem1);
                            mTRMenuAdapter.notifyItemChanged(i);
                        }
                    }
                }
                slideUp.hide();
            }
        });
        mtopRightMenu.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {
                ToastUtils.showToast(list.get(position).getContent());
            }
        });

    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.double_title);
    }

}
