package cn.com.futurelottery.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.com.futurelottery.R;
import cn.com.futurelottery.utils.StatusBarUtil;

/**
 * Created by apple on 2018/4/8.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        setStatusBar();
        setTitle();
    }

    public abstract int getLayoutResource();

    /**
     * 版本检测
     */
    protected  void checkVersion(){

    }
    protected  void setTitle(){

    }
    protected void setStatusBar() {
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary),0);
    }
}
