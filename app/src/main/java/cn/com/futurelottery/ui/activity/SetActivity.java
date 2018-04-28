package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.view.supertextview.SuperTextView;

public class SetActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tip_stv)
    SuperTextView tipStv;
    @BindView(R.id.shake_stv)
    SuperTextView shakeStv;
    @BindView(R.id.voice_stv)
    SuperTextView voiceStv;
    @BindView(R.id.agreement_stv)
    SuperTextView agreementStv;
    @BindView(R.id.about_stv)
    SuperTextView aboutStv;
    @BindView(R.id.exit_btn)
    Button exitBtn;


    @Override
    protected void setTitle() {
        tvTitle.setText("设置");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        if (TextUtils.isEmpty(BaseApplication.getInstance().token)){
            exitBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.layout_top_back, R.id.exit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.exit_btn:
                setResult(-1);
                finish();
                break;
        }
    }
}
