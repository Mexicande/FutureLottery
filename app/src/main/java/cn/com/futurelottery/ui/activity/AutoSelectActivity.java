package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import cn.com.futurelottery.inter.OnRequestDataListener;
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
    private int Note = 5;

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
        initView();
        setListener();
    }

    private void initView() {
        tvPeriod.setText("期  ");
        bottomResultClearTv.setVisibility(View.GONE);
        bottomResultCountTv.setText("共"+5);
        bottomResultMoneyTv.setText(Note*1*1*2+"");
        bottomResultNextBtn.setText("确定");
    }

    private void setListener() {
        //每期机选
        AutoAmountZhu.setGoodsStorage(99);
        //每期投
        amountBei.setGoodsStorage(50);
        AutoAmountZhu.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                bottomResultMoneyTv.setText(amount*Note*amountBei.getValue()*2+"");
            }
        });
        amountBei.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                bottomResultMoneyTv.setText(amount*Note*AutoAmountZhu.getValue()*2+"");

            }
        });

    }


    @OnClick({R.id.layout_top_back, R.id.layout_five, R.id.layout_ten, R.id.layout_fifteen, R.id.bottom_result_next_btn})
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
                bottomResultCountTv.setText("共"+5);
                break;
            case R.id.layout_ten:
                Note = 10;
                setNumberDate();
                tvTen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivTen.setVisibility(View.VISIBLE);
                bottomResultCountTv.setText("共"+10);
                break;
            case R.id.layout_fifteen:
                Note = 15;
                setNumberDate();
                tvFifteen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivFifteen.setVisibility(View.VISIBLE);
                bottomResultCountTv.setText("共"+15);
                break;
            case R.id.bottom_result_next_btn:
                postSend();
                break;
            default:
                break;
        }
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
        ApiService.GET_SERVICE(Api.Double_Ball.POST_MULTI, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                ToastUtils.showToast("Ok,去支付");
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
    }

    /**
     * 连续买状态
     */
    private void setNumberDate() {
        bottomResultMoneyTv.setText(AutoAmountZhu.getValue()*Note*amountBei.getValue()*2+"");
        tvFive.setTextColor(getResources().getColor(R.color.black));
        tvFifteen.setTextColor(getResources().getColor(R.color.black));
        tvTen.setTextColor(getResources().getColor(R.color.black));
        ivTen.setVisibility(View.GONE);
        ivFive.setVisibility(View.GONE);
        ivFifteen.setVisibility(View.GONE);
    }
}
