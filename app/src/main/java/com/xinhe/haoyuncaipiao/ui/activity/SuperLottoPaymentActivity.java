package com.xinhe.haoyuncaipiao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.model.SuperLotto;
import com.xinhe.haoyuncaipiao.ui.activity.chipped.ChippedActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.SuperLottoPaymentAdapter;
import com.xinhe.haoyuncaipiao.ui.dialog.QuitDialogFragment;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.RandomMadeBall;
import com.xinhe.haoyuncaipiao.utils.SPUtil;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

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
import com.xinhe.haoyuncaipiao.view.AmountView;
import com.xinhe.haoyuncaipiao.view.DashlineItemDivider;

public class SuperLottoPaymentActivity extends BaseActivity implements SaveDialogListener {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.bt_check_box)
    LinearLayout btCheckBox;
    @BindView(R.id.accumulative_ll)
    LinearLayout accumulativeLl;
    @BindView(R.id.checkbox_plus)
    CheckBox checkboxPlus;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_btn)
    Button bottomResultBtn;
    @BindView(R.id.right_tv)
    TextView rightTv;
    private ArrayList<SuperLotto> balls = new ArrayList<>();
    private SuperLottoPaymentAdapter adapter;
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
    //单价
    private int perMoney = 2;


    @Override
    protected void setTitle() {
        tvTitle.setText(R.string.title_activity_super_lotto);
    }


    @Override
    public int getLayoutResource() {
        return R.layout.activity_super_lotto_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();
    }

    private void setListener() {
        //监听子控件删除
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                zhushu = zhushu - balls.get(position).getZhushu();
                balls.remove(position);
                show();
                adapter.notifyDataSetChanged();
            }
        });
        //item监听
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
        //追加
        checkboxPlus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    perMoney = 3;
                } else {
                    perMoney = 2;
                }
                adapter.updatePermoney(perMoney);
                show();
            }
        });
    }

    //获取数据
    private void getData() {
        //本地数据
        if (SPUtil.contains(this, Contacts.superLottoSave)) {
            ArrayList<SuperLotto> getBalls = (ArrayList<SuperLotto>) ((Serializable) SPUtil.getList(this, Contacts.superLottoSave));
            balls.addAll(0, getBalls);
            for (int i = 0; i < getBalls.size(); i++) {
                SuperLotto db = getBalls.get(i);
                zhushu = zhushu + db.getZhushu();
            }
        }


        Intent intent = getIntent();
        phase = intent.getStringExtra("phase");
        ArrayList<SuperLotto> getBalls = (ArrayList<SuperLotto>) intent.getSerializableExtra("balls");
        balls.addAll(0, getBalls);
        for (int i = 0; i < getBalls.size(); i++) {
            SuperLotto db = getBalls.get(i);
            zhushu = zhushu + db.getZhushu();
        }
        show();
    }


    @OnClick({R.id.choose_self_ll, R.id.choose_random_ll, R.id.choose_clear_ll, R.id.bottom_result_btn, R.id.layout_top_back, R.id.tip_iv,R.id.right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_self_ll:
                Intent intent = new Intent(this, SuperLottoActivity.class);
                intent.putExtra("data", 1);
                startActivityForResult(intent, Contacts.REQUEST_CODE_SL_PAYMENT_TO_CHOOSE);
                break;
            case R.id.choose_random_ll:
                randomChoose();
                break;
            case R.id.choose_clear_ll:
                clearList();
                break;
            case R.id.bottom_result_btn:
                boolean chip = SPUtil.contains(this, "chip");

                if (!chip) {
                    String string = (String) SPUtils.get(this, Contacts.TOKEN, "");
                    if (TextUtils.isEmpty(string)) {
                        ToastUtils.showToast(getString(R.string.login_please));
                        ActivityUtils.startActivity(LoginActivity.class);
                    } else {
                        pay();
                    }
                }else {
                    if (periods>1){
                        ToastUtils.showToast("合买只能为一期");
                        return;
                    }
                    String token = (String) SPUtils.get(this, Contacts.TOKEN, "");
                    if (TextUtils.isEmpty(token)) {
                        ToastUtils.showToast(getString(R.string.login_please));
                        ActivityUtils.startActivity(LoginActivity.class);
                    } else {
                        if (zhushu * periods * multiple * perMoney<8){
                            showTipDialog("提示","合买方案金额不能小于8");
                        }else {
                            chipped();
                        }
                    }
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
                    if (zhushu * periods * multiple * perMoney<8){
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
            SuperLotto ball = balls.get(i);
            Map<String, String> map = new HashMap<>();
            if (ball.getType() > 1) {
                map.put(Contacts.RED, ball.getDanRed());
                map.put(Contacts.RED_TUO, ball.getRed());
                map.put(Contacts.BLU, ball.getDanBlu());
                map.put(Contacts.BLUE_TUO, ball.getBlu());
                map.put(Contacts.TYPE, "2");
            } else {
                map.put(Contacts.RED, ball.getRed());
                map.put(Contacts.BLU, ball.getBlu());
                map.put(Contacts.TYPE, "1");
            }

            map.put(Contacts.MONEY, ball.getZhushu() * perMoney * periods * multiple + "");
            map.put(Contacts.NOTES, ball.getZhushu() + "");

            map.put(Contacts.PERIODS, periods + "");
            map.put(Contacts.MULTIPLE, multiple + "");


            JSONObject jsonObject = new JSONObject(map);
            ja.put(jsonObject);
        }
        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * perMoney + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, stop_money);
            if (perMoney == 2) {
                jo.put(Contacts.IS_ADD, 2);
            } else {
                jo.put(Contacts.IS_ADD, 1);
            }
        } catch (JSONException e) {

        }
        Intent intent=new Intent(SuperLottoPaymentActivity.this,ChippedActivity.class);
        intent.putExtra("totalMoney",zhushu * periods * multiple * perMoney );
        intent.putExtra("information", "大乐透 第" + phase + "期");
        intent.putExtra("name", "大乐透");
        intent.putExtra("json", jo.toString());
        intent.putExtra("lotid", "dlt");
        startActivity(intent);
    }


    private void showTipDialog(String title,String content) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        alertDialog1.setCancelable(false);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        if (window1 != null) {
            window1.setContentView(R.layout.tip_dialog);
            TextView tvTip = window1.findViewById(R.id.tips_tv);
            TextView tvContent = window1.findViewById(R.id.tips_tv_content);
            TextView tvClick = window1.findViewById(R.id.click_tv);
            tvTip.setText(title);
            tvContent.setText(content);
            tvClick.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    alertDialog1.dismiss();
                }
            });
        }
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
            SuperLotto ball = balls.get(i);
            Map<String, String> map = new HashMap<>();
            if (ball.getType() > 1) {
                map.put(Contacts.RED, ball.getDanRed());
                map.put(Contacts.RED_TUO, ball.getRed());
                map.put(Contacts.BLU, ball.getDanBlu());
                map.put(Contacts.BLUE_TUO, ball.getBlu());
                map.put(Contacts.TYPE, "2");
            } else {
                map.put(Contacts.RED, ball.getRed());
                map.put(Contacts.BLU, ball.getBlu());
                map.put(Contacts.TYPE, "1");
            }

            map.put(Contacts.MONEY, ball.getZhushu() * perMoney * periods * multiple + "");
            map.put(Contacts.NOTES, ball.getZhushu() + "");

            map.put(Contacts.PERIODS, periods + "");
            map.put(Contacts.MULTIPLE, multiple + "");


            JSONObject jsonObject = new JSONObject(map);
            ja.put(jsonObject);
        }
        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * perMoney + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, stop_money);
            if (perMoney == 2) {
                jo.put(Contacts.IS_ADD, 2);
            } else {
                jo.put(Contacts.IS_ADD, 1);
            }
        } catch (JSONException e) {

        }

        Intent intent = new Intent(SuperLottoPaymentActivity.this, PayAffirmActivity.class);
        intent.putExtra("information", "大乐透 第" + phase + "期");
        intent.putExtra("money", zhushu * periods * multiple * perMoney+"");
        intent.putExtra("lotid", "dlt");
        intent.putExtra("json", jo.toString());
        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);

    }

    //机选一注
    private void randomChoose() {
        ArrayList<String> randomRed = RandomMadeBall.getManyBall(35, 5);
        ArrayList<String> randomBlue = RandomMadeBall.getManyBall(12, 2);
        SuperLotto db = new SuperLotto();
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
        for (int i = 0; i < randomBlue.size(); i++) {
            int number = Integer.parseInt(randomBlue.get(i)) + 1;
            if (i == 0) {
                blue = blue + ((number < 10) ? ("0" + number) : number);
            } else {
                blue = blue + "," + ((number < 10) ? ("0" + number) : number);
            }
        }
        db.setType(0);
        db.setRed(red);
        db.setBlu(blue);
        db.setZhushu(1);
        db.setMoney(2);
        zhushu += 1;
        balls.add(0, db);
        adapter.notifyDataSetChanged();
        //列表是否显示
        listIsShow();
        //显示
        show();
    }

    private void show() {
        if (balls.size() == 0) {
            chooseNullTv.setVisibility(View.VISIBLE);
            chooseZhuRv.setVisibility(View.GONE);
        } else {
            chooseNullTv.setVisibility(View.GONE);
            chooseZhuRv.setVisibility(View.VISIBLE);
        }
        bottomResultCountTv.setText(zhushu + "注" + periods + "期" + multiple + "倍");
        bottomResultMoneyTv.setText(zhushu * periods * multiple * perMoney + "");
    }

    //选球列表是否显示
    private void listIsShow() {
        if (balls.size() > 0) {
            chooseZhuRv.setVisibility(View.VISIBLE);
            chooseNullTv.setVisibility(View.GONE);
        } else {
            chooseZhuRv.setVisibility(View.GONE);
            chooseNullTv.setVisibility(View.VISIBLE);
        }
    }


    private void initView() {

        boolean chip = SPUtil.contains(this, "chip");

        if (!chip) {
            rightTv.setVisibility(View.VISIBLE);
            rightTv.setText("发起合买");
        }else {
            rightTv.setVisibility(View.GONE);
            bottomResultBtn.setText("发起合买");
        }

        //每期机选
        periodsCount.setGoodsStorage(15);
        //每期投
        multiplyCount.setGoodsStorage(50);


        adapter = new SuperLottoPaymentAdapter(balls);
        chooseZhuRv.setLayoutManager(new LinearLayoutManager(this));
        chooseZhuRv.setAdapter(adapter);
        //设置分割线
        chooseZhuRv.addItemDecoration(new DashlineItemDivider(this));
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        adapter.addFooterView(view);


    }


    @Override
    public void saveDate() {
        SPUtil.putList(this, Contacts.superLottoSave, balls);
        finish();
    }

    @Override
    public void clearDate() {
        SPUtil.remove(this, Contacts.superLottoSave);
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
                case Contacts.REQUEST_CODE_SL_PAYMENT_TO_CHOOSE:
                    ArrayList<SuperLotto> getBalls = (ArrayList<SuperLotto>) data.getSerializableExtra("balls");
                    phase = data.getStringExtra("phase");
                    balls.addAll(0, getBalls);
                    for (int i = 0; i < getBalls.size(); i++) {
                        SuperLotto db = getBalls.get(i);
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
