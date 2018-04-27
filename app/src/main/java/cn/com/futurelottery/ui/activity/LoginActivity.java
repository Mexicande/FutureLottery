package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import cn.com.futurelottery.utils.CommonUtil;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.editext.PowerfulEditText;
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
                getCode();
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
                ApiService.GET_SERVICE(Api.Login.LOGIN, this, jsonObject, new OnRequestDataListener() {
                    @Override
                    public void requestSuccess(int code, JSONObject data) {
                        try {
                            BaseApplication.getInstance().mobile = data.getString("mobile");
                            BaseApplication.getInstance().token = data.getString("token");
                            BaseApplication.getInstance().amount = data.getString("amount");
                            BaseApplication.getInstance().userName = data.getString("user_name");
                            BaseApplication.getInstance().integral = data.getString("integral");
                            SPUtils.put(LoginActivity.this,Contacts.MOBILE,BaseApplication.getInstance().mobile);
                            SPUtils.put(LoginActivity.this,Contacts.TOKEN,BaseApplication.getInstance().token);
                            SPUtils.put(LoginActivity.this,Contacts.AMOUNT,BaseApplication.getInstance().amount);
                            SPUtils.put(LoginActivity.this,Contacts.NICK,BaseApplication.getInstance().userName);
                            SPUtils.put(LoginActivity.this,Contacts.INTEGRAL,BaseApplication.getInstance().integral);


                            //initokgo
                            HttpHeaders headers = new HttpHeaders();
                            headers.put("channel", BaseApplication.getInstance().channel);
                            headers.put("os", BaseApplication.getInstance().versionName);
                            headers.put(Contacts.TOKEN, BaseApplication.getInstance().token);
                            OkGo.getInstance()
                                    .init(getApplication())
                                    .setCacheMode(CacheMode.NO_CACHE)
                                    .addCommonHeaders(headers);


                            setResult(RESULT_CODE);
                            finish();
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
