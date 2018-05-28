package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.base.BaseApplication;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.view.supertextview.SuperTextView;

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

    @OnClick({R.id.layout_top_back, R.id.exit_btn,R.id.agreement_stv,R.id.about_stv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.exit_btn:
                setResult(-1);
                finish();
                break;
            case R.id.agreement_stv:
                Intent intent1=new Intent(this, WebViewActivity.class);
                intent1.putExtra("url", Contacts.H5.service);
                intent1.putExtra("title","服务协议");
                startActivity(intent1);
                break;
            case R.id.about_stv:
                Intent intent=new Intent(this, WebViewActivity.class);
                intent.putExtra("url", Contacts.H5.about);
                intent.putExtra("title","关于");
                startActivity(intent);
                break;
        }
    }
}
