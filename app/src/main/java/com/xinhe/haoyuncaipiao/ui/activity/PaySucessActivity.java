package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.ui.activity.order.BallOrderDetailActivity;
import com.xinhe.haoyuncaipiao.ui.activity.order.ChippedOrderDetailActivity;
import com.xinhe.haoyuncaipiao.ui.activity.order.FootBallOrderDetailActivity;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.ui.activity.order.BallOrderDetailOneActivity;

public class PaySucessActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.continue_btn)
    Button continueBtn;
    @BindView(R.id.order_btn)
    Button orderBtn;
    private String kind;
    private String type;
    private String orderid;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_pay_sucess;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    private void getData() {
        Intent intent=getIntent();
        kind=intent.getStringExtra("kind");
        type=intent.getStringExtra("type");
        orderid=intent.getStringExtra("orderid");
    }

    @OnClick({R.id.layout_top_back, R.id.continue_btn, R.id.order_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                setResult(-1);
                finish();
                break;
            case R.id.continue_btn:
                setResult(-1);
                finish();
                break;
            case R.id.order_btn:
                setResult(-1);
                orderDetail();
                break;
        }
    }

    private void orderDetail() {
        Intent intent=new Intent();
        if ("3".equals(type)){
            intent.putExtra("id",orderid);
            intent.putExtra("type","合买");
            intent.setClass(PaySucessActivity.this,ChippedOrderDetailActivity.class);
        }else{
            if ("2".equals(type)){
                intent.putExtra("id",orderid);
                intent.putExtra("type","追号投注");
                intent.setClass(PaySucessActivity.this,BallOrderDetailOneActivity.class);
            }else if ("1".equals(type)){
                intent.putExtra("id",orderid);
                intent.putExtra("type","普通投注");
                intent.setClass(PaySucessActivity.this,BallOrderDetailActivity.class);
            }
            switch (kind){
                case "dlt":
                    intent.putExtra("ballName","大乐透");
                    break;
                case "ssq":
                    intent.putExtra("ballName","双色球");
                    break;
                case "p3":
                    intent.putExtra("ballName","排列3");
                    break;
                case "p5":
                    intent.putExtra("ballName","排列5");
                    break;
                case "3d":
                    intent.putExtra("ballName","3D");
                    break;
                case "FT001":
                case "FT002":
                case "FT003":
                case "FT004":
                case "FT005":
                case "FT006":
                case "ftb":
                    intent.setClass(PaySucessActivity.this,FootBallOrderDetailActivity.class);
                    intent.putExtra("id",orderid);
                    intent.putExtra("type","普通投注");
                    intent.putExtra("ballName","竞彩足球");
                    break;
            }
        }
        intent.putExtra("lotid",kind);
        startActivity(intent);
    }
}
