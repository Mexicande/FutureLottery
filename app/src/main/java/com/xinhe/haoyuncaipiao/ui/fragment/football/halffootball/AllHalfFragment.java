package com.xinhe.haoyuncaipiao.ui.fragment.football.halffootball;


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
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseApplication;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.listener.SizeDialogListener;
import com.xinhe.haoyuncaipiao.model.FootBallList;
import com.xinhe.haoyuncaipiao.presenter.CompetitionSelectType;
import com.xinhe.haoyuncaipiao.presenter.FootCleanType;
import com.xinhe.haoyuncaipiao.presenter.FootSureType;
import com.xinhe.haoyuncaipiao.ui.activity.Football.SizeBetActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.football.HalfAdapter;
import com.xinhe.haoyuncaipiao.ui.dialog.HalfDialogFragment;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;


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
    private View notDataView;
    private KProgressHUD hud;

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
        initView();
        getDate();
        setListener();
    }


    private void initView() {
        mHalfAdapter = new HalfAdapter(null);
        allHalfAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        allHalfAllRecycler.setAdapter(mHalfAdapter);
        ((SimpleItemAnimator) allHalfAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) allHalfAllRecycler.getParent(), false);

    }

    private void getDate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.PASS_RULES_1);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE, Api.FOOTBALL.FT004);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
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
                if(beans.size()==0){
                    mHalfAdapter.setEmptyView(notDataView);
                }
                mHalfAdapter.addData(res);
                mHalfAdapter.expandAll();
                mMatchBeans = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });
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
            jsonObject.put("pass_rules",1);
            jsonObject.put("play_rules",Api.FOOTBALL.FT004);
            jsonObject.put("league",league);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();

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
                hud.dismiss();
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
                hud.dismiss();
            }
        });

    }
    @Subscribe
    public void setSelect(CompetitionSelectType type){
        if(type.getmSelect()==9){
            setSelect(type.getmLeague());
        }

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
                startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
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
