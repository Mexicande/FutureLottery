package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.DoubleBall;
import cn.com.futurelottery.ui.adapter.ChooseBallPaymentAdapter;
import cn.com.futurelottery.utils.RandomMadeBall;
import cn.com.futurelottery.utils.StatusBarUtil;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.AmountView;

public class ChooseBallPaymentActivity extends BaseActivity {

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
    @BindView(R.id.et_stop_money)
    EditText etStopMoney;
    @BindView(R.id.accumulative_ll)
    LinearLayout accumulativeLl;
    private ArrayList<DoubleBall> balls = new ArrayList<>();
    private ChooseBallPaymentAdapter adapter;
    private long zhushu = 0;
    private long money = 0;
    //期数
    private long periods = 1;
    //倍数
    private long multiple = 1;
    //是否通知追期1=是 2=否
    String is_stop = "2";
    //停止追期金额
    String stop_money = "0";
    private String phase = "2018032";
    //种类  1双色球2大乐透
    private String kind;

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
                zhushu=zhushu-balls.get(position).getZhushu();
                money=zhushu*2;
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
                if (amount<=1){
                    accumulativeLl.setVisibility(View.GONE);
                    checkbox.setChecked(false);
                }else {
                    accumulativeLl.setVisibility(View.VISIBLE);
                }
                show();
            }
        });
        multiplyCount.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                multiple=amount;
                show();
            }
        });
    }

    //获取数据
    private void getData() {
        Intent intent = getIntent();
        kind = intent.getStringExtra("kinds");
        ArrayList<DoubleBall> getBalls = (ArrayList<DoubleBall>) intent.getSerializableExtra("balls");
        balls.addAll(0, getBalls);
        for (int i = 0; i < getBalls.size(); i++) {
            DoubleBall db = getBalls.get(i);
            zhushu = zhushu + db.getZhushu();
            money = zhushu*2;
        }
        show();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_choose_ball_payment;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(ChooseBallPaymentActivity.this, 0, null);
    }

    @OnClick({R.id.choose_self_ll, R.id.choose_random_ll, R.id.choose_clear_ll, R.id.bottom_result_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_self_ll:
                break;
            case R.id.choose_random_ll:
                randomChoose();
                break;
            case R.id.choose_clear_ll:
                clearList();
                break;
            case R.id.bottom_result_btn:
                pay();
                break;
        }
    }

    //清空列表
    private void clearList() {
        balls.clear();
        zhushu=0;
        money=0;
        show();
        adapter.notifyDataSetChanged();
    }

    //付款
    private void pay() {
        if (zhushu <= 0) {
            ToastUtils.showToast("至少选一注");
            return;
        }

        is_stop=checkbox.isChecked()?"1":"2";
        stop_money=etStopMoney.getText().toString().trim();

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
            map.put(Contacts.RED, ball.getRed());
            map.put(Contacts.MONEY, ball.getMoney() + "");
            map.put(Contacts.NOTES, ball.getZhushu() + "");

            JSONObject jsonObject = new JSONObject(map);
            ja.put(jsonObject);
        }
        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, money + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, stop_money);
        } catch (JSONException e) {

        }

        ApiService.GET_SERVICE(Api.Double_Ball.POST_DOUBLE_BALL, this, jo, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                ToastUtils.showToast(data.toString());
            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
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
        money =zhushu*2;
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
        bottomResultMoneyTv.setText(zhushu*periods*multiple*2 + "");
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
        adapter = new ChooseBallPaymentAdapter(balls);
        chooseZhuRv.setLayoutManager(new LinearLayoutManager(this));
        chooseZhuRv.setAdapter(adapter);
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        adapter.addFooterView(view);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.payment_item_delet_iv:
                        adapter.remove(position);

                        break;
                }
            }
        });
    }


}
