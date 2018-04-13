package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.utils.StatusBarUtil;
import cn.com.futurelottery.view.AmountView;

/**
 * @author apple
 *  多期机选
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
    @BindView(R.id.one_AutoAmount)
    AmountView oneAutoAmount;
    @BindView(R.id.amount_view)
    AmountView amountView;
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

    private void setListener() {
        //每期机选
        oneAutoAmount.setGoodsStorage(99);
        //每期投
        amountView.setGoodsStorage(99);
        oneAutoAmount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

            }
        });
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

            }
        });
    }

    private void initView() {
        bottomResultClearTv.setVisibility(View.GONE);
    }



    @OnClick({R.id.layout_top_back, R.id.layout_five, R.id.layout_ten, R.id.layout_fifteen, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.layout_five:
                setNumberDate();
                tvFive.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivFive.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_ten:
                setNumberDate();
                tvTen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivTen.setVisibility(View.VISIBLE);
                break;
            case R.id.layout_fifteen:
                setNumberDate();
                tvFifteen.setTextColor(getResources().getColor(R.color.colorPrimary));
                ivFifteen.setVisibility(View.VISIBLE);
                break;
            case R.id.bottom_result_next_btn:
                break;
            default:
                break;
        }
    }

    /**
     * 连续买状态
     */
    private void setNumberDate() {
        tvFive.setTextColor(getResources().getColor(R.color.black));
        tvFifteen.setTextColor(getResources().getColor(R.color.black));
        tvTen.setTextColor(getResources().getColor(R.color.black));
        ivTen.setVisibility(View.GONE);
        ivFive.setVisibility(View.GONE);
        ivFifteen.setVisibility(View.GONE);
    }
}
