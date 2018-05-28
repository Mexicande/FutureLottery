package com.xinhe.haoyuncaipiao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.ui.activity.MainActivity;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.StatusBarUtil;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.utils.SharedPreferencesUtil;

/**
 * @author apple
 *         引导页
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;
    @BindView(R.id.tv_guide_skip)
    TextView tvGuideSkip;
    @BindView(R.id.btn_guide_enter)
    Button btnGuideEnter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setListener();
        processLogic();
    }
    private void setListener() {

        bannerGuideBackground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                ActivityUtils.startActivity(MainActivity.class);
                SharedPreferencesUtil.putBoolean(WelcomeActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
                finish();
            }
        });
    }
    private void processLogic() {
        // 设置数据源
        bannerGuideBackground.setData(R.mipmap.lod_first, R.mipmap.lod_second, R.mipmap.lod_third);

    }
}
