package com.xinhe.haoyuncaipiao.ui.activity.chipped;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.xinhe.haoyuncaipiao.base.Api;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.ui.activity.LoginActivity;
import com.xinhe.haoyuncaipiao.ui.activity.PayActivity;
import com.xinhe.haoyuncaipiao.ui.activity.PaySucessActivity;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.SwitchMultiButton;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

public class ChippedActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.total_money_tv)
    TextView totalMoneyTv;
    @BindView(R.id.buy_money_et)
    EditText buyMoneyEt;
    @BindView(R.id.buy_money_tv)
    TextView buyMoneyTv;
    @BindView(R.id.switchmultibutton)
    SwitchMultiButton switchmultibutton;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.minimum_et)
    EditText minimumEt;
    @BindView(R.id.manifesto_et)
    EditText manifestoEt;
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.buy_btn)
    Button buyBtn;
    private int totalMoney=0;
    private ArrayAdapter<String> adapterRate;
    private String[] rate = { "0%", "1%", "2%", "3%", "4%", "5%", "6%", "7%", "8%"};
    //认购
    private int subscription;
    //最低认购金额
    private int limitMoney;
    //保底金额
    private int guarantee;
    private String information;
    private String name;
    private String json;
    private int chooseRate;
    private int chippedType=1;
    //宣言
    private String manifesto;
    private String url;
    private String lotid;
    private KProgressHUD hud;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_chipped;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();

    }

    private void setListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseRate=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chooseRate=0;
            }
        });
        buyMoneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    subscription=0;
                }else {
                    subscription=Integer.parseInt(s.toString());
                }
                payMoneyTv.setText(subscription+guarantee+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        minimumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)){
                    guarantee=0;
                }else {
                    guarantee=Integer.parseInt(s.toString());
                }
                payMoneyTv.setText(subscription+guarantee+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getData() {
        Intent intent=getIntent();
        totalMoney=(int)intent.getLongExtra("totalMoney",0);
        information=intent.getStringExtra("information");
        name=intent.getStringExtra("name");
        json=intent.getStringExtra("json");
        lotid=intent.getStringExtra("lotid");
        limitMoney= (int) Math.ceil(totalMoney/10.0);
        switch (lotid){
            case "ssq":
                url= Api.Double_Ball.POST_DOUBLE_BALL;
                break;
            case "dlt":
                url=Api.Super_Lotto.POST_DOUBLE_BALL;
                break;
            case "line3":
                url=Api.Line.order;
                break;
            case "line5":
                url=Api.Line.five;
                break;
            case "3d":
            case "3D":
                url=Api.Lottery3D.order;
                break;
            case "ftb":
                url=Api.FootBall_Api.Payment;
                break;
            case "FT005":
                url=Api.FootBall_Api.blend;
                break;
        }
    }

    private void initView() {
        tvTitle.setText(name+"合买");
        totalMoneyTv.setText("方案总额:"+totalMoney+"元");
        buyMoneyEt.setText(limitMoney+"");
        buyMoneyTv.setText(limitMoney+"元");
        moneyTv.setText(totalMoney+"");
        payMoneyTv.setText(limitMoney+"");
        subscription=limitMoney;
        switchmultibutton.setText("完全公开", "参与可见", "截止后可见").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                switch (position){
                    case 0:
                        chippedType=1;
                        break;
                    case 1:
                        chippedType=2;
                        break;
                    case 2:
                        chippedType=3;
                        break;

                }

            }
        });
        adapterRate = new ArrayAdapter<String>(this, R.layout.rate_spinner_item, rate);
        adapterRate.setDropDownViewResource(R.layout.rate_dropdown_item);
        spinner.setAdapter(adapterRate);
    }

    @OnClick({R.id.layout_top_back, R.id.question_mark_iv, R.id.buy_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.question_mark_iv:
                break;
            case R.id.buy_btn:
                String string = (String) SPUtils.get(this, Contacts.TOKEN, "");
                if (TextUtils.isEmpty(string)) {
                    ToastUtils.showToast(getString(R.string.login_please));
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    pay();
                }
                break;
        }
    }
    //下单
    private void pay() {
        //认购金额
        String buyMoney = buyMoneyEt.getText().toString().trim();
        if (TextUtils.isEmpty(buyMoney)){
            ToastUtils.showToast("请填写认购金额");
            return;
        }
        subscription=Integer.parseInt(buyMoney);
        if (subscription<limitMoney){
            ToastUtils.showToast("最少认购"+limitMoney+"元");
            buyMoneyEt.setText(limitMoney+"");
            return;
        }
        if (subscription>=totalMoney){
            ToastUtils.showToast("最多认购"+(totalMoney-1)+"元");
            buyMoneyEt.setText((totalMoney-1)+"");
            return;
        }
        //保底金额
        String minimum = minimumEt.getText().toString().trim();
        if (!TextUtils.isEmpty(minimum)){
            guarantee=Integer.parseInt(minimum);
        }
        if (guarantee>totalMoney-subscription){
            ToastUtils.showToast("认购+保底大于总额");
            minimumEt.setText(totalMoney-subscription+"");
            return;
        }
        //宣言
        manifesto = manifestoEt.getText().toString().trim();
        if (TextUtils.isEmpty(manifesto)){
            manifesto="想中奖，跟我来！";
        }

        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，检查网络后重试");
            return;
        }


        JSONObject jo = null;
        try {
            jo = new JSONObject(json);
            jo.put("is_together", 2);
            jo.put("baseline_money", guarantee);
            jo.put("cut", chooseRate);
            jo.put("declaration", manifesto);
            jo.put("set", chippedType);
            jo.put("quota",subscription+guarantee);
        } catch (JSONException e) {

        }

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setDimAmount(0.5f)
                .show();

        ApiService.GET_SERVICE(url, this, jo, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();
                try {
                    if (code == Api.Special_Code.notEnoughMoney) {
                        Intent intent = new Intent(ChippedActivity.this, PayActivity.class);
                        intent.putExtra("information", information);
                        intent.putExtra("money", data.getJSONObject("data").getString(Contacts.Order.MONEY));
                        intent.putExtra(Contacts.Order.ORDERID, data.getJSONObject("data").getString(Contacts.Order.ORDERID));
                        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
                    } else if (code == 0) {
                        try {
                            JSONObject data1 = data.getJSONObject("data");
                            Intent intent=new Intent(ChippedActivity.this, PaySucessActivity.class);
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

            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case Contacts.REQUEST_CODE_TO_PAY:
                    setResult(-1);
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
