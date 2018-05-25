package cn.com.futurelottery.ui.fragment.football.scorefootball;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.listener.DialogListener;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.presenter.CompetitionSelectType;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.ui.activity.Football.ScoreBetActivity;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class ScoreOneFragment extends BaseFragment implements DialogListener {


    @BindView(R.id.oneRecycler)
    RecyclerView OneRecycler;
    Unbinder unbinder;
    private ScoreListAdapter mScoreListAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<ScoreList.DataBean> beans;
    private ArrayList<ScoreList.DataBean.MatchBean> mMatchBeans;
    private List<ScoreList.DataBean.MatchBean> list;
    private View notDataView;
    public ScoreOneFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_score_one;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getDate();
        setListener();

    }

    private void initView() {
        mScoreListAdapter = new ScoreListAdapter(null);
        OneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        OneRecycler.setAdapter(mScoreListAdapter);
        ((SimpleItemAnimator) OneRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) OneRecycler.getParent(), false);
    }

    private void getDate() {
        list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.PASS_RULES_O);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE, Api.FOOTBALL.FT002);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.FootBall_Api.FOOTBALL_LSIT, getActivity(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson = new Gson();
                ScoreList footBallList = gson.fromJson(data.toString(), ScoreList.class);
                beans = footBallList.getData();
                res = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    ScoreList.DataBean dataBean = beans.get(i);
                    dataBean.setSubItems(beans.get(i).getMatch());
                    res.add(dataBean);
                }
                mScoreListAdapter.addData(res);
                mScoreListAdapter.expandAll();
                if(beans.size()==0){
                    mScoreListAdapter.setEmptyView(notDataView);
                }
                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }
            }
            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }
    private void setListener() {
        mScoreListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean matchBean = mMatchBeans.get(position-1);
                ScoreDialogFragment adialogFragment = ScoreDialogFragment.newInstance(matchBean, position,Api.FOOTBALL.FT002);
                adialogFragment.show(getChildFragmentManager(), "timePicker");

            }
        });

    }

    @Override
    public void onDefeateComplete(int index, ScoreList.DataBean.MatchBean matchBean) {
        mScoreListAdapter.setData(index, matchBean);
        mScoreListAdapter.notifyItemChanged(index);

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
     *
     * @param league
     */
    private void setSelect(String league) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pass_rules", 0);
            jsonObject.put("play_rules", Api.FOOTBALL.FT002);
            jsonObject.put("league", league);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.GET_SERVICE(Api.FootBall_Api.PAY_SCREEN, BaseApplication.getInstance(), jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                Gson gson = new Gson();
                ScoreList footBallList = gson.fromJson(data.toString(), ScoreList.class);
                beans = footBallList.getData();
                res = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    ScoreList.DataBean dataBean = beans.get(i);
                    dataBean.setSubItems(beans.get(i).getMatch());
                    res.add(dataBean);
                }
                if (res.size() != 0) {
                    mScoreListAdapter.getData().clear();
                    mScoreListAdapter.addData(res);
                    mScoreListAdapter.expandAll();
                }
                if(beans.size()==0){
                    mScoreListAdapter.setEmptyView(notDataView);
                }
                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }
        });

    }

    @Subscribe
    public void setSelect(CompetitionSelectType type) {
        if (type.getmSelect() == 6) {
            setSelect(type.getmLeague());
        }
    }

    /**
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type) {
        if(type.getmMeeage()==6){
            for(int i=0;i<beans.size();i++){
                ScoreList.DataBean dataBean = beans.get(i);
                List<ScoreList.DataBean.MatchBean> match = dataBean.getMatch();
                for(ScoreList.DataBean.MatchBean s:match){
                    List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
                    for(int j=0;j<odds.size();j++){
                        List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(i);
                        for(int k=0;k<oddsBeans.size();k++){
                            if(oddsBeans.get(i).getType()==1){
                                oddsBeans.get(i).setType(0);
                            }
                        }
                    }
                    s.setSelect("");
                }
            }
            mScoreListAdapter.notifyDataSetChanged();
        }


    }

    /**
     * 提交
     */
    @Subscribe
    public void nextSubmit(FootSureType type) {
        if (type.getmType() == 6) {
            list.clear();
            int m=0;
            for(ScoreList.DataBean s:beans){
                List<ScoreList.DataBean.MatchBean> match = s.getMatch();
                for(int i=0;i<match.size();i++){
                    ScoreList.DataBean.MatchBean matchBean = match.get(i);
                    for(int j=0;j<match.size();j++){
                        List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = matchBean.getOdds();
                        for (int k=0;k<odds.size();k++){
                            List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(k);
                            for(int l=0;l<oddsBeans.size();l++){
                                if(oddsBeans.get(l).getType()==1){
                                    m++;
                                }
                            }
                        }

                    }
                    if(m>0){
                        list.add(matchBean);
                    }
                    m=0;
                }

            }
            Intent intent=new Intent(getActivity(),ScoreBetActivity.class);
            if(list.size()>=1){
                intent.putExtra("bean",(Serializable)list);
                intent.putExtra("type",6);
                startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
            }else {
                ToastUtils.showToast("请至少选择1场比赛");
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==-1){
            switch (requestCode){
                case Contacts.REQUEST_CODE_TO_PAY:
                    getActivity().finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
