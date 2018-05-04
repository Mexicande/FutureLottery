package cn.com.futurelottery.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.LotteryInformation;
import cn.com.futurelottery.ui.activity.LotteryInformationActivity;
import cn.com.futurelottery.ui.activity.runlottery.FootRunActivity;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class LotteryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.superLotto_time_tv)
    TextView superLottoTimeTv;
    @BindView(R.id.doubleBall_time_tv)
    TextView doubleBallTimeTv;
    @BindView(R.id.footBall_tv1)
    TextView footBallTv1;
    @BindView(R.id.footBall_tv2)
    TextView footBallTv2;
    @BindView(R.id.footBall_tv3)
    TextView footBallTv3;
    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;
    @BindView(R.id.dlt_FlexboxLayout)
    FlexboxLayout dltFlexboxLayout;
    private List<LotteryInformation.DataProduct> lotteryInformations = new ArrayList<>();
    private LotteryInformation.DataProduct superLotto;
    private LotteryInformation.DataProduct doubleBall;
    private LotteryInformation.DataProduct footBall;
    public LotteryFragment() {
        // Required empty public constructor
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
        for (int i = 0; i < lotteryInformations.size(); i++) {
            switch (lotteryInformations.get(i).getLotid()) {
                case "ssq":
                    superLotto = lotteryInformations.get(i);
                    textFlex(superLotto, 1, flexboxLayout);
                    doubleBallTimeTv.setText("第" + doubleBall.getPhase() + "期 " + doubleBall.getEnd_time() + " (" + doubleBall.getWeek() + ")");
                    break;
                case "dlt":
                    doubleBall = lotteryInformations.get(i);
                    superLottoTimeTv.setText("第" + superLotto.getPhase() + "期 " + superLotto.getEnd_time() + " (" + superLotto.getWeek() + ")");
                    textFlex(doubleBall, 2, dltFlexboxLayout);
                    break;
                case "ftb":
                    footBall = lotteryInformations.get(i);
                    break;
                default:
                    break;
            }
        }
        //足彩
        if (null != footBall) {
            footBallTv1.setText(footBall.getFront());
            footBallTv2.setText(footBall.getResult());
            footBallTv3.setText(footBall.getAfter());
        }

    }

    private void textFlex(LotteryInformation.DataProduct dataProduct, int type, FlexboxLayout mFlexboxLayout) {
        String bonuscode = dataProduct.getBonuscode();
        String[] split = bonuscode.split(",");

        List<String> strings = Arrays.asList(split);
        for (int i = 0; i < strings.size(); i++) {
            TextView textView = new TextView(getActivity());
            textView.setText(strings.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5, 5, 5, 5);
            textView.setTextColor(getResources().getColor(R.color.white));
            mFlexboxLayout.addView(textView);
            if (type == 1) {
                if (i < strings.size() - 1) {
                    textView.setBackground(getResources().getDrawable(R.drawable.lottery_fragment_redball));
                } else {
                    textView.setBackground(getResources().getDrawable(R.drawable.lottery_fragment_blueball));
                }
            } else if (type == 2) {
                if (i < strings.size() - 2) {
                    textView.setBackground(getResources().getDrawable(R.drawable.lottery_fragment_redball));
                } else {
                    textView.setBackground(getResources().getDrawable(R.drawable.lottery_fragment_blueball));
                }
            }
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                layoutParams.setFlexGrow(0f);
                if(i==strings.size()-type){
                    layoutParams.setMargins(50, 0, 10, 0);
                }else {
                    layoutParams.setMargins(10, 0, 10, 0);
                }
            }
        }

    }


    @OnClick({R.id.superLotto_rl, R.id.doubleBall_rl, R.id.footBall_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.superLotto_rl:
                Intent intent = new Intent(getContext(), LotteryInformationActivity.class);
                intent.putExtra("type", 1);
                getContext().startActivity(intent);
                break;
            case R.id.doubleBall_rl:
                Intent intent1 = new Intent(getContext(), LotteryInformationActivity.class);
                intent1.putExtra("type", 0);
                getContext().startActivity(intent1);
                break;
            case R.id.footBall_rl:
                ActivityUtils.startActivity(FootRunActivity.class);
                break;
            default:
                break;
        }
    }
}
