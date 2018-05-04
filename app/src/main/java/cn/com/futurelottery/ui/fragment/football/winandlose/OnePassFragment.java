package cn.com.futurelottery.ui.fragment.football.winandlose;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;

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
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.presenter.CompetitionSelectType;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.presenter.FooterOneEvent;
import cn.com.futurelottery.ui.activity.Football.FootAllBetActivity;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 * @author apple
 * 单关
 */
public class OnePassFragment extends BaseFragment {


    @BindView(R.id.OneRecycler)
    RecyclerView mOneRecycler;
    private WinAndLoseAdapter mWinAndLoseAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans=new ArrayList<>();
    private int nu=0;
    private boolean mTrue =false;
    private View notDataView;

    public OnePassFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_one_pass;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getDate();
        setListener();


    }

    private void initView() {
        mWinAndLoseAdapter = new WinAndLoseAdapter(null);
        mOneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOneRecycler.setAdapter(mWinAndLoseAdapter);
        ((SimpleItemAnimator) mOneRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) mOneRecycler.getParent(), false);

    }

    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.PASS_RULES_O);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE, Api.FOOTBALL.FT001);
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
                mWinAndLoseAdapter.addData(res);
                mWinAndLoseAdapter.expandAll();
                if(beans.size()==0){
                    mWinAndLoseAdapter.setEmptyView(notDataView);
                }

            }
            @Override
            public void requestFailure(int code, String msg) {

            }
        });
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
     * 筛选
     * @param league
     */
    private void setSelect(String league){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pass_rules",0);
            jsonObject.put("play_rules",Api.FOOTBALL.FT001);
            jsonObject.put("league",league);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.FootBall_Api.PAY_SCREEN, BaseApplication.getInstance(), jsonObject, new OnRequestDataListener() {
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
                if(res.size()!=0){
                    mWinAndLoseAdapter.getData().clear();
                    mWinAndLoseAdapter.addData(res);
                    mWinAndLoseAdapter.expandAll();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });

    }
    @Subscribe
    public void setSelect(CompetitionSelectType type){
        if(type.getmSelect()==2){
            setSelect(type.getmLeague());
        }

    }
    /**
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type){
        if(type.getmMeeage()==2){
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
    }

    /**
     * 提交
     */
    @Subscribe
    public void nextSubmit(FootSureType type){
        if(type.getmType()==2){
                nextDate();
                if(mTrue){
                    Intent intent=new Intent(getActivity(),FootAllBetActivity.class);
                    intent.putExtra("type",2);
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
        EventBus.getDefault().post(new FooterOneEvent(nu,2));
    }



}
