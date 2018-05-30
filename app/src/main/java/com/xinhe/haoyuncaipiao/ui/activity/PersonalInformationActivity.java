package com.xinhe.haoyuncaipiao.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.utils.BankUtils;
import com.xinhe.haoyuncaipiao.utils.CodeUtils;
import com.xinhe.haoyuncaipiao.utils.CommonUtil;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.IDCardCheckUtil;
import com.xinhe.haoyuncaipiao.utils.IsChineseUtil;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.editext.PowerfulEditText;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 绑定银行卡
 */
public class PersonalInformationActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.name_et)
    PowerfulEditText nameEt;
    @BindView(R.id.IDcard_et)
    PowerfulEditText IDcardEt;
    @BindView(R.id.bank_card_et)
    PowerfulEditText bankCardEt;
    @BindView(R.id.bank_et)
    EditText bankEt;
    @BindView(R.id.phone_et)
    PowerfulEditText phoneEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.code_tv)
    TextView codeTv;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.city_et)
    EditText cityEt;
    @BindView(R.id.bank_tv)
    TextView bankTv;
    private CaptchaTimeCount captchaTimeCount;
    private String phone;
    private AlertDialog alertDialog;
    private ImageView ivVerify;
    private EditText etVerify;
    private CodeUtils codeUtils;
    private String yanZhengCode;
    private String yanZhengResult;
    private String name;
    private String myID;
    private String bankCard;
    private String bank;
    private String etYanZhengCode;
    private String code;
    private KProgressHUD hud;
    private String city;
    private final int WITHDRAW_REQUEST_CODE = 1001;
    private ArrayList<String> banks = new ArrayList<>();
    private String bankZhi;

    @Override
    protected void setTitle() {
        tvTitle.setText("绑定提现银行卡");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBanks();
        initView();
    }

    //获取银行列表
    private void getBanks() {
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，请检查网络后重试");
            return;
        }
        //验证请求
        ApiService.GET_SERVICE(Api.Withdraw.opening, this, new JSONObject(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    Type bankType = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    banks = gson.fromJson(data.getJSONArray("data").toString(), bankType);
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

    private void initView() {
        captchaTimeCount = new CaptchaTimeCount(Contacts.Times.MILLIS_IN_TOTAL, Contacts.Times.COUNT_DOWN_INTERVAL, codeTv, this);
    }

    @OnClick({R.id.layout_top_back, R.id.code_tv, R.id.submit_btn, R.id.bank_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.code_tv:
                phone = phoneEt.getText().toString();
                if (CommonUtil.checkPhone(phone, true)) {
                    showVerifyDialog();
                }
                break;
            case R.id.submit_btn:
                submit();
                break;
            case R.id.bank_ll:
                chooseBank();
                break;
        }
    }

    private void submit() {
        //姓名
        name = nameEt.getText().toString().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showToast("请输入您的姓名");
            return;
        }
        if (!IsChineseUtil.checkNameChese(name)) {
            ToastUtils.showToast("请输入中文姓名");
            return;
        }
        if (name.length() < 2) {
            ToastUtils.showToast("姓名至少输入两个字");
            return;
        }
        //身份证号
        myID = IDcardEt.getText().toString().trim();
        if (TextUtils.isEmpty(myID)) {
            ToastUtils.showToast("请输入您的身份证号");
            return;
        }
        String chekID = "id";
        try {
            chekID = IDCardCheckUtil.IDCardValidate(myID);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!"".equals(chekID)) {
            ToastUtils.showToast("身份证号输入有误");
            return;
        }
        //银行卡号
        bankCard = bankCardEt.getText().toString().trim();
        if (TextUtils.isEmpty(bankCard)) {
            Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_LONG).show();
            return;
        }
        if (!BankUtils.checkBankCard(bankCard)) {
            Toast.makeText(this, "卡号输入有误", Toast.LENGTH_LONG).show();
            return;
        }
        //银行
        bank = bankTv.getText().toString().trim();
        if (TextUtils.isEmpty(bank)) {
            ToastUtils.showToast("请选择开户银行");
            return;
        }
        //支行
        bankZhi = bankEt.getText().toString().trim();
        if (TextUtils.isEmpty(bankZhi)) {
            ToastUtils.showToast("请输入开户支行");
            return;
        }
        //银行所在地
        city = cityEt.getText().toString().trim();
        if (TextUtils.isEmpty(bank)) {
            ToastUtils.showToast("请输入开户银行所在地");
            return;
        }
        //手机号
        phone = phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入手机号");
            return;
        }
        //验证码
        code = codeEt.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast("请输入验证码");
            return;
        }
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str))
                .setDimAmount(0.5f)
                .show();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userphone", phone);
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //验证请求
        ApiService.GET_SERVICE(Api.Withdraw.checkCode, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (code == 0) {
                    submitInformation();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });

    }

    //提交信息
    private void submitInformation() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("accNo", bankCard);
            jsonObject.put("idCard", myID);
            jsonObject.put("customerNm", name);
            jsonObject.put("phoneNo", phone);
            jsonObject.put("location", city);
            jsonObject.put("open_bank", bank+"-"+bankZhi);
            jsonObject.put("cardholder", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Api.Withdraw.idcard, PersonalInformationActivity.this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                if (code == 0) {
                    Intent intent = new Intent(PersonalInformationActivity.this, WithdrawActivity.class);
                    startActivityForResult(intent, WITHDRAW_REQUEST_CODE);
                    finish();
                }
                if (null != hud) {
                    hud.dismiss();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                if (null != hud) {
                    hud.dismiss();
                }
            }
        });
    }

    /*
     *
     *
     * 弹出对话框的步骤 1.创建alertDialog的builder. 2.要给builder设置属行, 对话框的内容,样式,按钮
     * 3.通过builder 创建个对话框 4.对话框show()出来
     */
    protected void showVerifyDialog() {
        alertDialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        /**
         * 下面三行可以让对话框里的输入框可以输入
         */
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.verify, null);
        alertDialog.setView(layout);

        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.verify);
        ivVerify = (ImageView) window.findViewById(R.id.verify_iv);
        etVerify = (EditText) window.findViewById(R.id.verify_et);
        Button btn = (Button) window.findViewById(R.id.verify_btn);
        ivVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initYanzheng();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeviceUtil.IsNetWork(PersonalInformationActivity.this) == false) {
                    ToastUtils.showToast("网络未连接");
                    return;
                }
                etYanZhengCode = etVerify.getText().toString().trim();

                if (TextUtils.isEmpty(etYanZhengCode)) {
                    ToastUtils.showToast("请输入图片里的结果");
                    return;
                }

                if (!yanZhengResult.equals(etYanZhengCode)) {
                    ToastUtils.showToast("图片结果输入有误");
                    return;
                }
                //对话框隐藏
                alertDialog.dismiss();
                getCode();
            }
        });
        initYanzheng();
    }

    private void initYanzheng() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        ivVerify.setImageBitmap(bitmap);
        yanZhengCode = codeUtils.getCode();
        yanZhengResult = codeUtils.getResult() + "";
    }


    /**
     * 验证码
     */
    private void getCode() {
        phone = phoneEt.getText().toString();
        if (CommonUtil.checkPhone(phone, true)) {
            captchaTimeCount.start();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mobile", phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ApiService.GET_SERVICE(Api.Login.GET_CODE, this, jsonObject, new OnRequestDataListener() {
                @Override
                public void requestSuccess(int code, JSONObject data) {
                    ToastUtils.showToast("发送成功");
                }

                @Override
                public void requestFailure(int code, String msg) {
                    ToastUtils.showToast(msg);
                    captchaTimeCount.onFinish();
                }
            });
        }
    }


    /**
     * 验证码倒计时
     */
    public class CaptchaTimeCount extends CountDownTimer {
        private TextView validate_btn;
        private Context context;

        /**
         * 验证码倒计时
         *
         * @param millisInFuture
         * @param countDownInterval
         * @param validate_btn      点击的按钮
         * @param context
         */
        public CaptchaTimeCount(long millisInFuture, long countDownInterval, TextView validate_btn, Context context) {
            super(millisInFuture, countDownInterval);
            this.validate_btn = validate_btn;
            this.context = context;
        }

        @Override//开始
        public void onTick(long millisUntilFinished) {
            validate_btn.setClickable(false);
            validate_btn.setText(millisUntilFinished / 1000 + "s后重发");//   改为  60秒倒计时
            validate_btn.setTextColor(getResources().getColorStateList(R.color.color_999)); //颜色
            validate_btn.setBackgroundResource(R.drawable.money_uncheck);
        }

        @Override
        public void onFinish() {

            validate_btn.setClickable(true);
            validate_btn.setText(R.string.captcha_btn_resend);//@color/color_63b953
            validate_btn.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
            validate_btn.setBackgroundResource(R.drawable.money_check);
        }

        /**
         * 重置
         */
        public void reset() {
            validate_btn.setClickable(true);
            validate_btn.setText(R.string.validate_btn_hint);
            validate_btn.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
            validate_btn.setBackgroundResource(R.drawable.money_check);
        }
    }

    /**
     * 选择银行
     */
    private void chooseBank() {
        int color = getResources().getColor(R.color.colorPrimary);
        OptionsPickerBuilder opb = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                bankTv.setText(banks.get(options1));
            }
        })

                .setTitleText("开户银行")
                .setTitleColor(color)//标题文字颜色
                .setDividerColor(color)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setSubmitColor(color)
                .setCancelColor(color);
        OptionsPickerView<String> pvOptions = opb.build();
        pvOptions.setPicker(banks);//一级选择器
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case WITHDRAW_REQUEST_CODE:
                    setResult(-1);
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
