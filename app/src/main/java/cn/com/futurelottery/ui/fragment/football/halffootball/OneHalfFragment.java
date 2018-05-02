package cn.com.futurelottery.ui.fragment.football.halffootball;


import android.content.Intent;
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
import cn.com.futurelottery.base.BaseApplication;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.inter.SizeDialogListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.presenter.CompetitionSelectType;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.ui.activity.Football.SizeBetActivity;
import cn.com.futurelottery.ui.adapter.football.HalfAdapter;
import cn.com.futurelottery.ui.dialog.HalfDialogFragment;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.LogUtils;
import cn.com.futurelottery.utils.ToastUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         单关
 */
public class OneHalfFragment extends BaseFragment implements SizeDialogListener{

    @BindView(R.id.oneHalfAllRecycler)
    RecyclerView oneHalfAllRecycler;
    private HalfAdapter mHalfAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    private int nu=0;
    private boolean mTrue =false;
    private ArrayList<FootBallList.DataBean.MatchBean> mMatchBeans;
    private View notDataView;

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
        initView();
        getDate();
        setListener();
    }

    private void setListener() {
        mHalfAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.layout_select:
                        FootBallList.DataBean.MatchBean matchBean = mMatchBeans.get(position - 1);
                        HalfDialogFragment adialogFragment = HalfDialogFragment.newInstance(matchBean, position);
                        adialogFragment.show(getChildFragmentManager(), "timePicker");
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void initView() {
        mHalfAdapter = new HalfAdapter(res);
        oneHalfAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        oneHalfAllRecycler.setAdapter(mHalfAdapter);
        ((SimpleItemAnimator) oneHalfAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) oneHalfAllRecycler.getParent(), false);

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
                mHalfAdapter.addData(res);
                mHalfAdapter.expandAll();


                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }
                if(beans.size()==0){
                    mHalfAdapter.setEmptyView(notDataView);
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
            jsonObject.put("play_rules",Api.FOOTBALL.FT004);
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
                    mHalfAdapter.getData().clear();
                    mHalfAdapter.addData(res);
                    mHalfAdapter.expandAll();
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
        if(type.getmSelect()==10){
            setSelect(type.getmLeague());
        }

    }
    /**
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type){
        if(type.getmMeeage()==10){
            for (int i = 0; i < beans.size(); i++) {
                FootBallList.DataBean dataBean = beans.get(i);
                for(int j=0;j<dataBean.getMatch().size();j++){
                    List<FootBallList.DataBean.MatchBean> match = dataBean.getMatch();
                    for(FootBallList.DataBean.MatchBean s:match){
                        List<FootBallList.DataBean.MatchBean.OddsBean> odds = s.getOdds();
                        for(FootBallList.DataBean.MatchBean.OddsBean m:odds){
                            if(m.getType()==1){
                                m.setType(0);
                            }
                        }
                        s.setSelect("");
                    }
                }
            }
            mHalfAdapter.notifyDataSetChanged();
        }
        mTrue=false;

    }

    /**
     * 提交
     */
    @Subscribe
    public void nextSubmit(FootSureType type){
        if(type.getmType()==10){
            nextDate();
            Intent intent=new Intent(getActivity(),SizeBetActivity.class);
            intent.putExtra("type",10);
            List<FootBallList.DataBean.MatchBean> list=new ArrayList<>();
            for(FootBallList.DataBean s:beans){
                for(int i=0;i<s.getMatch().size();i++){
                    int nu=0;
                    FootBallList.DataBean.MatchBean matchBean = s.getMatch().get(i);
                    List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
                    for(FootBallList.DataBean.MatchBean.OddsBean m:odds){
                        if(m.getType()==1){
                            nu++;
                        }
                    }
                    if(nu!=0){
                        list.add(matchBean);
                    }
                }
            }
            if(list.size()>=1){
                intent.putExtra("bean",(Serializable)list);
                startActivity(intent);
            }else {
                ToastUtils.showToast("请至少选择2场比赛");
            }
        }
    }

    private void nextDate(){

        for (int i = 0; i < beans.size(); i++) {
            FootBallList.DataBean dataBean = beans.get(i);
            for(int j=0;j<dataBean.getMatch().size();j++){
                FootBallList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                if(matchBean.getFistfrom()==1||matchBean.getSecondfrom()==1||matchBean.getThirdfrom()==1
                        ||matchBean.getFourthfrom()==1||matchBean.getFifthfrom()==1||matchBean.getSixthfrom()==1
                        ||matchBean.getSecondfrom()==1||matchBean.getEighthfrom()==1){
                    nu++;
                }
            }
        }
    }

    @Override
    public void onDefeateComplete(int index, FootBallList.DataBean.MatchBean matchBean) {
        mHalfAdapter.setData(index, matchBean);
        mHalfAdapter.notifyItemChanged(index);

    }
}
