package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.model.DoubleBall;
import com.xinhe.haoyuncaipiao.ui.activity.chipped.ChippedActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.ChooseBallPaymentAdapter;
import com.xinhe.haoyuncaipiao.ui.dialog.QuitDialogFragment;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.RandomMadeBall;
import com.xinhe.haoyuncaipiao.utils.SPUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.AmountView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.listener.SaveDialogListener;
import com.xinhe.haoyuncaipiao.view.DashlineItemDivider;

public class ChooseBallPaymentActivity extends BaseActivity implements SaveDialogListener {

    @BindView(R.id.choose_self_ll)
    LinearLayout chooseSelfLl;
    @BindView(R.id.choose_random_ll)
    LinearLayout chooseRandomLl;
    @BindView(R.id.choose_clear_ll)
    LinearLayout chooseClearLl;
    @BindView(R.id.choose_null_tv)
    TextView chooseNullTv;
    @BindView(R.id.choose_zhu_rv)
    RecyclerView chooseZhuRv;
    @BindView(R.id.choose_ball_ll)
    LinearLayout chooseBallLl;
    @BindView(R.id.periods_count)
    AmountView periodsCount;
    @BindView(R.id.multiply_count)
    AmountView multiplyCount;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_btn)
    Button bottomResultBtn;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_check_box)
    LinearLayout btCheckBox;
    @BindView(R.id.accumulative_ll)
    LinearLayout accumulativeLl;
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.right_tv)
    TextView rightTv;
    private ArrayList<DoubleBall> balls = new ArrayList<>();
    private ChooseBallPaymentAdapter adapter;
    private long zhushu = 0;
    //期数
    private long periods = 1;
    //倍数
    private long multiple = 1;
    //是否通知追期1=是 2=否
    String is_stop = "2";
    //停止追期金额
    String stop_money = "0";
    private String phase = "2018032";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();
    }

    private void setListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                zhushu = zhushu - balls.get(position).getZhushu();
                balls.remove(position);
                show();
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        periodsCount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                periods = amount;
                if (amount <= 1) {
                    accumulativeLl.setVisibility(View.GONE);
                    checkbox.setChecked(false);
                } else {
                    accumulativeLl.setVisibility(View.VISIBLE);
                }
                show();
            }
        });
        multiplyCount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                multiple = amount;
                show();
            }
        });
    }

    //获取数据
    private void getData() {
        //本地数据
        if (SPUtil.contains(this, Contacts.doubleBallSave)) {
            ArrayList<DoubleBall> getBalls = (ArrayList<DoubleBall>) ((Serializable) SPUtil.getList(this, Contacts.doubleBallSave));
            balls.addAll(0, getBalls);
            for (int i = 0; i < getBalls.size(); i++) {
                DoubleBall db = getBalls.get(i);
                zhushu = zhushu + db.getZhushu();
            }
        }


        Intent intent = getIntent();
        phase = intent.getStringExtra("phase");
        ArrayList<DoubleBall> getBalls = (ArrayList<DoubleBall>) intent.getSerializableExtra("balls");
        balls.addAll(0, getBalls);
        for (int i = 0; i < getBalls.size(); i++) {
            DoubleBall db = getBalls.get(i);
            zhushu = zhushu + db.getZhushu();
        }
        show();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_choose_ball_payment;
    }


    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.double_title);
    }

    @OnClick({R.id.choose_self_ll, R.id.choose_random_ll, R.id.choose_clear_ll, R.id.bottom_result_btn, R.id.layout_top_back, R.id.tip_iv,R.id.right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_self_ll:
                Intent intent = new Intent(this, DoubleBallActivity.class);
                intent.putExtra("data", 1);
                startActivityForResult(intent, Contacts.REQUEST_CODE_PAYMENT_TO_CHOOSE);
                break;
            case R.id.choose_random_ll:
                randomChoose();
                break;
            case R.id.choose_clear_ll:
                clearList();
                break;
            case R.id.bottom_result_btn:
                String string = (String) SPUtils.get(this, Contacts.TOKEN, "");
                if (TextUtils.isEmpty(string)) {
                    ToastUtils.showToast(getString(R.string.login_please));
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    pay();
                }
                break;
            case R.id.layout_top_back:
                if (balls.size() > 0) {
                    showMyDialog();
                } else {
                    finish();
                }
                break;
            case R.id.tip_iv:
                showTipDialog("什么是中奖后停止追号","勾选后，当您的追号方案某一期中奖，则后续的追号订单将被撤销，资金返还到您的账户中。如不勾选，系统一直帮您购买所有的追号投注订单。");
                break;
            case R.id.right_tv:
                if (periods>1){
                    ToastUtils.showToast("合买只能为一期");
                    return;
                }
                String token = (String) SPUtils.get(this, Contacts.TOKEN, "");
                if (TextUtils.isEmpty(token)) {
                    ToastUtils.showToast(getString(R.string.login_please));
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    if (zhushu * periods * multiple * 2<8){
                        showTipDialog("提示","合买方案金额不能小于8");
                    }else {
                        chipped();
                    }
                }
                break;
        }
    }

    //跳转合买
    private void chipped() {
        is_stop = checkbox.isChecked() ? "1" : "2";

        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        for (int i = 0; i < balls.size(); i++) {
            DoubleBall ball = balls.get(i);
            Map<String, String> map = new HashMap<>();
            if (ball.getType() > 1) {
                map.put(Contacts.RED, ball.getDan());
                map.put(Contacts.TUO, ball.getRed());
                map.put(Contacts.TYPE, "2");
            } else {
                map.put(Contacts.RED, ball.getRed());
                map.put(Contacts.TYPE, "1");
            }

            map.put(Contacts.BLU, ball.getBlu());
            map.put(Contacts.MONEY, ball.getZhushu() * 2 * periods * multiple + "");
            map.put(Contacts.NOTES, ball.getZhushu() + "");

            map.put(Contacts.PERIODS, periods + "");
            map.put(Contacts.MULTIPLE, multiple + "");

            JSONObject jsonObject = new JSONObject(map);
            ja.put(jsonObject);
        }
        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * 2 + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, stop_money);
        } catch (JSONException e) {

        }
        Intent intent=new Intent(ChooseBallPaymentActivity.this,ChippedActivity.class);
        intent.putExtra("totalMoney",zhushu * periods * multiple * 2 );
        intent.putExtra("information", "双色球 第" + phase + "期");
        intent.putExtra("name", "双色球");
        intent.putExtra("json", jo.toString());
        intent.putExtra("lotid", "ssq");
        startActivity(intent);
    }

    private void showTipDialog(String title,String content) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        alertDialog1.setCancelable(false);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        window1.setContentView(R.layout.tip_dialog);
        TextView tvTip = (TextView) window1.findViewById(R.id.tips_tv);
        TextView tvContent = (TextView) window1.findViewById(R.id.tips_tv_content);
        TextView tvClick = (TextView) window1.findViewById(R.id.click_tv);
        tvTip.setText(title);
        tvContent.setText(content);
        tvClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }

    private void showMyDialog() {
        QuitDialogFragment qt = new QuitDialogFragment();
        qt.show(getSupportFragmentManager(), "是否保存");


    }

    //清空列表
    private void clearList() {
        balls.clear();
        zhushu = 0;
        show();
        adapter.notifyDataSetChanged();
    }

    //付款
    private void pay() {
        if (zhushu <= 0) {
            ToastUtils.showToast("至少选一注");
            return;
        }
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，检查网络后重试");
            return;
        }

        is_stop = checkbox.isChecked() ? "1" : "2";

        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        for (int i = 0; i < balls.size(); i++) {
            DoubleBall ball = balls.get(i);
            Map<String, String> map = new HashMap<>();
            if (ball.getType() > 1) {
                map.put(Contacts.RED, ball.getDan());
                map.put(Contacts.TUO, ball.getRed());
                map.put(Contacts.TYPE, "2");
            } else {
                map.put(Contacts.RED, ball.getRed());
                map.put(Contacts.TYPE, "1");
            }

            map.put(Contacts.BLU, ball.getBlu());
            map.put(Contacts.MONEY, ball.getZhushu() * 2 * periods * multiple + "");
            map.put(Contacts.NOTES, ball.getZhushu() + "");

            map.put(Contacts.PERIODS, periods + "");
            map.put(Contacts.MULTIPLE, multiple + "");

            JSONObject jsonObject = new JSONObject(map);
            ja.put(jsonObject);
        }
        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * 2 + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, stop_money);
        } catch (JSONException e) {

        }

        Intent intent = new Intent(ChooseBallPaymentActivity.this, PayAffirmActivity.class);
        intent.putExtra("information", "双色球 第" + phase + "期");
        intent.putExtra("money", zhushu * periods * multiple * 2+"");
        intent.putExtra("lotid", "ssq");
        intent.putExtra("json", jo.toString());
        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);

    }

    //机选一注
    private void randomChoose() {
        ArrayList<String> randomRed = RandomMadeBall.getManyBall(33, 6);
        ArrayList<String> randomBlue = RandomMadeBall.getOneBall(16);
        DoubleBall db = new DoubleBall();
        String red = "";
        String blue = "";
        for (int i = 0; i < randomRed.size(); i++) {
            int number = Integer.parseInt(randomRed.get(i)) + 1;
            if (i == 0) {
                red = red + ((number < 10) ? ("0" + number) : number);
            } else {
                red = red + "," + ((number < 10) ? ("0" + number) : number);
            }
        }
        int number = Integer.parseInt(randomBlue.get(0)) + 1;
        blue = "" + ((number < 10) ? ("0" + number) : number);
        db.setType(0);
        db.setRed(red);
        db.setBlu(blue);
        db.setZhushu(1);
        db.setMoney(2);
        zhushu += 1;
        balls.add(0, db);
        adapter.notifyDataSetChanged();
        //显示
        show();
    }

    private void show() {
        if (balls.size() > 0) {
            chooseZhuRv.setVisibility(View.VISIBLE);
            chooseNullTv.setVisibility(View.GONE);
        } else {
            chooseZhuRv.setVisibility(View.GONE);
            chooseNullTv.setVisibility(View.VISIBLE);
        }
        bottomResultCountTv.setText(zhushu + "注" + periods + "期" + multiple + "倍");
        bottomResultMoneyTv.setText(zhushu * periods * multiple * 2 + "");
    }


    private void initView() {
        //合买
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("发起合买");
        //每期机选
        periodsCount.setGoodsStorage(15);
        //每期投
        multiplyCount.setGoodsStorage(50);


        adapter = new ChooseBallPaymentAdapter(balls);
        chooseZhuRv.setLayoutManager(new LinearLayoutManager(this));
        chooseZhuRv.setAdapter(adapter);
        //设置分割线
        chooseZhuRv.addItemDecoration(new DashlineItemDivider(this));
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        adapter.addFooterView(view);
    }


    @Override
    public void saveDate() {
        SPUtil.putList(this, Contacts.doubleBallSave, balls);
        finish();
    }

    @Override
    public void clearDate() {
        SPUtil.remove(this, Contacts.doubleBallSave);
        finish();
    }


    @Override
    public void onBackPressed() {
        if (balls.size() > 0) {
            showMyDialog();
        } else {
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case Contacts.REQUEST_CODE_PAYMENT_TO_CHOOSE:
                    ArrayList<DoubleBall> getBalls = (ArrayList<DoubleBall>) data.getSerializableExtra("balls");
                    phase = data.getStringExtra("phase");
                    balls.addAll(0, getBalls);
                    for (int i = 0; i < getBalls.size(); i++) {
                        DoubleBall db = getBalls.get(i);
                        zhushu = zhushu + db.getZhushu();
                    }
                    adapter.notifyDataSetChanged();
                    show();
                    break;
                case Contacts.REQUEST_CODE_TO_PAY:
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
