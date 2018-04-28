package cn.com.futurelottery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpHeaders;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.utils.CaptchaTimeCount;
import cn.com.futurelottery.utils.CodeUtils;
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.editext.PowerfulEditText;
import cn.com.futurelottery.view.progressdialog.KProgressHUD;
import cn.com.futurelottery.view.supertextview.SuperButton;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.et_phone)
    PowerfulEditText etPhone;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.login_phone_r2)
    RelativeLayout loginPhoneR2;
    @BindView(R.id.login_btn)
    SuperButton loginBtn;
    @BindView(R.id.code_tv)
    TextView codeTv;
    private CaptchaTimeCount captchaTimeCount;
    private final int RESULT_CODE=-1;
    private AlertDialog alertDialog;
    private ImageView ivVerify;
    private EditText etVerify;
    private CodeUtils codeUtils;
    private String yanZhengCode;
    private String yanZhengResult;
    private String etYanZhengCode;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captchaTimeCount = new CaptchaTimeCount(Contacts.Times.MILLIS_IN_TOTAL, Contacts.Times.COUNT_DOWN_INTERVAL, codeTv, this);

        setListener();

    }

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.login);
    }


    @OnClick({R.id.code_tv, R.id.login_btn, R.id.login_weixin_iv, R.id.layout_top_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.code_tv:
                showVerifyDialog();
                break;
            case R.id.login_btn:
                String code = codeEt.getText().toString();
                String phone = etPhone.getText().toString();
                if (CommonUtil.checkPhone(phone)) {
                    setLogin(code);
                }
                break;
            case R.id.login_weixin_iv:
                break;
            default:
                break;

        }
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
                if (DeviceUtil.IsNetWork(LoginActivity.this) == false) {
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
        String phone = etPhone.getText().toString();
            if (CommonUtil.checkPhone(phone,true)) {
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
     * 登陆
     */
    private void setLogin(String code) {
        String phone = etPhone.getText().toString();
        if (!TextUtils.isEmpty(phone)) {
            if (CommonUtil.checkPhone(phone)) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mobile", phone);
                    jsonObject.put("code", code);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final KProgressHUD hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("提交中...")
                        .setDimAmount(0.5f)
                        .show();
                ApiService.GET_SERVICE(Api.Login.LOGIN, this, jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        try {
                            JSONObject jo = data.getJSONObject("data");
                            BaseApplication.getInstance().mobile = jo.getString("mobile");
                            BaseApplication.getInstance().token = jo.getString("token");
                            BaseApplication.getInstance().amount = jo.getString("amount");
                            BaseApplication.getInstance().userName = jo.getString("user_name");
                            BaseApplication.getInstance().integral = jo.getString("integral");
                            SPUtils.put(LoginActivity.this,Contacts.MOBILE,BaseApplication.getInstance().mobile);
                            SPUtils.put(LoginActivity.this,Contacts.TOKEN,BaseApplication.getInstance().token);
                            SPUtils.put(LoginActivity.this,Contacts.AMOUNT,BaseApplication.getInstance().amount);
                            SPUtils.put(LoginActivity.this,Contacts.NICK,BaseApplication.getInstance().userName);
                            SPUtils.put(LoginActivity.this,Contacts.INTEGRAL,BaseApplication.getInstance().integral);

                            // 发广播
                            Intent intent = new Intent();
                            intent.setAction(Contacts.INTENT_EXTRA_LOGIN_SUCESS);
                            sendBroadcast(intent);

                            //initokgo
                            HttpHeaders headers = new HttpHeaders();
                            headers.put("channel", BaseApplication.getInstance().channel);
                            headers.put("os", BaseApplication.getInstance().versionName);
                            headers.put(Contacts.TOKEN, BaseApplication.getInstance().token);
                            OkGo.getInstance()
                                    .init(getApplication())
                                    .setCacheMode(CacheMode.NO_CACHE)
                                    .addCommonHeaders(headers);


                            ToastUtils.showToast("登录成功");
                            setResult(RESULT_CODE);
                            finish();
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
        }
    }

    private void setListener() {

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11 && codeEt.getText().toString().length() == 4) {
                    loginBtn.setEnabled(true);
                    loginBtn.setUseShape();
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        codeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 4 && etPhone.getText().toString().length() == 11) {
                    loginBtn.setEnabled(true);
                    loginBtn.setUseShape();
                } else {
                    loginBtn.setEnabled(false);
                    loginBtn.setUseShape();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
