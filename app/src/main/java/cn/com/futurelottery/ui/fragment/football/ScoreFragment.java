package cn.com.futurelottery.ui.fragment.football;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Api;
import cn.com.futurelottery.base.ApiService;
import cn.com.futurelottery.base.BaseFragment;
import cn.com.futurelottery.inter.DialogListener;
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;
import cn.com.futurelottery.ui.dialog.AdialogFragment;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.LogUtils;
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
    private  ArrayList<ScoreList.DataBean.MatchBean>mMatchBeans;
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

                mMatchBeans=new ArrayList<>();
                for(int i=0;i<beans.size();i++){
                    mMatchBeans.addAll(beans.get(i).getMatch());
                }

                mScoreListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ScoreList.DataBean.MatchBean matchBean = mMatchBeans.get(position);

                        ScoreDialogFragment adialogFragment = ScoreDialogFragment.newInstance(matchBean,position);

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
    public void onDefeateComplete(int index,ScoreList.DataBean.MatchBean matchBean) {
        mScoreListAdapter.setData(index,matchBean);
        mScoreListAdapter.notifyItemChanged(index);
    }
}
