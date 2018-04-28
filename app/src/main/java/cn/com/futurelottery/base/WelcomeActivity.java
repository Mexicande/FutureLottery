package cn.com.futurelottery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.futurelottery.R;
import cn.com.futurelottery.ui.activity.MainActivity;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.SharedPreferencesUtil;
import cn.com.futurelottery.utils.StatusBarUtil;

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
