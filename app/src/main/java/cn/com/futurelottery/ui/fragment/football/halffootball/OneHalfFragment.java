package cn.com.futurelottery.ui.fragment.football.halffootball;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.ui.adapter.football.HalfAdapter;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.LogUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         单关
 */
public class OneHalfFragment extends BaseFragment {

    @BindView(R.id.oneHalfAllRecycler)
    RecyclerView oneHalfAllRecycler;
    private HalfAdapter mHalfAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;

    public OneHalfFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_one_half;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();
    }

    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.pass_rules_o);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE, Api.FOOTBALL.FT004);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.GET_SERVICE(Api.FootBall_Api.FOOTBALL_LSIT, getActivity(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson = new Gson();
                FootBallList footBallList = gson.fromJson(data.toString(), FootBallList.class);
                beans = footBallList.getData();
                res = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    FootBallList.DataBean dataBean = beans.get(i);
                    dataBean.setSubItems(beans.get(i).getMatch());
                    res.add(dataBean);
                }
                mHalfAdapter = new HalfAdapter(res);
                oneHalfAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                oneHalfAllRecycler.setAdapter(mHalfAdapter);
                ((SimpleItemAnimator) oneHalfAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                mHalfAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (FootBallList.DataBean s : beans) {
                            LogUtils.i(s.toString());
                        }
                    }
                });

                mHalfAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ScoreDialogFragment scoreDialogFragment = new ScoreDialogFragment();
                        scoreDialogFragment.show(getChildFragmentManager(), "scoreDialogFragment");
                    }
                });

            }

            @Override
            public void requestFailure(int code, String msg) {


            }
        });
    }

}
