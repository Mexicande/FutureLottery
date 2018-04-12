package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.view.View;
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
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        StatusBarUtil.setTranslucentForImageViewInFragment(LoginActivity.this, 0, null);
    }

}
