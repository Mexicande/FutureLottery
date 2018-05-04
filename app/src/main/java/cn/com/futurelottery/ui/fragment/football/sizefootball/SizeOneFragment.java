package cn.com.futurelottery.ui.fragment.football.sizefootball;


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
import cn.com.futurelottery.ui.activity.Football.SizeBetActivity;
import cn.com.futurelottery.ui.adapter.football.SizeAdapter;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;


/**
 * A simple {@link Fragment} subclass.
 * @author apple
 * 总进球 单关
 */
public class SizeOneFragment extends BaseFragment {


    @BindView(R.id.sizeOneRecycler)
    RecyclerView conOneRecycler;
    private SizeAdapter mSizeAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    private int nu=0;
    private boolean mTrue =false;
    private View notDataView;

    public SizeOneFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_size_one;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getDate();
        setListener();
    }

    private void initView() {
        mSizeAdapter = new SizeAdapter(null);
        conOneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        conOneRecycler.setAdapter(mSizeAdapter);
        ((SimpleItemAnimator)conOneRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) conOneRecycler.getParent(), false);

    }

    private void getDate() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE,Api.FOOTBALL.PASS_RULES_O);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE,Api.FOOTBALL.FT003);
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
                mSizeAdapter.addData(res);
                mSizeAdapter.expandAll();
                if(beans.size()==0){
                    mSizeAdapter.setEmptyView(notDataView);
                }

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });


    }
    private void setListener() {

        mSizeAdapter.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
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
                if(matchBean.getFistfrom()==1||matchBean.getSecondfrom()==1||matchBean.getThirdfrom()==1
                        ||matchBean.getFourthfrom()==1||matchBean.getFifthfrom()==1||matchBean.getSixthfrom()==1
                        ||matchBean.getSecondfrom()==1||matchBean.getEighthfrom()==1){
                    nu++;
                }

            }
        }
        // EventBus.getDefault().post(new FootSizeType(nu,0));
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
            jsonObject.put("play_rules",Api.FOOTBALL.FT003);
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
                    mSizeAdapter.getData().clear();
                    mSizeAdapter.addData(res);
                    mSizeAdapter.expandAll();
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
        if(type.getmSelect()==8){
            setSelect(type.getmLeague());
        }

    }
    /**
     * 清除
     */
    @Subscribe
    public void cleanSelected(FootCleanType type){
        if(type.getmMeeage()==8){
            for (int i = 0; i < beans.size(); i++) {
                FootBallList.DataBean dataBean = beans.get(i);
                for(int j=0;j<dataBean.getMatch().size();j++){
                    FootBallList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                    if(matchBean.getFistfrom()==1||matchBean.getSecondfrom()==1||matchBean.getThirdfrom()==1
                            ||matchBean.getFourthfrom()==1||matchBean.getFifthfrom()==1||matchBean.getSixthfrom()==1
                            ||matchBean.getSecondfrom()==1||matchBean.getEighthfrom()==1){
                        matchBean.setFistfrom(0);
                        matchBean.setSecondfrom(0);
                        matchBean.setThirdfrom(0);
                        matchBean.setFourthfrom(0);
                        matchBean.setFifthfrom(0);
                        matchBean.setSixthfrom(0);
                        matchBean.setSeventhfrom(0);
                        matchBean.setEighthfrom(0);

                    }

                }
            }
            mSizeAdapter.notifyDataSetChanged();
        }
        mTrue=false;

    }

    /**
     * 提交
     */
    @Subscribe
    public void nextSubmit(FootSureType type){
        if(type.getmType()==8){
            nextDate();
            Intent intent=new Intent(getActivity(),SizeBetActivity.class);
            intent.putExtra("type",8);
            List<FootBallList.DataBean.MatchBean> list=new ArrayList<>();
            for(FootBallList.DataBean s:beans){
                for(int i=0;i<s.getMatch().size();i++){
                    FootBallList.DataBean.MatchBean matchBean = s.getMatch().get(i);
                    if(matchBean.getFistfrom()==1||matchBean.getSecondfrom()==1||matchBean.getThirdfrom()==1
                            ||matchBean.getFourthfrom()==1||matchBean.getFifthfrom()==1||matchBean.getSixthfrom()==1
                            ||matchBean.getSecondfrom()==1||matchBean.getEighthfrom()==1){
                        list.add(matchBean);
                    }
                }
            }
            if(nu>=2){
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

}
