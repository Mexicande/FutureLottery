package cn.com.futurelottery.ui.fragment.football.conwinandlose;


import android.content.Intent;
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

import java.io.Serializable;
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
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.presenter.FooterOneEvent;
import cn.com.futurelottery.ui.activity.Football.FootAllBetActivity;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;


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
    private int nu=0;
    private boolean mTrue =false;
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
        EventBus.getDefault().post(new FooterOneEvent(nu,4));
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
        if(type.getmMeeage()==4){
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


    /**
     * 提交
     */
    @Subscribe
    public void nextSubmit(FootSureType type){
        if(type.getmType()==4){
            nextDate();
            if(mTrue){
                Intent intent=new Intent(getActivity(),FootAllBetActivity.class);
                intent.putExtra("type",4);
                List<FootBallList.DataBean.MatchBean> list=new ArrayList<>();
                for(FootBallList.DataBean s:beans){
                    for(int i=0;i<s.getMatch().size();i++){
                        FootBallList.DataBean.MatchBean matchBean = s.getMatch().get(i);
                        if(matchBean.getAwayType()==1||matchBean.getHomeType()==1||matchBean.getVsType()==1){
                            list.add(matchBean);
                        }
                    }
                }
                if(list.size()!=0){
                    intent.putExtra("bean",(Serializable)list);
                    startActivity(intent);
                }else {
                    ToastUtils.showToast("请至少选择1场比赛");
                }
            }
        }
    }

    private void nextDate(){

        for (int i = 0; i < beans.size(); i++) {
            FootBallList.DataBean dataBean = beans.get(i);
            for(int j=0;j<dataBean.getMatch().size();j++){
                FootBallList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                if(matchBean.getAwayType()==1||matchBean.getHomeType()==1||matchBean.getVsType()==1){
                    mTrue=true;
                    return;

                }
            }
        }
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
                if(beans.size()==0){
                    ToastUtils.showToast("暂无比赛");
                }
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
                setListener();

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }

}
