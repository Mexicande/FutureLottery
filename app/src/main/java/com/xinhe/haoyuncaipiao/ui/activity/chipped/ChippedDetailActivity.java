package com.xinhe.haoyuncaipiao.ui.activity.chipped;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseActivity;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;
import com.xinhe.haoyuncaipiao.ui.activity.LoginActivity;
import com.xinhe.haoyuncaipiao.ui.activity.PayActivity;
import com.xinhe.haoyuncaipiao.ui.activity.PaySucessActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.ChippedDetailFootAdapter;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.ChippedOtherDetailFootAdapter;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.FollowPeopleAdapter;
import com.xinhe.haoyuncaipiao.ui.adapter.chipped.InformationAdapter;
import com.xinhe.haoyuncaipiao.utils.ActivityUtils;
import com.xinhe.haoyuncaipiao.utils.DeviceUtil;
import com.xinhe.haoyuncaipiao.utils.RoteteUtils;
import com.xinhe.haoyuncaipiao.utils.SPUtils;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;
import com.xinhe.haoyuncaipiao.view.AmountView;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author apple
 *         合买详情
 */
public class ChippedDetailActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.phase_tv)
    TextView phaseTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.total_money_tv)
    TextView totalMoneyTv;
    @BindView(R.id.buy_money_tv)
    TextView buyMoneyTv;
    @BindView(R.id.left_money_tv)
    TextView leftMoneyTv;
    @BindView(R.id.progress_tv)
    TextView progressTv;
    @BindView(R.id.people_tv)
    TextView peopleTv;
    @BindView(R.id.recored_tv)
    TextView recoredTv;
    @BindView(R.id.recored_money_tv)
    TextView recoredMoneyTv;
    @BindView(R.id.guarantee_tv)
    TextView guaranteeTv;
    @BindView(R.id.rate_tv)
    TextView rateTv;
    @BindView(R.id.infromation_tv)
    TextView infromationTv;
    @BindView(R.id.follow_people_tv)
    TextView followPeopleTv;
    @BindView(R.id.drop_down_iv)
    ImageView dropDownIv;
    @BindView(R.id.follow_people_ll)
    LinearLayout followPeopleLl;
    @BindView(R.id.orderid_tv)
    TextView orderidTv;
    @BindView(R.id.manifesto_tv)
    TextView manifestoTv;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.finish_time_tv)
    TextView finishTimeTv;
    @BindView(R.id.full_tv)
    TextView fullTv;
    @BindView(R.id.left_count_tv)
    TextView leftCountTv;
    @BindView(R.id.money_av)
    AmountView moneyAv;
    @BindView(R.id.all_tv)
    TextView allTv;
    @BindView(R.id.bottom_money_tv)
    TextView bottomMoneyTv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.pay_ll)
    LinearLayout payLl;
    @BindView(R.id.drop_down_information_iv)
    ImageView dropDownInformationIv;
    @BindView(R.id.infromation_rv)
    RecyclerView infromationRv;
    @BindView(R.id.follow_ll)
    LinearLayout followLl;
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.lottery_tv)
    TextView lotteryTv;
    @BindView(R.id.follow_rv)
    RecyclerView followRv;
    @BindView(R.id.information_foot_ll)
    LinearLayout informationFootLl;
    @BindView(R.id.information_ll)
    LinearLayout informationLl;
    @BindView(R.id.NestedScrollView)
    NestedScrollView NestedScrollView;
    @BindView(R.id.layout_time)
    LinearLayout layoutTime;
    @BindView(R.id.infomation)
    TextView infomation;
    private KProgressHUD hud;
    private String together_id;
    /**
     * 倒计时60秒，一次1秒
     */
    private CountDownTimer timer;
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //是否展示信息
    private boolean isShowInformation;
    private ArrayList<ChippedDetail.InfoProduct> info = new ArrayList<>();
    private ArrayList<ChippedDetail.DataProduct> dataBall = new ArrayList<>();
    private ArrayList<ChippedDetail.DataFootball> dataFoot = new ArrayList<>();
    private FollowPeopleAdapter followAdapter;
    private boolean flag;
    private int money = 1;
    private String leftMoney;
    private String lotid;
    private JSONObject data1;
    private ChippedDetailFootAdapter footAdapter;
    private ChippedOtherDetailFootAdapter otherFootAdapter;
    private InformationAdapter ballAdapter;
    private boolean flagInformation;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_chipped_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData();
        setlistener();
    }

    private void setlistener() {
        moneyAv.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                money = amount;
                bottomMoneyTv.setText(money + "");
            }
        });
    }

    private void initView() {
        NestedScrollView.setNestedScrollingEnabled(false);

        tvTitle.setText("合买详情");
        bottomMoneyTv.setText(money + "");

        Intent intent = getIntent();
        together_id = intent.getStringExtra("together_id");
        lotid = intent.getStringExtra("lotid");

        footAdapter = new ChippedDetailFootAdapter(dataFoot);
        otherFootAdapter = new ChippedOtherDetailFootAdapter(dataFoot);

        ballAdapter = new InformationAdapter(dataBall);
        infromationRv.setLayoutManager(new LinearLayoutManager(this));


        if ("FT001".equals(lotid) || "FT002".equals(lotid) || "FT003".equals(lotid) || "FT004".equals(lotid) || "FT006".equals(lotid)) {
            infromationRv.setAdapter(otherFootAdapter);
            informationFootLl.setVisibility(View.VISIBLE);
        } else if ("FT005".equals(lotid)) {
            infromationRv.setAdapter(footAdapter);
            informationFootLl.setVisibility(View.VISIBLE);
        } else {
            infromationRv.setAdapter(ballAdapter);
            informationFootLl.setVisibility(View.GONE);
        }

        followAdapter = new FollowPeopleAdapter(info);
        followRv.setLayoutManager(new LinearLayoutManager(this));
        followRv.setAdapter(followAdapter);
    }

    private void getData() {
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常");
            return;
        }
        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setDimAmount(0.5f)
                .show();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", together_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Together.details, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    Gson gson = new Gson();
                    data1 = data.getJSONObject("data");
                    Type typeInfo = new TypeToken<ArrayList<ChippedDetail.InfoProduct>>() {
                    }.getType();
                    ArrayList<ChippedDetail.InfoProduct> in = gson.fromJson(data1.getJSONArray("info").toString(), typeInfo);
                    info.addAll(in);
                    //投注信息
                    if ("FT001".equals(lotid) || "FT002".equals(lotid) || "FT003".equals(lotid) || "FT004".equals(lotid) || "FT005".equals(lotid) || "FT006".equals(lotid)) {
                        Type typeFoot = new TypeToken<ArrayList<ChippedDetail.DataFootball>>() {
                        }.getType();
                        ArrayList<ChippedDetail.DataFootball> foot = gson.fromJson(data1.getJSONArray("data").toString(), typeFoot);
                        dataFoot.clear();
                        dataFoot.addAll(foot);
                        footAdapter.notifyDataSetChanged();
                    } else {
                        Type typeBall = new TypeToken<ArrayList<ChippedDetail.DataProduct>>() {
                        }.getType();
                        ArrayList<ChippedDetail.DataProduct> ball = gson.fromJson(data1.getJSONArray("data").toString(), typeBall);
                        dataBall.clear();
                        dataBall.addAll(ball);
                        ballAdapter.notifyDataSetChanged();
                    }

                    update();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                if (null != hud) {
                    hud.dismiss();
                }
            }
        });
    }

    private void update() throws Exception {
        followAdapter.notifyDataSetChanged();
        Glide.with(this).load(data1.getString("logo"))
                .apply(new RequestOptions())
                .into(logoIv);
        nameTv.setText(data1.getString("name"));
        phaseTv.setText(data1.getString("phase").split("[*]")[0]);

        final long time = data1.getLong("date") * 1000 - System.currentTimeMillis();
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long l = millisUntilFinished / 1000;
                long hour = (long) Math.floor(l / 3600.0);
                long min = (long) Math.floor(l % 3600 / 60.0);
                long sec = (long) l % 60;
                timeTv.setText((hour < 10 ? "0" + hour : hour) + ":" + (min < 10 ? "0" + min : min) + ":" + sec);
            }

            @Override
            public void onFinish() {

            }
        }.start();

        totalMoneyTv.setText(data1.getString("pay_money_total"));
        buyMoneyTv.setText(data1.getString("percentage"));
        leftMoneyTv.setText(data1.getString("remaining_money"));
        progressTv.setText(data1.getString("progress") + "%");

        peopleTv.setText(data1.getString("user"));
        recoredTv.setText("中奖" + data1.getString("winning_num") + "次，中奖");
        recoredMoneyTv.setText(data1.getString("winning_money_total") + "元");
        guaranteeTv.setText(data1.getString("baseline_money") + "元");
        rateTv.setText(data1.getString("cut") + "%");



        String multiple = data1.getString("multiple");
        String strand = data1.getString("strand");
        String notes = data1.getString("notes");

      /*  if("0".equals(strand)){
            infomation.setText("单关"+" "+multiple+"倍");
        }else {
            infomation.setText(strand+"串1"+" "+multiple+"倍");
        }*/


        switch (data1.getString("type")) {
            case "1":
                infromationTv.setText("完全公开");
                isShowInformation = true;
                dropDownInformationIv.setVisibility(View.VISIBLE);

                break;
            case "2":
                infromationTv.setText("参与可见");
                isShowInformation = false;
                dropDownInformationIv.setVisibility(View.GONE);
                break;
            case "3":
                infromationTv.setText("截止后可");
                isShowInformation = false;
                dropDownInformationIv.setVisibility(View.GONE);
                break;
        }

        if ("0".equals(strand)) {
            infomation.setText("单关" + " "+ notes+"注"+ multiple + "倍");
        } else {
            infomation.setText(strand + "串1" + " "+notes+"注" + multiple + "倍");
        }

        followPeopleTv.setText(data1.getString("number") + "人跟单");

        orderidTv.setText(data1.getString("together_id"));
        manifestoTv.setText(data1.getString("declaration"));
        startTimeTv.setText(data1.getString("created_at"));
        finishTimeTv.setText(format1.format(data1.getLong("date") * 1000));

        leftMoney = data1.getString("remaining_money");
        if ("0".equals(leftMoney)) {
            payLl.setVisibility(View.GONE);
            fullTv.setVisibility(View.VISIBLE);
        } else {
            fullTv.setVisibility(View.GONE);
            payLl.setVisibility(View.VISIBLE);
            leftCountTv.setText(leftMoney);
            moneyAv.setGoodsStorage(Integer.parseInt(leftMoney));
        }
    }

    @OnClick({R.id.layout_top_back, R.id.question_mark_iv, R.id.pay_btn, R.id.follow_people_ll, R.id.all_tv, R.id.information_click_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.question_mark_iv:
                break;
            case R.id.pay_btn:
                String string = (String) SPUtils.get(this, Contacts.TOKEN, "");
                if (TextUtils.isEmpty(string)) {
                    ToastUtils.showToast(getString(R.string.login_please));
                    ActivityUtils.startActivity(LoginActivity.class);
                } else {
                    pay();
                }
                break;
            case R.id.follow_people_ll:
                RoteteUtils.rotateArrow(dropDownIv, flag);
                flag = !flag;
                if (flag) {
                    followLl.setVisibility(View.VISIBLE);
                } else {
                    followLl.setVisibility(View.GONE);
                }
                break;
            case R.id.all_tv:
                moneyAv.setCurrentValue(Integer.parseInt(leftMoney));
                break;
            case R.id.information_click_ll:
                if (isShowInformation) {
                    RoteteUtils.rotateArrow(dropDownInformationIv, flagInformation);
                    flagInformation = !flagInformation;
                    if (flagInformation) {
                        informationLl.setVisibility(View.VISIBLE);
                    } else {
                        informationLl.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    //下单
    private void pay() {
        if (!DeviceUtil.IsNetWork(this)) {
            ToastUtils.showToast("网络异常，检查网络后重试");
            return;
        }


        JSONObject jo = new JSONObject();
        try {
            jo.put("id", together_id);
            jo.put("money", money);
        } catch (JSONException e) {

        }

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中...")
                .setDimAmount(0.5f)
                .show();

        ApiService.GET_SERVICE(Api.Together.documentary, this, jo, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (code == Api.Special_Code.notEnoughMoney) {
                        Intent intent = new Intent(ChippedDetailActivity.this, PayActivity.class);
                        intent.putExtra("information", "合买跟单");
                        intent.putExtra("money", data.getJSONObject("data").getString(Contacts.Order.MONEY));
                        intent.putExtra(Contacts.Order.ORDERID, data.getJSONObject("data").getString(Contacts.Order.ORDERID));
                        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
                    } else if (code == 0) {
                        try {
                            JSONObject data1 = data.getJSONObject("data");
                            Intent intent = new Intent(ChippedDetailActivity.this, PaySucessActivity.class);
                            intent.putExtra("kind", data1.getString("lotid"));
                            intent.putExtra("type", data1.getString("type"));
                            intent.putExtra("orderid", data1.getString("order_id"));
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
                ToastUtils.showToast(msg);
            }
        });
    }
}
