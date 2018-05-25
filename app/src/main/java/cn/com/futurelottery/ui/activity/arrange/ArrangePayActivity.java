package cn.com.futurelottery.ui.activity.arrange;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.listener.SaveDialogListener;
import cn.com.futurelottery.model.Arrange;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.ui.activity.PayActivity;
import cn.com.futurelottery.ui.activity.PayAffirmActivity;
import cn.com.futurelottery.ui.activity.PaySucessActivity;
import cn.com.futurelottery.ui.activity.chipped.ChippedActivity;
import cn.com.futurelottery.ui.adapter.arrange.ArrangePayAdapter;
import cn.com.futurelottery.ui.dialog.QuitDialogFragment;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.RandomMadeBall;
import cn.com.futurelottery.utils.SPUtil;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.AmountView;
import cn.com.futurelottery.view.DashlineItemDivider;

/**
 * 排3、排5、3D付款页面
 */
public class ArrangePayActivity extends BaseActivity implements SaveDialogListener {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
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
    @BindView(R.id.tip_iv)
    ImageView tipIv;
    @BindView(R.id.accumulative_ll)
    LinearLayout accumulativeLl;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_btn)
    Button bottomResultBtn;
    @BindView(R.id.right_tv)
    TextView rightTv;
    private ArrayList<Arrange> balls = new ArrayList<>();
    private long zhushu = 0;
    private String phase = "";
    //期数
    private long periods = 1;
    //倍数
    private long multiple = 1;
    private String kind;
    private ArrangePayAdapter adapter;
    private int REQUEST_CODE_PAYMENT_TO_CHOOSE = 101;
    private String is_stop = "2";
    String sp = "";
    private String information;
    private String lotid;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_arrange_pay;
    }

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
                zhushu = zhushu - balls.get(position).getNotes();
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

    private void initView() {
        //合买
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("发起合买");

        //每期机选
        periodsCount.setGoodsStorage(15);
        //每期投
        multiplyCount.setGoodsStorage(50);


        adapter = new ArrangePayAdapter(balls, kind);
        chooseZhuRv.setLayoutManager(new LinearLayoutManager(this));
        chooseZhuRv.setAdapter(adapter);
        //设置分割线
        chooseZhuRv.addItemDecoration(new DashlineItemDivider(this));
        View view = getLayoutInflater().inflate(R.layout.payment_rv_footor, null);
        adapter.addFooterView(view);
    }


    //获取数据
    private void getData() {
        Intent intent = getIntent();
        phase = intent.getStringExtra("phase");
        kind = intent.getStringExtra("kind");
        tvTitle.setText(kind);
        if (Contacts.Lottery.P5name.equals(kind)) {
            sp = Contacts.line5;
            lotid="p5";
        } else if (Contacts.Lottery.P3name.equals(kind)) {
            sp = Contacts.line3;
            lotid="p3";
        } else if (Contacts.Lottery.D3name.equals(kind)) {
            sp = Contacts.D3;
            lotid="3d";
        }
        //本地数据
        if (SPUtil.contains(this, sp)) {
            ArrayList<Arrange> getBalls = (ArrayList<Arrange>) ((Serializable) SPUtil.getList(this, sp));
            balls.addAll(0, getBalls);
            for (int i = 0; i < getBalls.size(); i++) {
                Arrange db = getBalls.get(i);
                zhushu = zhushu + db.getNotes();
            }
        }


        ArrayList<Arrange> getBalls = (ArrayList<Arrange>) intent.getSerializableExtra("balls");
        balls.addAll(0, getBalls);
        for (int i = 0; i < getBalls.size(); i++) {
            Arrange arrange = getBalls.get(i);
            zhushu = zhushu + arrange.getNotes();
        }
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
        bottomResultMoneyTv.setText(zhushu * periods * multiple * 2 + "");
    }

    @OnClick({R.id.choose_self_ll, R.id.choose_random_ll, R.id.choose_clear_ll, R.id.layout_top_back, R.id.bottom_result_btn, R.id.tip_iv,R.id.right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_self_ll:
                if (Contacts.Lottery.P5name.equals(kind)) {
                    Intent intent = new Intent(this, Line5Activity.class);
                    intent.putExtra("data", 1);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT_TO_CHOOSE);
                } else if (Contacts.Lottery.P3name.equals(kind)) {
                    Intent intent = new Intent(this, Line3Activity.class);
                    intent.putExtra("data", 1);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT_TO_CHOOSE);
                } else if (Contacts.Lottery.D3name.equals(kind)) {
                    Intent intent = new Intent(this, Lottery3DActivity.class);
                    intent.putExtra("data", 1);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT_TO_CHOOSE);
                }
                break;
            case R.id.choose_random_ll:
                randomChoose();
                break;
            case R.id.choose_clear_ll:
                clearList();
                break;
            case R.id.layout_top_back:
                if (balls.size() > 0) {
                    showMyDialog();
                } else {
                    finish();
                }
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
        if (Contacts.Lottery.P5name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.Arrange.individual, ball.getIndividual());
                map.put(Contacts.Arrange.ten, ball.getTen());
                map.put(Contacts.Arrange.hundred, ball.getHundred());
                map.put(Contacts.Arrange.thousand, ball.getThousand());
                map.put(Contacts.Arrange.absolutely, ball.getAbsolutely());
                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }
        } else if (Contacts.Lottery.P3name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.TYPE, ball.getType() + "");
                map.put(Contacts.Arrange.individual, ball.getIndividual());
                map.put(Contacts.Arrange.ten, ball.getTen());
                map.put(Contacts.Arrange.hundred, ball.getHundred());
                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }

        } else if (Contacts.Lottery.D3name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.TYPE, ball.getType() + "");

                if (1 == ball.getType()) {
                    map.put("zhi", ball.getZhi());
                } else if (2 == ball.getType()) {

                } else if (3 == ball.getType()) {
                    map.put("three_fu", ball.getThree_fu());
                } else if (4 == ball.getType()) {
                    map.put("six", ball.getSix());
                }

                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }

        }

        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * 2 + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, "");
        } catch (JSONException e) {

        }
        Intent intent=new Intent(ArrangePayActivity.this,ChippedActivity.class);
        intent.putExtra("totalMoney",zhushu * periods * multiple * 2);
        intent.putExtra("information", information);
        intent.putExtra("name", kind);
        intent.putExtra("json", jo.toString());
        intent.putExtra("lotid", sp);
        startActivity(intent);
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
        if (Contacts.Lottery.P5name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.Arrange.individual, ball.getIndividual());
                map.put(Contacts.Arrange.ten, ball.getTen());
                map.put(Contacts.Arrange.hundred, ball.getHundred());
                map.put(Contacts.Arrange.thousand, ball.getThousand());
                map.put(Contacts.Arrange.absolutely, ball.getAbsolutely());
                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }
        } else if (Contacts.Lottery.P3name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.TYPE, ball.getType() + "");
                map.put(Contacts.Arrange.individual, ball.getIndividual());
                map.put(Contacts.Arrange.ten, ball.getTen());
                map.put(Contacts.Arrange.hundred, ball.getHundred());
                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }

        } else if (Contacts.Lottery.D3name.equals(kind)) {
            information = kind + " 第" + phase + "期";

            for (int i = 0; i < balls.size(); i++) {
                Arrange ball = balls.get(i);
                Map<String, String> map = new HashMap<>();

                map.put(Contacts.TYPE, ball.getType() + "");

                if (1 == ball.getType()) {
                    map.put("zhi", ball.getZhi());
                } else if (2 == ball.getType()) {

                } else if (3 == ball.getType()) {
                    map.put("three_fu", ball.getThree_fu());
                } else if (4 == ball.getType()) {
                    map.put("six", ball.getSix());
                }

                map.put(Contacts.MONEY, ball.getNotes() * 2 * periods * multiple + "");
                map.put(Contacts.NOTES, ball.getNotes() + "");

                JSONObject jsonObject = new JSONObject(map);
                ja.put(jsonObject);
            }

        }

        try {
            jo.put(Contacts.TOTAL, ja);
            jo.put(Contacts.NOTES, zhushu + "");
            jo.put(Contacts.MONEY, zhushu * periods * multiple * 2 + "");
            jo.put(Contacts.PERIODS, periods + "");
            jo.put(Contacts.MULTIPLE, multiple + "");
            jo.put(Contacts.PHASE, phase);
            jo.put(Contacts.IS_STOP, is_stop);
            jo.put(Contacts.STOP_MONEY, "");
        } catch (JSONException e) {

        }

        Intent intent = new Intent(ArrangePayActivity.this, PayAffirmActivity.class);
        intent.putExtra("information", information);
        intent.putExtra("money", zhushu * periods * multiple * 2+"");
        intent.putExtra("lotid", lotid);
        intent.putExtra("json", jo.toString());
        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
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

    //机选一注
    private void randomChoose() {
        Arrange arrange = new Arrange();
        if (Contacts.Lottery.P5name.equals(kind)) {
            arrange.setAbsolutely((int) (Math.random() * 10) + "");
            arrange.setThousand((int) (Math.random() * 10) + "");
            arrange.setHundred((int) (Math.random() * 10) + "");
            arrange.setTen((int) (Math.random() * 10) + "");
            arrange.setIndividual((int) (Math.random() * 10) + "");

            arrange.setNotes(1);
            arrange.setMoney(2);

        } else if (Contacts.Lottery.P3name.equals(kind)) {
            if (balls.size() == 0 || balls.get(0).getType() == 1) {
                arrange.setHundred((int) (Math.random() * 10) + "");
                arrange.setTen((int) (Math.random() * 10) + "");
                arrange.setIndividual((int) (Math.random() * 10) + "");

                arrange.setType(1);
                arrange.setNotes(1);
                arrange.setMoney(2);
            } else if (balls.get(0).getType() == 2) {
                ArrayList<String> random = RandomMadeBall.getManyBall(10, 2);
                arrange.setIndividual(random.get(0) + random.get(1));

                arrange.setType(2);
                arrange.setNotes(2);
                arrange.setMoney(4);
            } else if (balls.get(0).getType() == 3) {
                ArrayList<String> random = RandomMadeBall.getManyBall(10, 3);
                arrange.setIndividual(random.get(0) + random.get(1) + random.get(2));

                arrange.setType(3);
                arrange.setNotes(1);
                arrange.setMoney(2);
            }
        } else if (Contacts.Lottery.D3name.equals(kind)) {
            if (balls.size() == 0 || balls.get(0).getType() == 1) {
                arrange.setZhi((int) (Math.random() * 10) + "," + (int) (Math.random() * 10) + "," + (int) (Math.random() * 10));

                arrange.setType(1);
                arrange.setNotes(1);
                arrange.setMoney(2);
            } else if (balls.get(0).getType() == 3) {
                ArrayList<String> random = RandomMadeBall.getManyBall(10, 2);
                arrange.setThree_fu(random.get(0) + "," + random.get(1));

                arrange.setType(3);
                arrange.setNotes(2);
                arrange.setMoney(4);
            } else if (balls.get(0).getType() == 4) {
                ArrayList<String> random = RandomMadeBall.getManyBall(10, 3);
                arrange.setSix(random.get(0) + "," + random.get(1) + "," + random.get(2));

                arrange.setType(4);
                arrange.setNotes(1);
                arrange.setMoney(2);
            }
        }


        zhushu += arrange.getNotes();
        balls.add(0, arrange);
        adapter.notifyDataSetChanged();
        //显示
        show();
    }

    @Override
    public void saveDate() {
        SPUtil.putList(this, sp, balls);
        finish();
    }

    @Override
    public void clearDate() {
        SPUtil.remove(this, sp);
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
                    ArrayList<Arrange> getBalls = (ArrayList<Arrange>) data.getSerializableExtra("balls");
                    phase = data.getStringExtra("phase");
                    balls.addAll(0, getBalls);
                    for (int i = 0; i < getBalls.size(); i++) {
                        Arrange db = getBalls.get(i);
                        zhushu = zhushu + db.getNotes();
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
