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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FooterAllEvent;
import cn.com.futurelottery.presenter.FooterOneEvent;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 * @author apple
 * 让平负 过关
 */
public class ConAllPassFragment extends BaseFragment {

    @BindView(R.id.conAllRecycler)
    RecyclerView mAllRecycler;
    private WinAndLoseAdapter mWinAndLoseAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    private boolean mTrue =false;
    private int nu=0;

    public ConAllPassFragment() {
        // Required empty public constructor
    }



    @Override
    public int getLayoutResource() {
        return R.layout.fragment_con_all_pass;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();

    }

    private void setListener() {

            mWinAndLoseAdapter.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
                @Override
                public void onTopRightMenuItemClick(int position) {
                    update();
                }
            });
    }
    private void update() {
        nu=0;
        for (int i = 0; i < beans.size(); i++) {
            FootBallList.DataBean dataBean = beans.get(i);
            for(int j=0;j<dataBean.getMatch().size();j++){
                FootBallList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                if(matchBean.getAwayType()==1||matchBean.getHomeType()==1||matchBean.getVsType()==1){
                    nu++;
                }
            }
        }
        EventBus.getDefault().post(new FooterOneEvent(nu,3));
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type){
        if(type.getmMeeage()==3){
            for (int i = 0; i < beans.size(); i++) {
                FootBallList.DataBean dataBean = beans.get(i);
                for(int j=0;j<dataBean.getMatch().size();j++){
                    FootBallList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                    if(matchBean.getAwayType()==1||matchBean.getHomeType()==1||matchBean.getVsType()==1){
                        matchBean.setHomeType(0);
                        matchBean.setVsType(0);
                        matchBean.setAwayType(0);

                    }
                }
            }
            mWinAndLoseAdapter.notifyDataSetChanged();
        }
        mTrue=false;
    }

    private void getDate() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.pass_rules_1);
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
                mAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAllRecycler.setAdapter(mWinAndLoseAdapter);
                ((SimpleItemAnimator)mAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                setListener();

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }

}
