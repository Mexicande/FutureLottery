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
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.inter.SizeDialogListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;
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
 *         过关
 */
public class AllHalfFragment extends BaseFragment implements SizeDialogListener {


    @BindView(R.id.allHalfAllRecycler)
    RecyclerView allHalfAllRecycler;
    private HalfAdapter mHalfAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    private int nu=0;
    private boolean mTrue =false;
    private ArrayList<FootBallList.DataBean.MatchBean> mMatchBeans;

    public AllHalfFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_half_all;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate();
    }
    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.pass_rules_1);
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
                allHalfAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                allHalfAllRecycler.setAdapter(mHalfAdapter);
                ((SimpleItemAnimator) allHalfAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }

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
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type){
        if(type.getmMeeage()==9){
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
        if(type.getmType()==9){
            nextDate();
            Intent intent=new Intent(getActivity(),SizeBetActivity.class);
            intent.putExtra("type",9);
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
            if(list.size()>=2){
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
