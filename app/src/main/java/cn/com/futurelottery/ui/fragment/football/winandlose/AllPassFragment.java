package cn.com.futurelottery.ui.fragment.football.winandlose;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.presenter.FooterTypeEvent;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         过关 football
 */
public class AllPassFragment extends BaseFragment {


    @BindView(R.id.AllRecycler)
    RecyclerView mAllRecycler;
    private WinAndLoseAdapter mWinAndLoseAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    public AllPassFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_pass;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();
        initView();
        setListener();
    }

    private void setListener() {

    }

    private void initView() {


    }

    private void getDate() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE,Api.FOOTBALL.pass_rules_1);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE,Api.FOOTBALL.FT001);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Api.FootBall_Api.FOOTBALL_LSIT, getActivity(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson=new Gson();
                FootBallList footBallList = gson.fromJson(data.toString(), FootBallList.class);
                 beans = footBallList.getData();
                res = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    FootBallList.DataBean dataBean = beans.get(i);
                    dataBean.setSubItems(beans.get(i).getMatch());
                    res.add(dataBean);
                }
                mWinAndLoseAdapter = new WinAndLoseAdapter(res);
                mAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAllRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                mAllRecycler.setAdapter(mWinAndLoseAdapter);
                ((SimpleItemAnimator)mAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                mWinAndLoseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for( FootBallList.DataBean s :beans){
                            LogUtils.i(s.toString());
                        }
                    }
                });

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }


}
