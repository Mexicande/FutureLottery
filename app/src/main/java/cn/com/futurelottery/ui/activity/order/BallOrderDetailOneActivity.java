package cn.com.futurelottery.ui.activity.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.ChaseOrder;
import cn.com.futurelottery.ui.activity.DoubleBallActivity;
import cn.com.futurelottery.ui.activity.SuperLottoActivity;
import cn.com.futurelottery.ui.activity.arrange.Line3Activity;
import cn.com.futurelottery.ui.activity.arrange.Line5Activity;
import cn.com.futurelottery.ui.activity.arrange.Lottery3DActivity;
import cn.com.futurelottery.ui.adapter.ChaseOrderAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;

public class BallOrderDetailOneActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.icon_iv)
    ImageView iconIv;
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
    @BindView(R.id.order_detail_iv)
    ImageView orderDetailIv;
    @BindView(R.id.have_chased_tv)
    TextView haveChasedTv;
    @BindView(R.id.wait_chased_tv)
    TextView waitChasedTv;
    @BindView(R.id.have_chased_rv)
    RecyclerView haveChasedRv;
    @BindView(R.id.order_detail_tv1)
    TextView orderDetailTv;
    @BindView(R.id.delet)
    TextView delet;
    @BindView(R.id.continue_btn)
    Button continueBtn;
    @BindView(R.id.buy_btn)
    Button buyBtn;
    @BindView(R.id.drop_down_iv)
    ImageView dropDownIv;
    @BindView(R.id.nest_sv)
    NestedScrollView nestSv;
    @BindView(R.id.order_status_tv)
    TextView orderStatusTv;
    @BindView(R.id.winning_iv)
    ImageView winningIv;
    private String id;
    private String iconURL;
    private String name;
    private String pay_money;
    private String winning_money;
    private String ballName;
    private boolean flag = false;
    private AlertDialog alertDialog;
    private ChaseOrder.DataProduct data1;
    private String already_periods;
    private String remaining_periods;
    private String total_periods;
    private ArrayList<ChaseOrder.DataProduct.InfoProduct> info = new ArrayList<>();
    private String stop_money;
    private int tip;
    private ChaseOrderAdapter chaseOrderAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_ball_order_detail_one;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
        setListener();
    }

    private void setListener() {
        chaseOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(BallOrderDetailOneActivity.this, BallOrderDetailActivity.class);
                intent.putExtra("id", info.get(position).getOrder_id());
                intent.putExtra("lotid", info.get(position).getLotid());
                intent.putExtra("type", TextUtils.isEmpty(info.get(position).getChasing_id()) ? "普通投注" : "追号投注");
                intent.putExtra("ballName", data1.getName());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        nestSv.setNestedScrollingEnabled(false);

        chaseOrderAdapter = new ChaseOrderAdapter(info);
        haveChasedRv.setLayoutManager(new LinearLayoutManager(this));
        haveChasedRv.setAdapter(chaseOrderAdapter);
    }

    private void getData() {
        info.clear();
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
        ApiService.GET_SERVICE(Api.Order.chasing, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {

                    Gson gson = new Gson();
                    ChaseOrder chaseOrder = gson.fromJson(data.toString(), ChaseOrder.class);
                    data1 = chaseOrder.getData();

                    iconURL = data1.getLogo();
                    name = data1.getName();
                    pay_money = data1.getPay_money_total();
                    //中奖额度
                    winning_money = data1.getWinning_money();
                    stop_money = data1.getStop_money();

                    already_periods = data1.getAlready_periods();
                    remaining_periods = data1.getRemaining_periods();
                    total_periods = data1.getTotal_periods();

                    info.addAll(data1.getInfo());

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
            chaseOrderAdapter.notifyDataSetChanged();

            Glide.with(this).load(iconURL)
                    .apply(new RequestOptions())
                    .into(iconIv);

            nameTv.setText(name);
            phaseTv.setText("追号共" + total_periods + "期");
            orderMoneyTv.setText(pay_money+"元");


            //判断是否中大奖
            if ("-1".equals(winning_money)){
                winningMoneyTv.setText("稍后会有工作人员主动联系您");
            }else {
                winningMoneyTv.setText( winning_money+ "元");
            }


            //订单状态
            if ("2".equals(data1.getStatus())) {
                winningIv.setImageResource(R.mipmap.order_wait);
                orderStatusTv.setText("正在委托中");
            } else if ("3".equals(data1.getStatus())) {
                winningIv.setImageResource(R.mipmap.order_wait);
                orderStatusTv.setText("委托失败");
            } else {
                if ("0".equals(data1.getOpenmatch())) {
                    winningIv.setImageResource(R.mipmap.order_wait);
                    orderStatusTv.setText("等待开奖");
                } else if ("2".equals(data1.getOpenmatch())) {
                    winningIv.setImageResource(R.mipmap.order_unwinning);
                    orderStatusTv.setText("未中奖");
                }
                if ("3".equals(data1.getOpenmatch())) {
                    winningIv.setImageResource(R.mipmap.order_winning);
                    winningMoneyTv.setTextColor(getResources().getColor(R.color.red_ball));
                    orderStatusTv.setTextColor(getResources().getColor(R.color.red_ball));
                    orderStatusTv.setText("中奖");
                }
            }

            //停追条件
            if (TextUtils.isEmpty(stop_money) || "0".equals(stop_money) || "0.00".equals(stop_money)) {
                orderDetailTv.setText("到期后停止");
                tip = 0;
            } else {
                tip = 1;
                orderDetailTv.setText("累计金额" + stop_money + "停止");
            }
            haveChasedTv.setText(already_periods);
            waitChasedTv.setText(remaining_periods);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showTipDialog(String tital, String content) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        alertDialog1.setCancelable(false);
        alertDialog1.setCanceledOnTouchOutside(false);
        alertDialog1.show();
        Window window1 = alertDialog1.getWindow();
        window1.setContentView(R.layout.tip_dialog);
        TextView tvTip = (TextView) window1.findViewById(R.id.tips_tv);
        TextView tvContent = (TextView) window1.findViewById(R.id.tips_tv_content);
        TextView tvClick = (TextView) window1.findViewById(R.id.click_tv);
        tvTip.setText(tital);
        tvContent.setText(content);
        tvClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });
    }

    @OnClick({R.id.order_detail_iv, R.id.layout_top_back, R.id.delet, R.id.continue_btn, R.id.buy_btn, R.id.have_chased_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_detail_iv:
                if (0 == tip) {
                    showTipDialog("什么是到期后停止", "您的追号方案会按照您设定的期次进行完毕，不会因某一期中奖而停止");
                } else {
                    showTipDialog("什么是累计金额停止", "当累计的中奖金额大于您设定的金额后，后续的期次将被撤销，资金返还到您的账户中。");
                }
                break;
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.delet:
                //删除
                showDelet();
                break;
            case R.id.have_chased_ll:
                RoteteUtils.rotateArrow(dropDownIv, flag);
                flag = !flag;
                if (haveChasedRv.getVisibility() == View.GONE) {
                    haveChasedRv.setVisibility(View.VISIBLE);
                } else {
                    haveChasedRv.setVisibility(View.GONE);
                }
                break;
            case R.id.continue_btn:
                break;
            case R.id.buy_btn:
                //投注
                if ("双色球".equals(ballName)) {
                    ActivityUtils.startActivity(DoubleBallActivity.class);
                } else if ("大乐透".equals(ballName)) {
                    ActivityUtils.startActivity(SuperLottoActivity.class);
                } else if ("排列3".equals(ballName)) {
                    ActivityUtils.startActivity(Line3Activity.class);
                } else if ("排列5".equals(ballName)) {
                    ActivityUtils.startActivity(Line5Activity.class);
                } else if ("3D".equals(ballName)) {
                    ActivityUtils.startActivity(Lottery3DActivity.class);
                }
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
