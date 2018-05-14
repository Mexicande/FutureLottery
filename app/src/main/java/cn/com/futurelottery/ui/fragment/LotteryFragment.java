package cn.com.futurelottery.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.LotteryInformation;
import cn.com.futurelottery.ui.activity.LotteryInformationActivity;
import cn.com.futurelottery.ui.activity.runlottery.FootRunActivity;
import cn.com.futurelottery.ui.adapter.LotteryFragmentAdapter;
import cn.com.futurelottery.utils.ActivityUtils;
import cn.com.futurelottery.utils.ToastUtils;

/**
 * 开奖信息
 */
public class LotteryFragment extends BaseFragment {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lottery_rv)
    RecyclerView lotteryRv;
    private List<LotteryInformation.DataProduct> lotteryInformations = new ArrayList<>();
    private LotteryFragmentAdapter adapter;

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
        setListener();
    }

    private void setListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LotteryInformation.DataProduct item = lotteryInformations.get(position);
                switch (item.getLotid()) {
                    case "ssq":
                        Intent intent = new Intent(getContext(), LotteryInformationActivity.class);
                        intent.putExtra("type", item.getLotid());
                        getContext().startActivity(intent);
                        break;
                    case "dlt":
                        Intent intent1 = new Intent(getContext(), LotteryInformationActivity.class);
                        intent1.putExtra("type", item.getLotid());
                        getContext().startActivity(intent1);
                        break;
                    case "p3":
                        Intent intent2 = new Intent(getContext(), LotteryInformationActivity.class);
                        intent2.putExtra("type", item.getLotid());
                        getContext().startActivity(intent2);
                        break;
                    case "p5":
                        Intent intent3 = new Intent(getContext(), LotteryInformationActivity.class);
                        intent3.putExtra("type", item.getLotid());
                        getContext().startActivity(intent3);
                        break;
                    case "3d":
                        Intent intent4 = new Intent(getContext(), LotteryInformationActivity.class);
                        intent4.putExtra("type", item.getLotid());
                        getContext().startActivity(intent4);
                        break;
                    case "ftb":
                        ActivityUtils.startActivity(FootRunActivity.class);
                        break;
                }
            }
        });
    }


    private void initView() {
        tvTitle.setText(R.string.lottery_title);
        adapter = new LotteryFragmentAdapter(lotteryInformations);
        lotteryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        lotteryRv.setAdapter(adapter);
    }

    public void getData() {
        lotteryInformations.clear();
        Map<String, String> map = new HashMap<>();

        JSONObject jsonObject = new JSONObject(map);
        ApiService.GET_SERVICE(Api.Open.open, getContext(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    Gson gson = new Gson();
                    lotteryInformations.addAll(gson.fromJson(data.toString(), LotteryInformation.class).getData());
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



}
