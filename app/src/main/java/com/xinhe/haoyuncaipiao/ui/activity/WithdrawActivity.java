package com.xinhe.haoyuncaipiao.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.BankInformation;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity {


    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.can_money_tv)
    TextView canMoneyTv;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.bank_tv)
    TextView bankTv;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    private ArrayList<BankInformation> bankInformations;
    private BankInformation bankInformation;
    private String money;
    private String canMoney;

    @Override
    protected void setTitle() {
        tvTitle.setText("提现");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData();
    }

    private void initView() {
        //设置字符过滤
        moneyEt.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(".") && dest.toString().length() == 0) {
                    return "0.";
                }
                if (dest.toString().contains(".")) {
                    int index = dest.toString().indexOf(".");
                    int length = dest.toString().substring(index).length();
                    if (length == 3) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    private void getData() {
        JSONObject jsonObject = new JSONObject();
        //验证请求
        ApiService.GET_SERVICE(Api.Withdraw.amount, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (code == 0) {
                        canMoney = data.getJSONObject("data").getString("amount");
                        canMoneyTv.setText(canMoney+"元");
                        Gson gson = new Gson();
                        Type bannerType = new TypeToken<ArrayList<BankInformation>>() {
                        }.getType();
                        bankInformations = (ArrayList<BankInformation>) gson.fromJson(data.getJSONObject("data").getJSONArray("bank").toString(), bannerType);
                        bankInformation = bankInformations.get(0);
                        bankTv.setText(bankInformation.getOpen_bank() + "(" + bankInformation.getBank_id() + ")");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    @OnClick({R.id.layout_top_back, R.id.submit_btn, R.id.all_money_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.submit_btn:
                submit();
                break;
            case R.id.all_money_tv:
                moneyEt.setText(canMoney);
                break;
        }
    }

    private void submit() {
        money = moneyEt.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastUtils.showToast("请输入提现金额");
            return;
        }
        float moneyF;
        try {
            moneyF = Float.parseFloat(money);
        } catch (NumberFormatException e) {
            ToastUtils.showToast("输入格式有误");
            return;
        }
        if (moneyF<10.00){
            ToastUtils.showToast("提现金额不足十元");
            return;
        }
        if (moneyF>Float.parseFloat(canMoney)){
            ToastUtils.showToast("提现金额大于可提现金额");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", bankInformation.getId());
            jsonObject.put("amount", money);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final KProgressHUD hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str))
                .setDimAmount(0.5f)
                .show();
        //验证请求
        ApiService.GET_SERVICE(Api.Withdraw.take, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (code == 0) {
                        showTipDialog();
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

    private void showTipDialog() {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        alertDialog1.setCancelable(false);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        window1.setContentView(R.layout.tip_dialog);
        TextView tvTip = (TextView) window1.findViewById(R.id.tips_tv);
        TextView tvContent = (TextView) window1.findViewById(R.id.tips_tv_content);
        TextView tvClick = (TextView) window1.findViewById(R.id.click_tv);
        tvTip.setText("提示");
        tvContent.setText("提现成功，资金将在1-3个工作日到账");
        tvClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(-1);
                finish();
                alertDialog1.dismiss();
            }
        });
    }
}
