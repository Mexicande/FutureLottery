package cn.com.futurelottery.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.model.DoubleBall;
import cn.com.futurelottery.ui.adapter.ChooseBallPaymentAdapter;
import cn.com.futurelottery.ui.adapter.DoubleBallBlueAdapter;
import cn.com.futurelottery.utils.RandomMadeBall;
import cn.com.futurelottery.utils.StatusBarUtil;
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
    @BindView(R.id.agreement_cb)
    CheckBox agreementCb;
    @BindView(R.id.agreement_tv)
    TextView agreementTv;
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
    private ArrayList<DoubleBall> balls=new ArrayList<>();
    private ChooseBallPaymentAdapter adapter;
    private long zhushu=0;
    private long money=0;
    private long periods=0;
    private long multiple=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
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
                break;
            case R.id.bottom_result_btn:
                pay();
                break;
        }
    }

    private void pay() {


//        JSONArray ja=new JSONArray();
//        Map<String,String> map=new HashMap<>();
//        map.put("register_phone",phone);
//        map.put("password",password1);
//        map.put("terminal", Constants.channel);
//        map.put("nid", Constants.channel1);
//        JSONObject jsonObject=new JSONObject(map);
//
//        OkGo.post(Constants.commonURL + Constants.login)
//                .tag(this)
//                .upJson(jsonObject)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, okhttp3.Response response) {
//                        LogcatUtil.printLogcat( s);
//                        Gson gson = new Gson();
//                        LoginMessage loginMessage = gson.fromJson(s, LoginMessage.class);
//
//                        if (loginMessage.getError_code() == 0) {
//                            String token = loginMessage.getData().getToken();
//                        } else {
//                            Toast.makeText(LoginActivity.this, loginMessage.getError_message(), Toast.LENGTH_LONG).show();
//
//                        }
//                        hd.dismiss();
//                    }
//
//                    @Override
//                    public void onError(Call call, okhttp3.Response response, Exception e) {
//                        Toast.makeText(LoginActivity.this, "请求失败", Toast.LENGTH_LONG).show();
//                        hd.dismiss();
//                        super.onError(call, response, e);
//                    }
//                });
    }

    //机选一注
    private void randomChoose() {
        ArrayList<String> randomRed = RandomMadeBall.getManyBall(33, 6);
        ArrayList<String> randomBlue = RandomMadeBall.getOneBall(16);
        DoubleBall db=new DoubleBall();
        String red="";
        String blue="";
        for (int i=0;i<randomRed.size();i++){
            int number = Integer.parseInt(randomRed.get(i))+ 1;
            if (i==0){
                red=red+((number<10)?("0"+number):number);
            }else {
                red=red+","+((number<10)?("0"+number):number);
            }
        }
        int number = Integer.parseInt(randomBlue.get(0))+ 1;
        blue=""+((number<10)?("0"+number):number);
        db.setType(0);
        db.setRed(red);
        db.setBlu(blue);
        db.setZhushu(1);
        db.setMoney(2);
        zhushu+=1;
        money+=2;
        balls.add(0,db);
        adapter.notifyDataSetChanged();
        //列表是否显示
        listIsShow();
        //显示
        show();
    }

    private void show() {
        bottomResultCountTv.setText(zhushu+"注"+periods+"期"+multiple+"倍");
        bottomResultMoneyTv.setText(money+"");
    }

    //选球列表是否显示
    private void listIsShow() {
        if (balls.size()>0){
            chooseZhuRv.setVisibility(View.VISIBLE);
            chooseNullTv.setVisibility(View.GONE);
        }else {
            chooseZhuRv.setVisibility(View.GONE);
            chooseNullTv.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        adapter=new ChooseBallPaymentAdapter(balls);
        chooseZhuRv.setLayoutManager(new LinearLayoutManager(this));
        chooseZhuRv.setAdapter(adapter);
    }




}
