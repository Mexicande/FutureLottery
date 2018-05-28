package com.xinhe.haoyuncaipiao.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关于
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tvTitle.setText("关于");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_about;
    }

    @OnClick({R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
        }
    }
}
