package cn.com.futurelottery.ui.fragment.football;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.DialogListener;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.activity.Football.FootAllBetActivity;
import cn.com.futurelottery.ui.activity.Football.ScoreBetActivity;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * @author apple
 *         比分
 */
public class ScoreFragment extends BaseFragment implements DialogListener {

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

    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_score;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();
    }

    private void getDate() {
        list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.pass_rules_1);
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
                mScoreListAdapter = new ScoreListAdapter(res);
                AllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                AllRecycler.setAdapter(mScoreListAdapter);
                ((SimpleItemAnimator) AllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);

                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }

                mScoreListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ScoreList.DataBean.MatchBean matchBean = mMatchBeans.get(position);

                        ScoreDialogFragment adialogFragment = ScoreDialogFragment.newInstance(matchBean, position,Api.FOOTBALL.FT002);

                        adialogFragment.show(getChildFragmentManager(), "timePicker");

                    }
                });

            }

            @Override
            public void requestFailure(int code, String msg) {

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
        boolean flag=false;
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

                }
        }
        mScoreListAdapter.notifyDataSetChanged();
    }

}
