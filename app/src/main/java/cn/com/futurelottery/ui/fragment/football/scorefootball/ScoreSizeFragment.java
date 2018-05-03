package cn.com.futurelottery.ui.fragment.football.scorefootball;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.listener.DialogListener;
import cn.com.futurelottery.listener.OnRequestDataListener;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.presenter.CompetitionSelectType;
import cn.com.futurelottery.ui.activity.Football.ScoreBetActivity;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * @author apple
 *         比分
 */
public class ScoreSizeFragment extends BaseFragment implements DialogListener {

    @BindView(R.id.AllRecycler)
    RecyclerView AllRecycler;
    @BindView(R.id.bottom_result_clear_tv)
    ImageView bottomResultClearTv;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    private ScoreListAdapter mScoreListAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<ScoreList.DataBean> beans;
    private ArrayList<ScoreList.DataBean.MatchBean> mMatchBeans;
    private List<ScoreList.DataBean.MatchBean> list;
    private View notDataView;
    public ScoreSizeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_score;
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
        AllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        AllRecycler.setAdapter(mScoreListAdapter);
        ((SimpleItemAnimator) AllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) AllRecycler.getParent(), false);
    }

    private void getDate() {
        list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.PASS_RULES_1);
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


    @OnClick({R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_result_clear_tv:
                clearDate();
                break;
            case R.id.bottom_result_next_btn:
                nextSubmit();
                break;
            default:
                break;
        }
    }

    /**
     * 提交
     */
    private void nextSubmit() {
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
            startActivity(intent);
        }else {
            ToastUtils.showToast("请至少选择1场比赛");
        }
    }


    /**
     * 筛选
     * @param league
     */
    private void setSelect(String league){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("pass_rules",1);
            jsonObject.put("play_rules",Api.FOOTBALL.FT002);
            jsonObject.put("league",league);

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
                if(res.size()!=0){
                    mScoreListAdapter.getData().clear();
                    mScoreListAdapter.addData(res);
                    mScoreListAdapter.expandAll();
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
        if(type.getmSelect()==5){
            setSelect(type.getmLeague());
        }

    }

    /**
     * clear
     */
    private void clearDate() {
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
