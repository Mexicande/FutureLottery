package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

public class PayAffirmActivity extends BaseActivity {

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
    @BindView(R.id.information_tv)
    TextView informationTv;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.red_packet_tv)
    TextView redPacketTv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    private String information;
    private String money;
    private String lotid;
    private String json;
    private final int REGBALL=102;
    private String redId="";
    private String redMoney="";
    private KProgressHUD hud;
    private String url;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_pay_affirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    private void getData() {
        Intent intent=getIntent();
        information=intent.getStringExtra("information");
        lotid=intent.getStringExtra("lotid");
        json=intent.getStringExtra("json");
        money=intent.getStringExtra("money");

        tvTitle.setText("确认支付");
        informationTv.setText(information);
        moneyTv.setText(money+"元");
    }

    @OnClick({R.id.layout_top_back, R.id.pay_btn,R.id.red_packet_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.pay_btn:
                pay();
                break;
            case R.id.red_packet_ll:
                Intent intent=new Intent(PayAffirmActivity.this,RedPacketActivity.class);
                intent.putExtra("money",money);
                if ("FT005".equals(lotid)){
                    intent.putExtra("lotid","FT005");
                }else if ("ssqM".equals(lotid)){
                    intent.putExtra("lotid","ssq");
                }else if ("dltM".equals(lotid)){
                    intent.putExtra("lotid","dlt");
                }else {
                    intent.putExtra("lotid",lotid);
                }
                startActivityForResult(intent,REGBALL);
                break;
        }
    }

    private void pay() {
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，检查网络后重试");
            return;
        }


        switch (lotid) {
            case "ssq":
                url = Api.Double_Ball.POST_DOUBLE_BALL;
                break;
            case "dlt":
                url = Api.Super_Lotto.POST_DOUBLE_BALL;
                break;
            case "ftb":
                url=Api.FootBall_Api.Payment;
                break;
            case "FT005"://足球混合
                url=Api.FootBall_Api.blend;
                break;
            case "p3":
                url = Api.Line.order;
                break;
            case "p5":
                url = Api.Line.five;
                break;
            case "3d":
                url = Api.Lottery3D.order;
                break;
            case "ssqM"://双色球多期机选
                url = Api.Double_Ball.POST_MULTI;
                break;
            case "dltM"://大乐透多期机选
                url = Api.Super_Lotto.POST_MULTI;
                break;
        }
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("支付中...")
                .setDimAmount(0.5f)
                .show();
        JSONObject jsonObject = null;
        try {
            jsonObject=new JSONObject(json);
            jsonObject.put("red_id",redId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(url, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (code == Api.Special_Code.notEnoughMoney) {
                        Intent intent = new Intent(PayAffirmActivity.this, PayActivity.class);
                        intent.putExtra("information",information);
                        intent.putExtra("money", data.getJSONObject("data").getString(Contacts.Order.MONEY));
                        intent.putExtra(Contacts.Order.ORDERID, data.getJSONObject("data").getString(Contacts.Order.ORDERID));
                        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
                    } else if (code == 0) {
                        try {
                            JSONObject data1 = data.getJSONObject("data");
                            Intent intent=new Intent(PayAffirmActivity.this, PaySucessActivity.class);
                            intent.putExtra("kind",data1.getString("lotid"));
                            intent.putExtra("type",data1.getString("type"));
                            intent.putExtra("orderid",data1.getString("order_id"));
                            startActivityForResult(intent,Contacts.REQUEST_CODE_TO_PAY);
                            setResult(-1);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1){
            switch (requestCode){
                case REGBALL:
                    redId=data.getStringExtra("id");
                    redMoney=data.getStringExtra("redMoney");
                    redPacketTv.setText("-￥"+redMoney);
                    break;
                case Contacts.REQUEST_CODE_TO_PAY:
                    setResult(-1);
                    finish();
                    break;
            }
        }
    }
}
