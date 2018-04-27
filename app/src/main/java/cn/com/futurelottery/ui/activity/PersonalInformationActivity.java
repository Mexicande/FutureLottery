package cn.com.futurelottery.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.view.editext.PowerfulEditText;

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
    private CaptchaTimeCount captchaTimeCount;

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
        initView();
    }

    private void initView() {
        captchaTimeCount = new CaptchaTimeCount(Contacts.Times.MILLIS_IN_TOTAL, Contacts.Times.COUNT_DOWN_INTERVAL, codeTv, this);
    }

    @OnClick({R.id.layout_top_back, R.id.code_tv, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                break;
            case R.id.code_tv:
                break;
            case R.id.submit_btn:
                break;
        }
    }



    /**
     * 验证码倒计时
     *
     */
    public class CaptchaTimeCount extends CountDownTimer {
        private TextView validate_btn;
        private Context context;
        /**
         * 验证码倒计时
         * @param millisInFuture
         * @param countDownInterval
         * @param validate_btn 点击的按钮
         * @param context
         */
        public CaptchaTimeCount(long millisInFuture, long countDownInterval, TextView validate_btn, Context context) {
            super(millisInFuture, countDownInterval);
            this.validate_btn=validate_btn;
            this.context=context;
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
        public void reset(){
            validate_btn.setClickable(true);
            validate_btn.setText(R.string.validate_btn_hint);
            validate_btn.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
            validate_btn.setBackgroundResource(R.drawable.money_check);
        }
    }

}
