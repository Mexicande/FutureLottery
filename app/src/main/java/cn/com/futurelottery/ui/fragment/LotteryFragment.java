package cn.com.futurelottery.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.LotteryInformation;
import cn.com.futurelottery.ui.activity.LotteryInformationActivity;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.superLotto_time_tv)
    TextView superLottoTimeTv;
    @BindView(R.id.superLotto_rl1)
    RelativeLayout superLottoRl1;
    @BindView(R.id.superLotto_red_tv1)
    TextView superLottoRedTv1;
    @BindView(R.id.superLotto_red_tv2)
    TextView superLottoRedTv2;
    @BindView(R.id.superLotto_red_tv3)
    TextView superLottoRedTv3;
    @BindView(R.id.superLotto_red_tv4)
    TextView superLottoRedTv4;
    @BindView(R.id.superLotto_red_tv5)
    TextView superLottoRedTv5;
    @BindView(R.id.superLotto_blue_tv1)
    TextView superLottoBlueTv1;
    @BindView(R.id.superLotto_blue_tv2)
    TextView superLottoBlueTv2;
    @BindView(R.id.superLotto_rl)
    RelativeLayout superLottoRl;
    @BindView(R.id.doubleBall_name_tv)
    TextView doubleBallNameTv;
    @BindView(R.id.doubleBall_time_tv)
    TextView doubleBallTimeTv;
    @BindView(R.id.doubleBall_rl1)
    RelativeLayout doubleBallRl1;
    @BindView(R.id.doubleBall_red_tv1)
    TextView doubleBallRedTv1;
    @BindView(R.id.doubleBall_red_tv2)
    TextView doubleBallRedTv2;
    @BindView(R.id.doubleBall_red_tv3)
    TextView doubleBallRedTv3;
    @BindView(R.id.doubleBall_red_tv4)
    TextView doubleBallRedTv4;
    @BindView(R.id.doubleBall_red_tv5)
    TextView doubleBallRedTv5;
    @BindView(R.id.doubleBall_red_tv6)
    TextView doubleBallRedTv6;
    @BindView(R.id.doubleBall_blue_tv1)
    TextView doubleBallBlueTv1;
    @BindView(R.id.doubleBall_rl)
    RelativeLayout doubleBallRl;
    @BindView(R.id.footBall_name_tv)
    TextView footBallNameTv;
    @BindView(R.id.footBall_time_tv)
    TextView footBallTimeTv;
    @BindView(R.id.footBall_rl1)
    RelativeLayout footBallRl1;
    @BindView(R.id.footBall_tv1)
    TextView footBallTv1;
    @BindView(R.id.footBall_tv2)
    TextView footBallTv2;
    @BindView(R.id.footBall_tv3)
    TextView footBallTv3;
    @BindView(R.id.footBall_rl)
    RelativeLayout footBallRl;
    @BindView(R.id.choose5_name_tv)
    TextView choose5NameTv;
    @BindView(R.id.choose5_time_tv)
    TextView choose5TimeTv;
    @BindView(R.id.choose5_rl1)
    RelativeLayout choose5Rl1;
    @BindView(R.id.choose5_red_tv1)
    TextView choose5RedTv1;
    @BindView(R.id.choose5_red_tv2)
    TextView choose5RedTv2;
    @BindView(R.id.choose5_red_tv3)
    TextView choose5RedTv3;
    @BindView(R.id.choose5_red_tv4)
    TextView choose5RedTv4;
    @BindView(R.id.choose5_red_tv5)
    TextView choose5RedTv5;
    @BindView(R.id.choose5_rl)
    RelativeLayout choose5Rl;
    @BindView(R.id.threeD_name_tv)
    TextView threeDNameTv;
    @BindView(R.id.threeD_time_tv)
    TextView threeDTimeTv;
    @BindView(R.id.threeD_rl1)
    RelativeLayout threeDRl1;
    @BindView(R.id.threeD_red_tv1)
    TextView threeDRedTv1;
    @BindView(R.id.threeD_red_tv2)
    TextView threeDRedTv2;
    @BindView(R.id.threeD_red_tv3)
    TextView threeDRedTv3;
    @BindView(R.id.threeD_red_tv4)
    TextView threeDRedTv4;
    @BindView(R.id.threeD_rl)
    RelativeLayout threeDRl;
    @BindView(R.id.quick3_name_tv)
    TextView quick3NameTv;
    @BindView(R.id.quick3_time_tv)
    TextView quick3TimeTv;
    @BindView(R.id.quick3_rl1)
    RelativeLayout quick3Rl1;
    @BindView(R.id.quick3_red_tv1)
    TextView quick3RedTv1;
    @BindView(R.id.quick3_red_tv2)
    TextView quick3RedTv2;
    @BindView(R.id.quick3_red_tv3)
    TextView quick3RedTv3;
    @BindView(R.id.quick3_red_ll)
    LinearLayout quick3RedLl;
    @BindView(R.id.quick3_red_tv4)
    TextView quick3RedTv4;
    @BindView(R.id.quick3_rl)
    RelativeLayout quick3Rl;
    @BindView(R.id.choose9_name_tv)
    TextView choose9NameTv;
    @BindView(R.id.choose9_time_tv)
    TextView choose9TimeTv;
    @BindView(R.id.choose9_rl1)
    RelativeLayout choose9Rl1;
    @BindView(R.id.choose9_tv1)
    TextView choose9Tv1;
    @BindView(R.id.choose9_tv2)
    TextView choose9Tv2;
    @BindView(R.id.choose9_tv3)
    TextView choose9Tv3;
    @BindView(R.id.choose9_tv4)
    TextView choose9Tv4;
    @BindView(R.id.choose9_tv5)
    TextView choose9Tv5;
    @BindView(R.id.choose9_tv6)
    TextView choose9Tv6;
    @BindView(R.id.choose9_tv7)
    TextView choose9Tv7;
    @BindView(R.id.choose9_tv8)
    TextView choose9Tv8;
    @BindView(R.id.choose9_tv9)
    TextView choose9Tv9;
    @BindView(R.id.choose9_tv10)
    TextView choose9Tv10;
    @BindView(R.id.choose9_tv11)
    TextView choose9Tv11;
    @BindView(R.id.choose9_tv12)
    TextView choose9Tv12;
    @BindView(R.id.choose9_tv13)
    TextView choose9Tv13;
    @BindView(R.id.choose9_tv14)
    TextView choose9Tv14;
    @BindView(R.id.choose9_rl)
    RelativeLayout choose9Rl;
    Unbinder unbinder;
    private List<LotteryInformation.DataProduct> lotteryInformations = new ArrayList<>();
    private LotteryInformation.DataProduct superLotto;
    private LotteryInformation.DataProduct doubleBall;
    private LotteryInformation.DataProduct footBall;

    public LotteryFragment() {
        // Required empty public constructor
    }


    @Override
    protected void setTitle() {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_lottery;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取数据
        getData();
        initView();
    }

    private void initView() {
        tvTitle.setText(R.string.lottery_title);
    }

    public void getData() {

        Map<String, String> map = new HashMap<>();

        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Open.open, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    lotteryInformations = gson.fromJson(data.toString(), LotteryInformation.class).getData();
                    updateView();
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

    private void updateView() {
        for (int i=0;i<lotteryInformations.size();i++){
            switch (lotteryInformations.get(i).getLotid()){
                case "ssq":
                    doubleBall=lotteryInformations.get(i);
                    break;
                case "dlt":
                    superLotto=lotteryInformations.get(i);
                    break;
                case "ftb":
                    footBall=lotteryInformations.get(i);
                    break;
            }
        }
        //大乐透
        if (null!=superLotto){
            superLottoTimeTv.setText("第"+superLotto.getPhase()+"期 "+superLotto.getEnd_time()+" ("+superLotto.getWeek()+")");
            String[] ball1 = superLotto.getBonuscode().split(",");
            superLottoRedTv1.setText(ball1[0]);
            superLottoRedTv2.setText(ball1[1]);
            superLottoRedTv3.setText(ball1[2]);
            superLottoRedTv4.setText(ball1[3]);
            superLottoRedTv5.setText(ball1[4]);
            superLottoBlueTv1.setText(ball1[5]);
            superLottoBlueTv2.setText(ball1[6]);
        }

        //双色球
        if (null!=doubleBall){
            doubleBallTimeTv.setText("第"+doubleBall.getPhase()+"期 "+doubleBall.getEnd_time()+" ("+doubleBall.getWeek()+")");
            String[] ball2 = doubleBall.getBonuscode().split(",");
            doubleBallRedTv1.setText(ball2[0]);
            doubleBallRedTv2.setText(ball2[1]);
            doubleBallRedTv3.setText(ball2[2]);
            doubleBallRedTv4.setText(ball2[3]);
            doubleBallRedTv5.setText(ball2[4]);
            doubleBallRedTv6.setText(ball2[5]);
            doubleBallBlueTv1.setText(ball2[6]);
        }
        //足彩
        if (null!=footBall){
            footBallTv1.setText(footBall.getFront());
            footBallTv2.setText(footBall.getResult());
            footBallTv3.setText(footBall.getAfter());
        }

    }

    @OnClick({R.id.superLotto_rl, R.id.doubleBall_rl, R.id.footBall_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.superLotto_rl:
                Intent intent=new Intent(getContext(),LotteryInformationActivity.class);
                intent.putExtra("type",1);
                getContext().startActivity(intent);
                break;
            case R.id.doubleBall_rl:
                Intent intent1=new Intent(getContext(),LotteryInformationActivity.class);
                intent1.putExtra("type",0);
                getContext().startActivity(intent1);
                break;
            case R.id.footBall_rl:
                break;
        }
    }
}
