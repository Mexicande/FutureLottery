package cn.com.futurelottery.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
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
    @BindView(R.id.choose_detail_number_tv)
    TextView chooseDetailNumberTv;
    @BindView(R.id.choose_detail_type_tv)
    TextView chooseDetailTypeTv;
    @BindView(R.id.choose_detail_ll2)
    LinearLayout chooseDetailLl2;
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
    private String id;
    private String iconURL;
    private String name;
    private String phase;
    private String pay_money;
    private String openmatch;
    private String winning_money;
    private String bonuscode;
    private String multiple;
    private String chooseBalls;
    private String play_type;
    private String created_at;
    private String tc_order_num;
    private String ballName;
    private boolean flag = false;
    private AlertDialog alertDialog;

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
        RoteteUtils.rotateArrow(dropDownIv, flag);
        flag = !flag;
    }

    private void getData() {
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
                    iconURL = jo.getString("logo");
                    name = jo.getString("name");
                    phase = jo.getString("phase");
                    pay_money = jo.getString("pay_money");
                    //中奖状态
                    openmatch = jo.getString("openmatch");
                    //中奖额度
                    winning_money = jo.getString("winning_money");
                    //开奖号码
                    bonuscode = jo.getString("bonuscode");
                    //倍数
                    multiple = jo.getString("multiple");
                    //选号
                    chooseBalls = jo.getString("data");
                    //玩法类型
                    play_type = jo.getString("play_type");
                    //下单时间
                    created_at = jo.getString("created_at");
                    //订单编号
                    tc_order_num = jo.getString("tc_order_num");
                    //订单编号
                    tc_order_num = jo.getString("tc_order_num");


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
            phaseTv.setText("第" + phase + "期");
            orderMoneyTv.setText(pay_money);
            if (TextUtils.isEmpty(winning_money)) {
                winningMoneyTv.setText("0.00");
            } else {
                winningMoneyTv.setText(winning_money);
            }

            String[] arr = bonuscode.split("#");
            orderDetailTv1.setText(arr[0].replace(",", " "));
            orderDetailTv2.setText(arr[1].replace(",", " "));

            String type = "";
            switch (play_type) {
                case "101":
                    type = "单式";
                    break;
                case "102":
                    type = "复式";
                    break;
                case "103":
                    type = "胆拖";
                    break;
                case "104":
                    type = "追加复式";
                    break;
                case "106":
                    type = "胆拖";
                    break;
                case "107":
                    type = "追加胆拖";
                    break;
            }
            chooseDetailNumberTv.setText(type + "\n" + multiple + "倍");

            chooseBalls.replace(",", " ");
            String[] arrBalls = chooseBalls.split("#");
            if (!chooseBalls.contains("$")) {
                chooseDetailTypeTv.setText(arrBalls[0] + ":" + arrBalls[1]);
            } else {
                String[] arrRedBalls = arrBalls[0].split("$");
                String[] arrBlueBalls;
                if (!arrRedBalls[1].contains("$")) {
                    chooseDetailTypeTv.setText("(" + arrRedBalls[0] + ")" + arrRedBalls[1] + ":" + arrBalls[1]);
                } else {
                    arrBlueBalls = arrBalls[1].split("$");
                    chooseDetailTypeTv.setText("(" + arrRedBalls[0] + ":" + arrBlueBalls[0] + ")" + arrRedBalls[1] + ":" + arrBlueBalls[1]);
                }

            }
            orderTimeTv.setText(created_at);
            orderNumberTv.setText(tc_order_num);


        } catch (Resources.NotFoundException e) {
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
                if (chooseDetailLl2.getVisibility() == View.GONE) {
                    chooseDetailLl2.setVisibility(View.VISIBLE);
                } else {
                    chooseDetailLl2.setVisibility(View.GONE);
                }
                break;
            case R.id.calculate_rl:
                break;
            case R.id.continue_btn:
                break;
            case R.id.buy_btn:
                //投注
                if ("双色球".equals(ballName)){
                    ActivityUtils.startActivity(DoubleBallActivity.class);
                }else if ("大乐透".equals(ballName)){
                    ActivityUtils.startActivity(SuperLottoActivity.class);
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
                    if (0==code){
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
