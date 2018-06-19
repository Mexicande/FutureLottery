package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.PaySucessEvent;
import com.xinhe.haoyuncaipiao.pay.alipay.Alipay;
import com.xinhe.haoyuncaipiao.pay.alipay.PayResult;
import com.xinhe.haoyuncaipiao.pay.wechat.Wechat;
import com.xinhe.haoyuncaipiao.ui.activity.chipped.ChippedActivity;
import com.xinhe.haoyuncaipiao.utils.SPUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

public class PayActivity extends BaseActivity {
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.information_tv)
    TextView informationTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.wechat_check_iv)
    ImageView wechatCheckIv;
    @BindView(R.id.alipay_check_iv)
    ImageView alipayCheckIv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.wechat_rl)
    RelativeLayout wechatRl;
    @BindView(R.id.alipay_rl)
    RelativeLayout alipayRl;
    private String orderid;
    //付款类型1微信2支付宝
    private int payType = 0;
    private String money;
    private SwitchHandler mHandler = new SwitchHandler(this);
    private static final int SDK_PAY_FLAG = 1;
    private InnerReceiver receiver;
    private KProgressHUD hud;

    @Override
    protected void setTitle() {
        tvTitle.setText("支付");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_pay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();
    }

    private void initView() {
        // 注册广播接收者
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contacts.INTENT_EXTRA_PAY_SUCESS);
        registerReceiver(receiver, filter);
    }

    private void setListener() {

    }

    private void getData() {
        Intent intent = getIntent();
        String information = intent.getStringExtra("information");
        money = intent.getStringExtra("money");
        orderid = intent.getStringExtra(Contacts.Order.ORDERID);
        informationTv.setText(information);
        moneyTv.setText(money + "元");

        //获取支付方式
        getPayType();
    }

    private void getPayType() {
        Map<String, String> map = new HashMap<>();

        map.put(Contacts.Order.TYPE, "1");

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Pay.mode, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray data1 = data.getJSONArray("data");
                    if (null != data1 && data1.length() > 0) {
                        for (int i = 0; i < data1.length(); i++) {
                            switch (data1.getJSONObject(i).getString("type")) {
                                case "1":
                                    payType = 1;
                                    wechatCheckIv.setImageResource(R.mipmap.pay_check);
                                    alipayCheckIv.setImageResource(R.mipmap.pay_uncheck);
                                    wechatRl.setVisibility(View.VISIBLE);
                                    break;
                                case "2":
                                    alipayRl.setVisibility(View.VISIBLE);
                                    if (wechatRl.getVisibility()==View.GONE){
                                        payType = 2;
                                        wechatCheckIv.setImageResource(R.mipmap.pay_uncheck);
                                        alipayCheckIv.setImageResource(R.mipmap.pay_check);
                                    }
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
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

    @OnClick({R.id.layout_top_back, R.id.wechat_rl, R.id.alipay_rl, R.id.pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.wechat_rl:
                wechatCheckIv.setImageResource(R.mipmap.pay_check);
                alipayCheckIv.setImageResource(R.mipmap.pay_uncheck);
                payType = 1;
                break;
            case R.id.alipay_rl:
                wechatCheckIv.setImageResource(R.mipmap.pay_uncheck);
                alipayCheckIv.setImageResource(R.mipmap.pay_check);
                payType = 2;
                break;
            case R.id.pay_btn:
                submit();
                break;
        }
    }

    private void submit() {
        if (payType == 0) {
            ToastUtils.showToast("当前暂无可用支付方式");
            return;
        }
        Map<String, String> map = new HashMap<>();

        map.put(Contacts.Order.ORDERID, orderid);
        map.put(Contacts.Order.MONEY, money);
        map.put(Contacts.Order.TYPE, payType + "");

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_zhifu))
                .setDimAmount(0.5f)
                .show();

        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Pay.pay, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (payType == 1) {
                        JSONObject payInfo1 = data.getJSONObject("data");
                        Wechat wechat = new Wechat(PayActivity.this);
                        wechat.pay(payInfo1.toString());
                    } else if (payType == 2) {
                        String payInfo = data.getString("data");
                        Alipay alipay = new Alipay(PayActivity.this);
                        alipay.setHander(mHandler);
                        alipay.pay(payInfo);
                    }else {
                        String payInfo2 = data.getString("data");
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri uri = Uri.parse(payInfo2);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                } catch (Exception e) {
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

    private static class SwitchHandler extends Handler {

        private WeakReference<PayActivity> mWeakReference;

        SwitchHandler(PayActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PayActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);
                        /**
                         * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                         * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                         * docType=1) 建议商户依赖异步通知
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                            // 发广播

                            Intent intent = new Intent();
                            intent.setAction(Contacts.INTENT_EXTRA_PAY_SUCESS);
                            activity.sendBroadcast(intent);
                        } else {
                            // 判断resultStatus 为非"9000"则代表可能支付失败
                            // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }

            }
        }
    }

    // 广播接收者
    private class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取Intent中的Action
            String action = intent.getAction();
            // 判断Action
            if (Contacts.INTENT_EXTRA_PAY_SUCESS.equals(action)) {
                boolean chip = SPUtil.contains(PayActivity.this, "chip");
                if(chip){
                    EventBus.getDefault().post(new PaySucessEvent());
                }
                PayActivity.this.setResult(-1);
                finish();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
