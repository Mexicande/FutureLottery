package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.xinhe.haoyuncaipiao.model.FootBallOrder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;

public class FootBallOrderItemActivity extends BaseActivity {


    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.lottery_tv)
    TextView lotteryTv;
    @BindView(R.id.detail_fl)
    FlexboxLayout detailFl;
    private String type;
    private String lottery;

    @Override
    protected void setTitle() {
        tvTitle.setText("出票详情");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball_order_item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        lottery = intent.getStringExtra("lottery");
        ArrayList<FootBallOrder.DataProduct.ArrProduct> orders = (ArrayList<FootBallOrder.DataProduct.ArrProduct>) intent.getSerializableExtra("data");
        if (null != orders) {
            for (int i = 0; i < orders.size(); i++) {
                FootBallOrder.DataProduct.ArrProduct p = orders.get(i);
                String a = p.getWeek() + p.getTeam_id();
                String b = "[ " + p.getSelected() + " ]";
                String c = a + b;
                //改变篮球的颜色
                SpannableStringBuilder builder = new SpannableStringBuilder(c);
                ForegroundColorSpan yellowSpan = new ForegroundColorSpan(getResources().getColor(R.color.blue_ball));
                builder.setSpan(yellowSpan, c.length() - b.length(), c.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                TextView textView = new TextView(this);
                textView.setText(builder);
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(0, 0, 0, 0);
                textView.setTextColor(getResources().getColor(R.color.black));
                detailFl.addView(textView);
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                if (params instanceof FlexboxLayout.LayoutParams) {
                    FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                    layoutParams.setFlexGrow(0f);
                    layoutParams.setMargins(0, 0, 0, 0);
                }
            }
        }
        lotteryTv.setText(lottery);
        typeTv.setText(type);
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
