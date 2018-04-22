package cn.com.futurelottery.ui.fragment.football.conwinandlose;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.utils.LogUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         让平负 单关
 */
public class ConOnePassFragment extends BaseFragment {


    @BindView(R.id.conOneRecycler)
    RecyclerView conOneRecycler;
    private WinAndLoseAdapter mWinAndLoseAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;

    public ConOnePassFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_con_one_pass;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();
    }

    private void getDate() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.pass_rules_o);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE,Api.FOOTBALL.FT006);
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
                conOneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                conOneRecycler.setAdapter(mWinAndLoseAdapter);
                ((SimpleItemAnimator)conOneRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
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
