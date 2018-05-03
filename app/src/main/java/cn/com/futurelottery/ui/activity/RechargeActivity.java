package cn.com.futurelottery.ui.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.pay.alipay.Alipay;
import cn.com.futurelottery.pay.alipay.PayResult;
import cn.com.futurelottery.pay.wechat.Wechat;
import cn.com.futurelottery.utils.ToastUtils;

public class RechargeActivity extends BaseActivity {


    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.account_tv)
    TextView accountTv;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.rb50)
    RadioButton rb50;
    @BindView(R.id.rb100)
    RadioButton rb100;
    @BindView(R.id.rb200)
    RadioButton rb200;
    @BindView(R.id.rb300)
    RadioButton rb300;
    @BindView(R.id.rb500)
    RadioButton rb500;
    @BindView(R.id.choose_profession_rg)
    RadioGroup chooseProfessionRg;
    @BindView(R.id.wechat_iv)
    ImageView wechatIv;
    @BindView(R.id.wechat_check_iv)
    ImageView wechatCheckIv;
    @BindView(R.id.wechat_rl)
    RelativeLayout wechatRl;
    @BindView(R.id.alipay_iv)
    ImageView alipayIv;
    @BindView(R.id.alipay_check_iv)
    ImageView alipayCheckIv;
    @BindView(R.id.alipay_rl)
    RelativeLayout alipayRl;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.input_money_et)
    EditText inputMoneyEt;
    private int money=50;
    //付款类型1微信2支付宝
    private int payType=1;
    private InnerReceiver receiver;
    private SwitchHandler mHandler = new SwitchHandler(this);
    private static final int SDK_PAY_FLAG = 1;


    @Override
    protected void setTitle() {
        tvTitle.setText("充值");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {

        inputMoneyEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMoneyEt.setFocusableInTouchMode(true);
                inputMoneyEt.setFocusable(true);
                inputMoneyEt.requestFocus();
                chooseProfessionRg.clearCheck();
                inputMoneyEt.setBackgroundResource(R.drawable.money_check);
                money=0;
            }
        });
        chooseProfessionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb50:
                        inputMoneyEt.setFocusable(false);
                        inputMoneyEt.setFocusableInTouchMode(false);
                        inputMoneyEt.setBackgroundResource(R.drawable.money_uncheck);
                        money=50;
                        break;
                    case R.id.rb100:
                        inputMoneyEt.setFocusable(false);
                        inputMoneyEt.setFocusableInTouchMode(false);
                        inputMoneyEt.setBackgroundResource(R.drawable.money_uncheck);
                        money=100;
                        break;
                    case R.id.rb200:
                        inputMoneyEt.setFocusable(false);
                        inputMoneyEt.setFocusableInTouchMode(false);
                        inputMoneyEt.setBackgroundResource(R.drawable.money_uncheck);
                        money=200;
                        break;
                    case R.id.rb300:
                        inputMoneyEt.setFocusable(false);
                        inputMoneyEt.setFocusableInTouchMode(false);
                        inputMoneyEt.setBackgroundResource(R.drawable.money_uncheck);
                        money=300;
                        break;
                    case R.id.rb500:
                        inputMoneyEt.setFocusable(false);
                        inputMoneyEt.setFocusableInTouchMode(false);
                        inputMoneyEt.setBackgroundResource(R.drawable.money_uncheck);
                        money=500;
                        break;
                }

            }
        });
    }

    private void initView() {
        // 注册广播接收者
        receiver = new InnerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contacts.INTENT_EXTRA_RECHARGE_SUCESS);
        registerReceiver(receiver, filter);


        inputMoneyEt.setFocusable(false);
    }

    @OnClick({R.id.layout_top_back, R.id.question_mark_iv, R.id.wechat_rl, R.id.alipay_rl, R.id.pay_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.question_mark_iv:
                break;
            case R.id.wechat_rl:
                wechatCheckIv.setImageResource(R.mipmap.pay_check);
                alipayCheckIv.setImageResource(R.mipmap.pay_uncheck);
                payType=1;
                break;
            case R.id.alipay_rl:
                wechatCheckIv.setImageResource(R.mipmap.pay_uncheck);
                alipayCheckIv.setImageResource(R.mipmap.pay_check);
                payType=2;
                break;
            case R.id.pay_btn:
                submit();
                break;
        }
    }

    private void submit() {
        if (money==0&&TextUtils.isEmpty(inputMoneyEt.getText().toString().trim())){
            ToastUtils.showToast("请选择金额");
            return;
        }
        if (money==0){
            money=Integer.parseInt(inputMoneyEt.getText().toString().trim());
        }
        Map<String, String> map = new HashMap<>();

        map.put(Contacts.Order.MONEY, money*100+"");
        map.put(Contacts.Order.TYPE, payType+"");


        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Pay.recharge, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (payType==1){
                        JSONObject payInfo1 = data.getJSONObject("data");
                        Wechat wechat = new Wechat(RechargeActivity.this);
                        wechat.pay(payInfo1.toString());
                    }else if (payType==2){
                        String payInfo = data.getString("data");
                        Alipay alipay = new Alipay(RechargeActivity.this);
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
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    private static class SwitchHandler extends Handler {

        private WeakReference<RechargeActivity> mWeakReference;

        SwitchHandler(RechargeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RechargeActivity activity = mWeakReference.get();
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
                            intent.setAction(Contacts.INTENT_EXTRA_RECHARGE_SUCESS);
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
            if (Contacts.INTENT_EXTRA_RECHARGE_SUCESS.equals(action)) {
                RechargeActivity.this.setResult(-1);
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
