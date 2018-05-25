package cn.com.futurelottery.ui.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallOrder;
import cn.com.futurelottery.ui.activity.FootBallOrderItemActivity;
import cn.com.futurelottery.ui.activity.Football.FootBallActivity;
import cn.com.futurelottery.ui.activity.WebViewActivity;
import cn.com.futurelottery.ui.adapter.FootBallOrderAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
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
    @BindView(R.id.nest_sv)
    NestedScrollView nestSv;
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
    private List<FootBallOrder.DataProduct.ArrProduct> orders = new ArrayList<>();
    private FootBallOrderAdapter adapter;
    private AlertDialog alertDialog;
    //开奖情况
    private String lottery;
    private String lotid;
    private String url;
    private String mess;

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
        nestSv.setNestedScrollingEnabled(false);

        adapter = new FootBallOrderAdapter(orders);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void getData() {
        orders.clear();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        lotid = intent.getStringExtra("lotid");
        String type = intent.getStringExtra("type");
        ballName = intent.getStringExtra("ballName");
        tvTitle.setText(type);
        buyBtn.setText(ballName + "投注");

        if ("FT005".equals(lotid)) {
            url = Api.Order.footDeteails;
        } else {
            url = Api.Order.deteails;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(url, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    FootBallOrder footBallOrder = gson.fromJson(data.toString(), FootBallOrder.class);
                    List<FootBallOrder.DataProduct> data1 = footBallOrder.getData();
                    if (null != data1 && data1.size() > 0) {
                        FootBallOrder.DataProduct dataProduct = data1.get(0);
                        iconURL = dataProduct.getLogo();
                        name = dataProduct.getName();
                        pay_money = dataProduct.getPay_money();
                        //中奖状态
                        openmatch = dataProduct.getOpenmatch();
                        //中奖额度
                        winning_money = dataProduct.getWinning_money();
                        //倍数
                        multiple = dataProduct.getMultiple();
                        //下单时间
                        created_at = dataProduct.getCreated_at();
                        //订单编号
                        tc_order_num = dataProduct.getOrder_id();
                        //场数
                        count = dataProduct.getCount();
                        //串
                        strand = dataProduct.getStrand();
                        //多场比赛
                        orders.addAll(dataProduct.getArr());
                        //订单状态
                        mess = dataProduct.getMess();
                    }
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
                lottery = "等待开奖";
                winningIv.setImageResource(R.mipmap.order_wait);
            } else if ("2".equals(openmatch)) {
                lottery = "未中奖";
                winningIv.setImageResource(R.mipmap.order_unwinning);
            } else if ("3".equals(openmatch)) {
                lottery = "中奖";
                winningIv.setImageResource(R.mipmap.order_winning);
                winningMoneyTv.setTextColor(getResources().getColor(R.color.red_ball));
                orderStatusTv.setTextColor(getResources().getColor(R.color.red_ball));
            }
            orderStatusTv.setText(mess);
            nameTv.setText(name);
//            phaseTv.setText("第" + phase + "期");
            orderMoneyTv.setText(pay_money);

            //判断是否中大奖
            if ("-1".equals(winning_money)){
                winningMoneyTv.setText("稍后会有工作人员主动联系您");
            }else {
                winningMoneyTv.setText( winning_money+ "元");
            }

            if ("0".equals(strand)) {
                orderDetailTv1.setText(count + "场，单关，方案" + multiple + "倍");
            } else {
                orderDetailTv1.setText(count + "场，" + strand + "串1，方案" + multiple + "倍");
            }


            orderTimeTv.setText(created_at);
            orderNumberTv.setText(tc_order_num);

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.layout_top_back, R.id.delet, R.id.buy_btn, R.id.choose_detail_ll1,R.id.calculate_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.delet:
                //删除
                showDelet();
                break;
            case R.id.buy_btn:
                ActivityUtils.startActivity(FootBallActivity.class);
                break;
            case R.id.choose_detail_ll1:
                Intent intent = new Intent(this, FootBallOrderItemActivity.class);
                intent.putExtra("data", (Serializable) orders);
                intent.putExtra("type", count + "场  " + strand + "串1  " + multiple + "倍");
                intent.putExtra("lottery", lottery);
                startActivity(intent);
                break;
            case R.id.calculate_rl:
                Intent intent4=new Intent(FootBallOrderDetailActivity.this, WebViewActivity.class);
                intent4.putExtra("url","http://p96a3nm36.bkt.clouddn.com/jczq.jpg");
                intent4.putExtra("title","竞彩足球玩法说明");
                startActivity(intent4);
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
            jsonObject.put("id", id);
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
}
