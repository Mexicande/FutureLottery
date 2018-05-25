package cn.com.futurelottery.ui.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.ChippedDetail;
import cn.com.futurelottery.ui.activity.DoubleBallActivity;
import cn.com.futurelottery.ui.activity.Football.FootBallActivity;
import cn.com.futurelottery.ui.activity.LoginActivity;
import cn.com.futurelottery.ui.activity.PayActivity;
import cn.com.futurelottery.ui.activity.PaySucessActivity;
import cn.com.futurelottery.ui.activity.SuperLottoActivity;
import cn.com.futurelottery.ui.activity.arrange.Line3Activity;
import cn.com.futurelottery.ui.activity.arrange.Line5Activity;
import cn.com.futurelottery.ui.activity.arrange.Lottery3DActivity;
import cn.com.futurelottery.ui.adapter.chipped.ChippedDetailFootAdapter;
import cn.com.futurelottery.ui.adapter.chipped.FollowPeopleAdapter;
import cn.com.futurelottery.ui.adapter.chipped.InformationAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.DeviceUtil;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.SPUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.AmountView;
import cn.com.futurelottery.view.progressdialog.KProgressHUD;

public class ChippedOrderDetailActivity extends BaseActivity {
    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.phase_tv)
    TextView phaseTv;
    @BindView(R.id.get_money_tv)
    TextView getMoneyTv;
    @BindView(R.id.progress_tv)
    TextView progressTv;
    @BindView(R.id.my_buy_tv)
    TextView myBuyTv;
    @BindView(R.id.status_tv)
    TextView statusTv;
    @BindView(R.id.people_tv)
    TextView peopleTv;
    @BindView(R.id.guarantee_tv)
    TextView guaranteeTv;
    @BindView(R.id.rate_tv)
    TextView rateTv;
    @BindView(R.id.infromation_tv)
    TextView infromationTv;
    @BindView(R.id.drop_down_information_iv)
    ImageView dropDownInformationIv;
    @BindView(R.id.information_click_ll)
    LinearLayout informationClickLl;
    @BindView(R.id.information_foot_ll)
    LinearLayout informationFootLl;
    @BindView(R.id.infromation_rv)
    RecyclerView infromationRv;
    @BindView(R.id.information_ll)
    LinearLayout informationLl;
    @BindView(R.id.prize_tv)
    TextView prizeTv;
    @BindView(R.id.prize_iv)
    ImageView prizeIv;
    @BindView(R.id.prize_ll)
    LinearLayout prizeLl;
    @BindView(R.id.prize_detail_ll)
    LinearLayout prizeDetailLl;
    @BindView(R.id.follow_people_tv)
    TextView followPeopleTv;
    @BindView(R.id.drop_down_iv)
    ImageView dropDownIv;
    @BindView(R.id.follow_people_ll)
    LinearLayout followPeopleLl;
    @BindView(R.id.lottery_tv)
    TextView lotteryTv;
    @BindView(R.id.follow_rv)
    RecyclerView followRv;
    @BindView(R.id.follow_ll)
    LinearLayout followLl;
    @BindView(R.id.orderid_tv)
    TextView orderidTv;
    @BindView(R.id.manifesto_tv)
    TextView manifestoTv;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.finish_time_tv)
    TextView finishTimeTv;
    @BindView(R.id.delet)
    TextView delet;
    @BindView(R.id.go_chipped_ll)
    LinearLayout goChippedLl;
    @BindView(R.id.go_chipped_tv)
    TextView goChippedTv;
    @BindView(R.id.left_count_tv)
    TextView leftCountTv;
    @BindView(R.id.money_av)
    AmountView moneyAv;
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.all_tv)
    TextView allTv;
    @BindView(R.id.bottom_money_tv)
    TextView bottomMoneyTv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.pay_ll)
    LinearLayout payLl;
    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.order_win_tv)
    TextView orderWinTv;
    @BindView(R.id.people_money_tv)
    TextView peopleMoneyTv;
    @BindView(R.id.people_win_tv)
    TextView peopleWinTv;
    @BindView(R.id.NestedScrollView)
    NestedScrollView NestedScrollView;
    @BindView(R.id.bouns_red_tv)
    TextView bounsRedTv;
    @BindView(R.id.bouns_blue_tv)
    TextView bounsBlueTv;
    @BindView(R.id.bouns_ll)
    LinearLayout bounsLl;
    private KProgressHUD hud;
    private String together_id;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
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
    private InformationAdapter ballAdapter;
    private boolean flagInformation;
    private boolean flagPrize;
    private AlertDialog alertDialog;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_chipped_order_detail;
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
        together_id = intent.getStringExtra("id");
        lotid = intent.getStringExtra("lotid");

        footAdapter = new ChippedDetailFootAdapter(dataFoot);
        ballAdapter = new InformationAdapter(dataBall);
        infromationRv.setLayoutManager(new LinearLayoutManager(this));
        if ("FT001".equals(lotid) || "FT002".equals(lotid) || "FT003".equals(lotid) || "FT004".equals(lotid) || "FT005".equals(lotid) || "FT006".equals(lotid)) {
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
        ApiService.GET_SERVICE(Api.Order.documentary, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
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
                if (null != hud) {
                    hud.dismiss();
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

    //更新界面
    private void update() throws Exception {
        String openmatch = data1.getString("openmatch");
        followAdapter.refresh(openmatch);

        Glide.with(this).load(data1.getString("logo"))
                .apply(new RequestOptions())
                .into(logoIv);
        nameTv.setText(data1.getString("name"));
        phaseTv.setText(data1.getString("phase").split("[*]")[0]);

        //判断是否中大奖
        String winning_money = data1.getString("winning_money");
        if ("-1".equals(winning_money)) {
            getMoneyTv.setText("稍后会有工作人员主动联系您");
        } else {
            getMoneyTv.setText(winning_money + "元");
        }

        //显示开奖信息
        if (TextUtils.isEmpty(data1.getString("bouns"))) {
            bounsLl.setVisibility(View.GONE);
        } else {
            String[] arr = data1.getString("bouns").split("#");
            if (arr.length > 1) {
                bounsRedTv.setText(arr[0].replace(",", " "));
                bounsBlueTv.setText(arr[1].replace(",", " "));
            } else {
                bounsRedTv.setText(arr[0].replace(",", " "));
            }
        }


        if ("0".equals(openmatch)) {
            statusTv.setText("待开奖");
            //是否显示合买支付
            leftMoney = data1.getString("remaining_money");
            if ("0".equals(leftMoney) || "0.00".equals(leftMoney)) {
                payLl.setVisibility(View.GONE);
            } else {
                payLl.setVisibility(View.VISIBLE);
                leftCountTv.setText(leftMoney);
                moneyAv.setGoodsStorage(Integer.parseInt(leftMoney));
            }
        } else {
            //是否显示合买支付
            payLl.setVisibility(View.GONE);

            if ("0".equals(winning_money) || "0.00".equals(winning_money)) {
                statusTv.setText("未中奖");
            } else {
                statusTv.setTextColor(getResources().getColor(R.color.red_ball));
                statusTv.setText("已中奖");
            }
            //显示合买与删除
            goChippedLl.setVisibility(View.VISIBLE);
            goChippedTv.setVisibility(View.VISIBLE);
        }
        myBuyTv.setText(data1.getString("quota"));
        progressTv.setText(data1.getString("progress") + "%");

        peopleTv.setText(data1.getString("user"));
        guaranteeTv.setText(data1.getString("baseline_money") + "元");
        rateTv.setText(data1.getString("cut") + "%");

        switch (data1.getString("type")) {
            case "1":
                infromationTv.setText("完全公开");
                break;
            case "2":
                infromationTv.setText("参与可见");
                break;
            case "3":
                infromationTv.setText("截止后可");
                break;
        }
        if (info.size() == 0) {
            isShowInformation = false;
            dropDownInformationIv.setVisibility(View.GONE);
        } else {
            isShowInformation = true;
            dropDownInformationIv.setVisibility(View.VISIBLE);
        }

        //派单详情
        orderMoneyTv.setText(data1.getString("pay_money_total"));
        orderWinTv.setText(data1.getString("winning_money_total")+"元");
        peopleMoneyTv.setText(data1.getString("cut") + "%");
        peopleWinTv.setText(data1.getString("extract")+"元");


        followPeopleTv.setText(data1.getString("number") + "人跟单");

        orderidTv.setText(data1.getString("together_id"));
        manifestoTv.setText(data1.getString("declaration"));
        startTimeTv.setText(data1.getString("created_at"));
        finishTimeTv.setText(format1.format(data1.getLong("date") * 1000));
    }

    @OnClick({R.id.layout_top_back, R.id.question_mark_iv, R.id.pay_btn, R.id.follow_people_ll, R.id.all_tv, R.id.information_click_ll, R.id.prize_ll, R.id.go_chipped_tv, R.id.delet})
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
            case R.id.prize_ll:
                RoteteUtils.rotateArrow(prizeIv, flagPrize);
                flagPrize = !flagPrize;
                if (flagPrize) {
                    prizeDetailLl.setVisibility(View.VISIBLE);
                } else {
                    prizeDetailLl.setVisibility(View.GONE);
                }
                break;
            case R.id.go_chipped_tv:
                switch (lotid) {
                    case "ssq":
                        ActivityUtils.startActivity(DoubleBallActivity.class);
                        break;
                    case "dlt":
                        ActivityUtils.startActivity(SuperLottoActivity.class);
                        break;
                    case "ftb":
                    case "FT001":
                    case "FT002":
                    case "FT003":
                    case "FT004":
                    case "FT005":
                    case "FT006":
                        ActivityUtils.startActivity(FootBallActivity.class);
                        break;
                    case "p3":
                        ActivityUtils.startActivity(Line3Activity.class);
                        break;
                    case "p5":
                        ActivityUtils.startActivity(Line5Activity.class);
                        break;
                    case "3d":
                        ActivityUtils.startActivity(Lottery3DActivity.class);
                        break;
                }
                break;
            case R.id.delet:
                //删除
                showDelet();
                break;
        }
    }

    private void showDelet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除本订单");
        builder.setMessage("删除后本订单将无法还原");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletMy();
                alertDialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletMy() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", together_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Order.del, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    if (0 == code) {
                        setResult(-1);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });
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
                        Intent intent = new Intent(ChippedOrderDetailActivity.this, PayActivity.class);
                        intent.putExtra("information", "合买跟单");
                        intent.putExtra("money", data.getJSONObject("data").getString(Contacts.Order.MONEY));
                        intent.putExtra(Contacts.Order.ORDERID, data.getJSONObject("data").getString(Contacts.Order.ORDERID));
                        startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
                    } else if (code == 0) {
                        try {
                            JSONObject data1 = data.getJSONObject("data");
                            Intent intent = new Intent(ChippedOrderDetailActivity.this, PaySucessActivity.class);
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
