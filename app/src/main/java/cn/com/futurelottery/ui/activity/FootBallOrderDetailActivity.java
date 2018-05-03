package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallOrder;
import cn.com.futurelottery.ui.adapter.FootBallOrderAdapter;
import cn.com.futurelottery.utils.ToastUtils;

public class FootBallOrderDetailActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.phase_tv)
    TextView phaseTv;
    @BindView(R.id.name_ll)
    LinearLayout nameLl;
    @BindView(R.id.order_money_tv)
    TextView orderMoneyTv;
    @BindView(R.id.order_money_ll)
    LinearLayout orderMoneyLl;
    @BindView(R.id.winning_money_tv)
    TextView winningMoneyTv;
    @BindView(R.id.winning_money_ll)
    LinearLayout winningMoneyLl;
    @BindView(R.id.winning_iv)
    ImageView winningIv;
    @BindView(R.id.order_status_tv)
    TextView orderStatusTv;
    @BindView(R.id.order_detail_tv1)
    TextView orderDetailTv1;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.choose_detail_tv)
    TextView chooseDetailTv;
    @BindView(R.id.choose_detail_ll1)
    LinearLayout chooseDetailLl1;
    @BindView(R.id.calculate_rl)
    RelativeLayout calculateRl;
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.order_time_tv)
    TextView orderTimeTv;
    @BindView(R.id.order_number_tv)
    TextView orderNumberTv;
    @BindView(R.id.order_tip_tv)
    TextView orderTipTv;
    @BindView(R.id.delet)
    TextView delet;
    @BindView(R.id.continue_btn)
    Button continueBtn;
    @BindView(R.id.buy_btn)
    Button buyBtn;
    @BindView(R.id.icon_iv)
    ImageView iconIv;
    private String id;
    private String ballName;
    private String iconURL;
    private String name;
    private String pay_money;
    private String openmatch;
    private String winning_money;
    private String multiple;
    private String created_at;
    private String tc_order_num;
    private String count;
    private String strand;
    private List<FootBallOrder.ArrProduct> orders=new ArrayList<>();
    private FootBallOrderAdapter adapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_foot_ball_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
    }

    private void initView() {
        adapter=new FootBallOrderAdapter(orders);
        rv.setAdapter(adapter);
    }

    private void getData() {
        orders.clear();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        ballName = intent.getStringExtra("ballName");
        tvTitle.setText(type);
        buyBtn.setText(ballName + "投注");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.Order.deteails, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONArray ja = data.getJSONArray("data");
                    JSONObject jo = ja.getJSONObject(0);
                    Gson gson = new Gson();
                    FootBallOrder footBallOrder = gson.fromJson(jo.toString(), FootBallOrder.class);
                    iconURL = footBallOrder.getLogo();
                    name = footBallOrder.getName();
                    pay_money = footBallOrder.getPay_money();
                    //中奖状态
                    openmatch = footBallOrder.getOpenmatch();
                    //中奖额度
                    winning_money = footBallOrder.getWinmoney();
                    //倍数
                    multiple = footBallOrder.getMultiple();
                    //下单时间
                    created_at = footBallOrder.getCreated_at();
                    //订单编号
                    tc_order_num = footBallOrder.getTc_order_num();
                    //订单编号
                    tc_order_num = footBallOrder.getTc_order_num();
                    //场数
                    count = footBallOrder.getCount();
                    //串
                    strand = footBallOrder.getStrand();
                    //多场比赛
                    orders.addAll(footBallOrder.getArr());


                    setView();
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


    private void setView() {
        try {
            Glide.with(this).load(iconURL)
                    .apply(new RequestOptions())
                    .into(iconIv);
            if ("0".equals(openmatch)) {
                winningIv.setImageResource(R.mipmap.order_wait);
                orderStatusTv.setText("等待开奖");
            } else if ("2".equals(openmatch)) {
                winningIv.setImageResource(R.mipmap.order_unwinning);
                orderStatusTv.setText("未中奖");
            }
            if ("3".equals(openmatch)) {
                winningIv.setImageResource(R.mipmap.order_winning);
                winningMoneyTv.setTextColor(getResources().getColor(R.color.red_ball));
                orderStatusTv.setTextColor(getResources().getColor(R.color.red_ball));
                orderStatusTv.setText("中奖");
            }
            nameTv.setText(name);
//            phaseTv.setText("第" + phase + "期");
            orderMoneyTv.setText(pay_money);
            if (TextUtils.isEmpty(winning_money)) {
                winningMoneyTv.setText("0.00");
            } else {
                winningMoneyTv.setText(winning_money);
            }

            orderDetailTv1.setText(count+"场，"+strand+"串1，方案"+multiple+"倍");


            orderTimeTv.setText(created_at);
            orderNumberTv.setText(tc_order_num);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.layout_top_back, R.id.delet, R.id.buy_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.delet:
                break;
            case R.id.buy_btn:
                break;
        }
    }
}
