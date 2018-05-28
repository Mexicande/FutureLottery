package com.xinhe.haoyuncaipiao.ui.fragment.football;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.xinhe.haoyuncaipiao.base.Api;
import com.xinhe.haoyuncaipiao.base.ApiService;
import com.xinhe.haoyuncaipiao.base.BaseFragment;
import com.xinhe.haoyuncaipiao.base.Contacts;
import com.xinhe.haoyuncaipiao.listener.DialogListener;
import com.xinhe.haoyuncaipiao.listener.OnRequestDataListener;
import com.xinhe.haoyuncaipiao.model.ScoreList;
import com.xinhe.haoyuncaipiao.ui.activity.Football.MixtureBetActivity;
import com.xinhe.haoyuncaipiao.ui.adapter.football.MixtureAdapter;
import com.xinhe.haoyuncaipiao.ui.dialog.MixtureDialog;
import com.xinhe.haoyuncaipiao.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.xinhe.haoyuncaipiao.R;

import com.xinhe.haoyuncaipiao.presenter.CompetitionSelectType;
import com.xinhe.haoyuncaipiao.view.progressdialog.KProgressHUD;
import com.xinhe.haoyuncaipiao.view.topRightMenu.OnTopRightMenuItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * 混合投注
 */
public class MixtureFragment extends BaseFragment implements DialogListener {


    @BindView(R.id.conAllRecycler)
    RecyclerView conAllRecycler;
    @BindView(R.id.bottom_result_clear_tv)
    ImageView bottomResultClearTv;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    private MixtureAdapter mixtureAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<ScoreList.DataBean> beans;
    private ArrayList<ScoreList.DataBean.MatchBean> mMatchBeans;
    private List<ScoreList.DataBean.MatchBean> list;
    private View notDataView;
    private int nu=0;
    private boolean mTrue =false;
    private KProgressHUD hud;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_mixture;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getDate();
        setListener();
    }

    private void initView() {
        tvSelect.setText("请至少选择2场比赛");

        mixtureAdapter = new MixtureAdapter(null);
        conAllRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        conAllRecycler.setAdapter(mixtureAdapter);
        ((SimpleItemAnimator) conAllRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_layout, (ViewGroup) conAllRecycler.getParent(), false);
    }

    private void getDate() {
        list=new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE, Api.FOOTBALL.PASS_RULES_1);
            jsonObject.put(Api.FOOTBALL.PLAY_RULE, Api.FOOTBALL.FT005);
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
                ScoreList footBallList = gson.fromJson(data.toString(), ScoreList.class);
                beans = footBallList.getData();
                res = new ArrayList<>();
                for (int i = 0; i < beans.size(); i++) {
                    ScoreList.DataBean dataBean = beans.get(i);
                    dataBean.setSubItems(beans.get(i).getMatch());
                    res.add(dataBean);
                }
                mixtureAdapter.addData(res);
                mixtureAdapter.expandAll();
                if(beans.size()==0){
                    mixtureAdapter.setEmptyView(notDataView);
                }
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
        mixtureAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean matchBean = (ScoreList.DataBean.MatchBean) adapter.getItem(position);
                MixtureDialog adialogFragment = MixtureDialog.newInstance(matchBean, position,Api.FOOTBALL.FT005);
                adialogFragment.show(getChildFragmentManager(), "timePicker");

            }
        });

        mixtureAdapter.setOnTopRightMenuItemClickListener(new OnTopRightMenuItemClickListener() {
            @Override
            public void onTopRightMenuItemClick(int position) {
                update();
            }
        });

    }

    private void update() {
        nu=0;
        for (int i = 0; i < beans.size(); i++) {
            ScoreList.DataBean dataBean = beans.get(i);
            for(int j=0;j<dataBean.getMatch().size();j++){
                boolean isChoose = false;
                ScoreList.DataBean.MatchBean matchBean = dataBean.getMatch().get(j);
                List<List<ScoreList.DataBean.MatchBean.OddsBean>> adds = matchBean.getOdds();
                if (null!=adds&&adds.size()>0){
                    for (int k=0;k<adds.size();k++){
                        List<ScoreList.DataBean.MatchBean.OddsBean> ads = adds.get(k);
                        if (null!=ads&&ads.size()>0){
                            for (int l=0;l<ads.size();l++){
                                ScoreList.DataBean.MatchBean.OddsBean ad = ads.get(l);
                                if (1==ad.getType()){
                                    isChoose=true;
                                }
                            }
                        }
                    }
                }
                if (isChoose==true){
                    nu++;
                }
            }
        }
        if (nu!= 0) {
            tvSelect.setText("已选择" + nu + "场");
        } else {
            tvSelect.setText("请至少选择2场比赛");
        }
    }


    @OnClick({R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_result_clear_tv:
                cleanSelected();
                break;
            case R.id.bottom_result_next_btn:
                nextSubmit();
                break;
        }
    }

    @Override
    public void onDefeateComplete(int index, ScoreList.DataBean.MatchBean matchBean) {
        mixtureAdapter.setData(index, matchBean);
        mixtureAdapter.notifyItemChanged(index);
        update();
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
            jsonObject.put("pass_rules", 1);
            jsonObject.put("play_rules", Api.FOOTBALL.FT005);
            jsonObject.put("league", league);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        hud = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_str_jiazai))
                .setDimAmount(0.5f)
                .show();
        ApiService.GET_SERVICE(Api.FootBall_Api.PAY_SCREEN, getContext(), jsonObject, new OnRequestDataListener() {
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
                    mixtureAdapter.getData().clear();
                    mixtureAdapter.addData(res);
                    mixtureAdapter.expandAll();
                }
                if(beans.size()==0){
                    mixtureAdapter.setEmptyView(notDataView);
                }
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

    @Subscribe
    public void setSelect(CompetitionSelectType type) {
        if (type.getmSelect() == 11) {
            setSelect(type.getmLeague());
        }
    }

    /**
     * 清除
     */
    public void cleanSelected() {
        for(int i=0;i<beans.size();i++){
            ScoreList.DataBean dataBean = beans.get(i);
            List<ScoreList.DataBean.MatchBean> match = dataBean.getMatch();
            for(ScoreList.DataBean.MatchBean s:match){
                List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = s.getOdds();
                for(int j=0;j<odds.size();j++){
                    List<ScoreList.DataBean.MatchBean.OddsBean> oddsBeans = odds.get(j);
                    for(int k=0;k<oddsBeans.size();k++){
                            oddsBeans.get(k).setType(0);
                    }
                }
                s.setSelect("");
                s.setHomeType1(0);
                s.setHomeType2(0);
                s.setAwayType1(0);
                s.setAwayType2(0);
                s.setVsType1(0);
                s.setVsType2(0);
            }
        }
        mixtureAdapter.notifyDataSetChanged();
        update();
    }



    /**
     * 提交
     */
    public void nextSubmit() {
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
            Intent intent=new Intent(getActivity(),MixtureBetActivity.class);
            if(list.size()>=2){
                intent.putExtra("bean",(Serializable)list);
                intent.putExtra("type",11);
                startActivityForResult(intent, Contacts.REQUEST_CODE_TO_PAY);
            }else {
                ToastUtils.showToast("请至少选择2场比赛");
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
