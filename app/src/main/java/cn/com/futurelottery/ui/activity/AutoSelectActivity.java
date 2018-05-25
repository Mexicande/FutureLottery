package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.AmountView;

/**
 * @author apple
 *         多期机选
 */
public class AutoSelectActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_five)
    TextView tvFive;
    @BindView(R.id.iv_five)
    ImageView ivFive;
    @BindView(R.id.layout_five)
    RelativeLayout layoutFive;
    @BindView(R.id.tv_ten)
    TextView tvTen;
    @BindView(R.id.iv_ten)
    ImageView ivTen;
    @BindView(R.id.tv_fifteen)
    TextView tvFifteen;
    @BindView(R.id.iv_fifteen)
    ImageView ivFifteen;
    @BindView(R.id.layout_fifteen)
    RelativeLayout layoutFifteen;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_check_box)
    LinearLayout btCheckBox;
    @BindView(R.id.bottom_result_clear_tv)
    TextView bottomResultClearTv;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    @BindView(R.id.AutoAmount_zhu)
    AmountView AutoAmountZhu;
    @BindView(R.id.amount_bei)
    AmountView amountBei;
    @BindView(R.id.et_stop_money)
    EditText etStopMoney;
    @BindView(R.id.tv_period)
    TextView tvPeriod;
    @BindView(R.id.checkbox_plus)
    CheckBox checkboxPlus;
    @BindView(R.id.plus_iv)
    ImageView plusIv;
    @BindView(R.id.plus_ll)
    LinearLayout plusLl;
    @BindView(R.id.isstop_iv)
    ImageView isstopIv;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    private int Note = 5;
    //种类0双色球1大乐透
    private int intentType;
    //大乐透是否追加
    private int isAdd=2;
    //注数数
    private long zhu = 1;
    //倍数
    private long multiple = 1;
    //单价
    private int perMoney=2;

    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.machine_selection_title);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_auto_select;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();
    }

    private void getData() {
        Intent intent = getIntent();
        intentType = intent.getIntExtra("intentType", 0);
        if (intentType == 0) {
            plusLl.setVisibility(View.GONE);
        } else {
            plusLl.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        //停止金额开始不可输入
        etStopMoney.setFocusable(false);
        etStopMoney.setFocusableInTouchMode(false);
        //顶部右侧色问号
        questionMarkIv.setVisibility(View.VISIBLE);


        tvPeriod.setText("期  ");
        bottomResultClearTv.setVisibility(View.GONE);
        bottomResultCountTv.setText("共" + 5);
        bottomResultMoneyTv.setText(Note * 1 * 1 * 2 + "");
        bottomResultNextBtn.setText("确定");
    }

    private void setListener() {
        //注数
        AutoAmountZhu.setGoodsStorage(99);
        //倍数
        amountBei.setGoodsStorage(50);
        AutoAmountZhu.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                zhu=amount;
                bottomResultMoneyTv.setText(amount * Note * amountBei.getValue() *perMoney + "");
            }
        });
        amountBei.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                multiple=amount;
                bottomResultMoneyTv.setText(amount * Note * AutoAmountZhu.getValue() * perMoney + "");

            }
        });

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    etStopMoney.setFocusableInTouchMode(true);
                    etStopMoney.setFocusable(true);
                    etStopMoney.requestFocus();
                }else {
                    etStopMoney.setFocusable(false);
                    etStopMoney.setFocusableInTouchMode(false);
                }
            }
        });

        checkboxPlus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isAdd=1;
                    perMoney=3;
                }else {
                    isAdd=2;
                    perMoney=2;
                }
                bottomResultMoneyTv.setText(Note * zhu * multiple * perMoney + "");
            }
        });
    }


    @OnClick({R.id.layout_top_back, R.id.layout_five, R.id.layout_ten, R.id.layout_fifteen, R.id.bottom_result_next_btn,
            R.id.plus_iv, R.id.isstop_iv,R.id.question_mark_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.layout_five:
                Note = 5;
                setNumberDate();
                tvFive.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivFive.setVisibility(View.VISIBLE);
                bottomResultCountTv.setText("共" + 5);
                break;
            case R.id.layout_ten:
                Note = 10;
                setNumberDate();
                tvTen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivTen.setVisibility(View.VISIBLE);
                bottomResultCountTv.setText("共" + 10);
                break;
            case R.id.layout_fifteen:
                Note = 15;
                setNumberDate();
                tvFifteen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivFifteen.setVisibility(View.VISIBLE);
                bottomResultCountTv.setText("共" + 15);
                break;
            case R.id.bottom_result_next_btn:
                if (intentType == 0) {
                    postSend();
                } else {
                    postSendSL();
                }

                break;
            case R.id.plus_iv:
                showTipPlusDialog();
                break;
            case R.id.isstop_iv:
                showTipDialog();
                break;
            case R.id.question_mark_iv:

                break;
        }
    }

    private void showTipPlusDialog() {
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
        tvContent.setText("勾选后，没注追加一元，中奖金额增加50%");
        tvClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
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
        tvContent.setText("勾选后，当累计的中奖金额大于您设定的金额后，后续的期次将被撤销，资金返还到您的账户中。如不勾选，系统一直帮您购买所有的期次。");
        tvClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }

    private void postSend() {
        String stopMoney = etStopMoney.getText().toString();
        String money = bottomResultMoneyTv.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Contacts.NOTES, Note);
            jsonObject.put(Contacts.MONEY, money);
            jsonObject.put(Contacts.PERIODS, amountBei.getValue());
            jsonObject.put(Contacts.MULTIPLE, AutoAmountZhu.getValue());
            jsonObject.put("is_stop", checkbox.isChecked() ? 1 : 2);
            jsonObject.put("stop_money", checkbox.isChecked() ? stopMoney : "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * 跳转支付确认界面
         */
        Intent intent = new Intent(AutoSelectActivity.this, PayAffirmActivity.class);
        intent.putExtra("information","双色球"+" 多期机选");
        intent.putExtra("money", money);
        intent.putExtra("lotid", "ssqM");
        intent.putExtra("json", jsonObject.toString());
        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);

    }


    private void postSendSL() {
        String stopMoney = etStopMoney.getText().toString();
        String money = bottomResultMoneyTv.getText().toString();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Contacts.NOTES, Note);
            jsonObject.put(Contacts.MONEY, money);
            jsonObject.put(Contacts.PERIODS, amountBei.getValue());
            jsonObject.put(Contacts.MULTIPLE, AutoAmountZhu.getValue());
            jsonObject.put("is_stop", checkbox.isChecked() ? 1 : 2);
            jsonObject.put("stop_money", checkbox.isChecked() ? stopMoney : "");
            jsonObject.put(Contacts.IS_ADD, isAdd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * 跳转支付确认界面
         */
        Intent intent = new Intent(AutoSelectActivity.this, PayAffirmActivity.class);
        intent.putExtra("information","大乐透"+" 多期机选");
        intent.putExtra("money", money);
        intent.putExtra("lotid", "dltM");
        intent.putExtra("json", jsonObject.toString());
        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);

    }


    /**
     * 连续买状态
     */
    private void setNumberDate() {
        bottomResultMoneyTv.setText(AutoAmountZhu.getValue() * Note * amountBei.getValue() * 2 + "");
        tvFive.setTextColor(getResources().getColor(R.color.black));
        tvFifteen.setTextColor(getResources().getColor(R.color.black));
        tvTen.setTextColor(getResources().getColor(R.color.black));
        ivTen.setVisibility(View.GONE);
        ivFive.setVisibility(View.GONE);
        ivFifteen.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==-1){
            switch (requestCode){
                case Contacts.REQUEST_CODE_TO_PAY:
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
