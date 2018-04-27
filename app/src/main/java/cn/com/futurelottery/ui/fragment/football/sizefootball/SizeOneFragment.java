package cn.com.futurelottery.ui.fragment.football.sizefootball;


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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

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
import cn.com.futurelottery.inter.OnRequestDataListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.presenter.FootCleanType;
import cn.com.futurelottery.presenter.FootSureType;
import cn.com.futurelottery.presenter.FooterOneEvent;
import cn.com.futurelottery.ui.adapter.football.SizeAdapter;
import cn.com.futurelottery.utils.LogUtils;
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
        getDate();

    }
    private void getDate() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(Api.FOOTBALL.PASS_RULE,Api.FOOTBALL.pass_rules_o);
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
                mSizeAdapter = new SizeAdapter(res);
                conOneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                conOneRecycler.setAdapter(mSizeAdapter);
                ((SimpleItemAnimator)conOneRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
                setListener();

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
}
