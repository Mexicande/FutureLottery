package cn.com.futurelottery.ui.fragment.football;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.football.HalfAdapter;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.dialog.ScoreDialogFragment;
import cn.com.futurelottery.utils.LogUtils;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         半全场
 */
public class HalfFragment extends BaseFragment {


    @BindView(R.id.halfRecycler)
    RecyclerView halfRecycler;
    @BindView(R.id.bottom_result_clear_tv)
    ImageView bottomResultClearTv;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    private HalfAdapter mHalfAdapter;
    private ArrayList<MultiItemEntity> res;
    private List<FootBallList.DataBean> beans;
    public HalfFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_half;
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
                halfRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                halfRecycler.setAdapter(mHalfAdapter);
                ((SimpleItemAnimator) halfRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                mHalfAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (FootBallList.DataBean s : beans) {
                            LogUtils.i(s.toString());
                        }
                    }
                });

                mHalfAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ScoreDialogFragment scoreDialogFragment = new ScoreDialogFragment();
                        scoreDialogFragment.show(getChildFragmentManager(), "scoreDialogFragment");
                    }
                });

            }

            @Override
            public void requestFailure(int code, String msg) {

            }
        });
    }
}
