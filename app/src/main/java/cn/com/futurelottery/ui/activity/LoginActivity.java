package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.StatusBarUtil;
import cn.com.futurelottery.view.topRightMenu.MenuItem;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;
import cn.com.futurelottery.view.topRightMenu.TopRightMenu;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    ArrayList<MenuItem>list=new ArrayList<>();
    private boolean flag = false;
    private TopRightMenu mTopRightMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.clear();
        list.add(new MenuItem(0, "多期机选"));
        list.add(new MenuItem(0, "显示遗漏"));
        list.add(new MenuItem(0, "玩法说明"));
        mTopRightMenu = new TopRightMenu(this);
        mTopRightMenu.addMenuItems(list);
        layoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    RoteteUtils.rotateArrow(ivArrow, false);
                    flag = false;
                } else {
                    RoteteUtils.rotateArrow(ivArrow, true);
                    flag = true;
                }
            }
        });

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopRightMenu.setWidth(230)
                        .setHeight(350)
                        .setShowIcon(false)
                        .setShowAnimationStyle(true)
                        .setAnimationStyle(R.style.TopRightMenu_Anim)
                        .setShowBackground(true)
                        .setArrowPosition(CommonUtil.dip2px(55f))
                        .setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
                            @Override
                            public void onTopRightMenuItemClick(int position) {
                                Toast.makeText(LoginActivity.this, " 点击位置 :" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                mTopRightMenu.showAsDropDown(ivMenu,40,0);
            }
        });
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }
    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.login);
    }
    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(LoginActivity.this, 0,null);
    }
}
