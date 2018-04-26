package cn.com.futurelottery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseActivity;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.BallInformation;
import cn.com.futurelottery.ui.adapter.BallInformationAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.ToastUtils;

public class LotteryInformationActivity extends BaseActivity {


    @BindView(R.id.layout_top_back)
    ImageView layoutTopBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.bet_tv)
    TextView betTv;
    //0双色球1大乐透
    private int type;
    private ArrayList<BallInformation> ballformations=new ArrayList<>();
    private BallInformationAdapter adapter;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_lottery_information;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getData();
    }

    private void initView() {
        adapter=new BallInformationAdapter(ballformations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Intent intent = getIntent();
        type=intent.getIntExtra("type",0);
        if (type==0){
            tvTitle.setText("双色球");
            betTv.setText("投注双色球");
        }else if (type==1){
            betTv.setText("投注大乐透");
            tvTitle.setText("大乐透");
        }
        getInformation();
    }

    private void getInformation() {
        Map<String, String> map = new HashMap<>();
        map.put("type",type==0?"ssq":"dlt");

        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Open.openssq, this, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    Type bannerType=new TypeToken<ArrayList<BallInformation>>(){}.getType();
                    ballformations.addAll((ArrayList<BallInformation>)gson.fromJson(data.getJSONArray("data").toString(),bannerType));
                    adapter.notifyDataSetChanged();
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

    @OnClick({R.id.bet_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bet_tv:
                if (type==0){
                    ActivityUtils.startActivity(DoubleBallActivity.class);
                }else if (type==1){
                    ActivityUtils.startActivity(SuperLottoActivity.class);
                }
                break;
            case R.id.layout_top_back:
                finish();
                break;
        }
    }
}
