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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

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
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.OrderDetail;
import cn.com.futurelottery.ui.activity.DoubleBallActivity;
import cn.com.futurelottery.ui.activity.SuperLottoActivity;
import cn.com.futurelottery.ui.activity.WebViewActivity;
import cn.com.futurelottery.ui.activity.arrange.Line3Activity;
import cn.com.futurelottery.ui.activity.arrange.Line5Activity;
import cn.com.futurelottery.ui.activity.arrange.Lottery3DActivity;
import cn.com.futurelottery.ui.adapter.OrderInfoAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.RoteteUtils;
import cn.com.futurelottery.utils.ToastUtils;

public class BallOrderDetailActivity extends BaseActivity {

    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.question_mark_iv)
    ImageView questionMarkIv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
    @BindView(R.id.winning_iv)
    ImageView winningIv;
    @BindView(R.id.order_status_tv)
    TextView orderStatusTv;
    @BindView(R.id.order_detail_tv1)
    TextView orderDetailTv1;
    @BindView(R.id.order_detail_tv2)
    TextView orderDetailTv2;
    @BindView(R.id.choose_detail_tv)
    TextView chooseDetailTv;
    @BindView(R.id.choose_detail_ll1)
    LinearLayout chooseDetailLl1;
    @BindView(R.id.calculate_rl)
    RelativeLayout calculateRl;
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
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.drop_down_iv)
    ImageView dropDownIv;
    @BindView(R.id.information_rv)
    RecyclerView informationRv;
    @BindView(R.id.nest_sv)
    NestedScrollView nestSv;
    private String id;
    private String ballName;
    private boolean flag = false;
    private AlertDialog alertDialog;
    private OrderDetail orderDetsil;
    private int position;
    private List<OrderDetail.DataProduct.InfoProduct> infos = new ArrayList<>();
    private String lotid;
    private OrderInfoAdapter infoAdapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_ball_order_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initView();
    }

    private void initView() {
        nestSv.setNestedScrollingEnabled(false);
        RoteteUtils.rotateArrow(dropDownIv, flag);
        flag = !flag;

        infoAdapter = new OrderInfoAdapter(infos, lotid);
        informationRv.setLayoutManager(new LinearLayoutManager(this));
        informationRv.setAdapter(infoAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String type = intent.getStringExtra("type");
        lotid = intent.getStringExtra("lotid");
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
                    Gson gson = new Gson();
                    orderDetsil = gson.fromJson(data.toString(), OrderDetail.class);
                    infos.clear();
                    infos.addAll(orderDetsil.getData().getInfo());
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
            OrderDetail.DataProduct data = orderDetsil.getData();
            if (null != data) {
                Glide.with(this).load(data.getLogo())
                        .apply(new RequestOptions())
                        .into(iconIv);

                if ("2".equals(data.getStatus())) {
                    winningIv.setImageResource(R.mipmap.order_wait);
                    orderStatusTv.setText("正在委托中");
                } else if ("3".equals(data.getStatus())) {
                    winningIv.setImageResource(R.mipmap.order_wait);
                    orderStatusTv.setText("委托失败");
                } else {
                    if ("0".equals(data.getOpenmatch())) {
                        winningIv.setImageResource(R.mipmap.order_wait);
                        orderStatusTv.setText("等待开奖");
                    } else if ("2".equals(data.getOpenmatch())) {
                        winningIv.setImageResource(R.mipmap.order_unwinning);
                        orderStatusTv.setText("未中奖");
                    }
                    if ("3".equals(data.getOpenmatch())) {
                        winningIv.setImageResource(R.mipmap.order_winning);
                        winningMoneyTv.setTextColor(getResources().getColor(R.color.red_ball));
                        orderStatusTv.setTextColor(getResources().getColor(R.color.red_ball));
                        orderStatusTv.setText("中奖");
                    }
                }

                nameTv.setText(data.getName());
                phaseTv.setText("第" + data.getPhase() + "期");
                orderMoneyTv.setText(data.getPay_money_total()+"元");

                //判断是否中大奖
                if ("-1".equals(data.getWinning_money())){
                    winningMoneyTv.setText("稍后会有工作人员主动联系您");
                }else {
                    winningMoneyTv.setText( data.getWinning_money()+ "元");
                }

                //显示开奖信息
                if (TextUtils.isEmpty(data.getBonuscode())){
                    orderDetailTv1.setText("暂未开奖");
                }else {
                    String[] arr = data.getBonuscode().split("#");
                    if (arr.length > 1) {
                        orderDetailTv1.setText(arr[0].replace(",", " "));
                        orderDetailTv2.setText(arr[1].replace(",", " "));
                    } else {
                        orderDetailTv1.setText(arr[0].replace(",", " "));
                    }
                }


                chooseDetailTv.setText(infos.size()+"条");
                //投注信息
                infoAdapter.notifyDataSetChanged();

                orderTimeTv.setText(data.getCreated_at());
                orderNumberTv.setText(data.getOrder_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.layout_top_back, R.id.delet, R.id.continue_btn, R.id.buy_btn, R.id.choose_detail_ll1, R.id.calculate_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_top_back:
                finish();
                break;
            case R.id.delet:
                //删除
                showDelet();
                break;
            case R.id.choose_detail_ll1:
                RoteteUtils.rotateArrow(dropDownIv, flag);
                flag = !flag;
                if (informationRv.getVisibility() == View.GONE) {
                    informationRv.setVisibility(View.VISIBLE);
                } else {
                    informationRv.setVisibility(View.GONE);
                }
                break;
            case R.id.calculate_rl:
                Intent intent=new Intent(BallOrderDetailActivity.this, WebViewActivity.class);
                switch (lotid){
                    case "ssq":
                        intent.putExtra("url","http://p96a3nm36.bkt.clouddn.com/ssq.jpg");
                        intent.putExtra("title","双色球玩法说明");
                        break;
                    case "dlt":
                        intent.putExtra("url","http://p96a3nm36.bkt.clouddn.com/dlt.jpg");
                        intent.putExtra("title","大乐透玩法说明");
                        break;
                    case "p3":
                        intent.putExtra("url","http://p96a3nm36.bkt.clouddn.com/p3.jpg");
                        intent.putExtra("title","排列3玩法说明");
                        break;
                    case "p5":
                        intent.putExtra("url","http://p96a3nm36.bkt.clouddn.com/p5.jpg");
                        intent.putExtra("title","排列5玩法说明");
                        break;
                    case "3d":
                        intent.putExtra("url","http://p96a3nm36.bkt.clouddn.com/fc3d.jpg");
                        intent.putExtra("title","3D玩法说明");
                        break;
                }
                startActivity(intent);
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
