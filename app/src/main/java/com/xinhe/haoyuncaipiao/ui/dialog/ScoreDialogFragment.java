package com.xinhe.haoyuncaipiao.ui.dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.listener.DialogListener;
import com.xinhe.haoyuncaipiao.model.ScoreList;
import com.xinhe.haoyuncaipiao.ui.adapter.ScoreDialogAdapter;


/**
 * A simple {@link Fragment} subclass.
 * 比分弹窗
 */
public class ScoreDialogFragment extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_away)
    TextView tvAway;
    @BindView(R.id.one_recyclerView)
    RecyclerView oneRecyclerView;
    @BindView(R.id.two_recyclerView)
    RecyclerView twoRecyclerView;
    @BindView(R.id.three_recyclerView)
    RecyclerView threeRecyclerView;
    private ScoreDialogAdapter mScoreListAdapter, mFlatAdapter, mLosrAdapter;
    private int width;
    private ScoreList.DataBean.MatchBean bean;

    private DialogListener callback;
    private int mIndex;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DialogListener) getParentFragment();
    }

    public ScoreDialogFragment() {

    }

    public static ScoreDialogFragment newInstance(ScoreList.DataBean.MatchBean mPopupBean,int index,String foot) {
        ScoreDialogFragment instance = new ScoreDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("bean", mPopupBean);
        args.putInt("index",index);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_AlertDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score_dialog, container, false);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }


    private void initView() {


        bean = (ScoreList.DataBean.MatchBean) getArguments().getSerializable("bean");
        mIndex = getArguments().getInt("index");
        if (bean != null) {
            tvHome.setText(bean.getHomeTeam());
            tvAway.setText(bean.getAwayTeam());
            mScoreListAdapter = new ScoreDialogAdapter(bean.getOdds().get(0));
            mFlatAdapter = new ScoreDialogAdapter(bean.getOdds().get(1));
            mLosrAdapter = new ScoreDialogAdapter(bean.getOdds().get(2));
        }

        FlexboxLayoutManager oneManager = new FlexboxLayoutManager(getActivity());
        //设置主轴排列方式
        oneManager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        oneManager.setFlexWrap(FlexWrap.WRAP);
        oneManager.setAlignItems(AlignItems.STRETCH);
        oneManager.setJustifyContent(JustifyContent.FLEX_START);


        FlexboxLayoutManager twoManager = new FlexboxLayoutManager(getActivity());
        //设置主轴排列方式
        twoManager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        twoManager.setFlexWrap(FlexWrap.WRAP);
        twoManager.setAlignItems(AlignItems.STRETCH);
        twoManager.setJustifyContent(JustifyContent.FLEX_START);


        FlexboxLayoutManager threeManager = new FlexboxLayoutManager(getActivity());
        //设置主轴排列方式
        threeManager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        threeManager.setFlexWrap(FlexWrap.WRAP);
        threeManager.setAlignItems(AlignItems.STRETCH);
        threeManager.setJustifyContent(JustifyContent.FLEX_START);

       /* View view = getLayoutInflater().inflate(R.layout.last_score_item, null);
        mScoreListAdapter.addFooterView(view);
        manager.addView(view);*/
        ((SimpleItemAnimator) oneRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        oneRecyclerView.setLayoutManager(oneManager);
        oneRecyclerView.setAdapter(mScoreListAdapter);


        ((SimpleItemAnimator) twoRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        twoRecyclerView.setLayoutManager(twoManager);
        twoRecyclerView.setAdapter(mFlatAdapter);


        ((SimpleItemAnimator) threeRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        threeRecyclerView.setLayoutManager(threeManager);
        threeRecyclerView.setAdapter(mLosrAdapter);

    }


    private void setListener() {
        mScoreListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean.OddsBean item = mScoreListAdapter.getItem(position);
                if (item != null) {
                    if(item.getType()==0){
                        item.setType(1);
                    }else {
                        item.setType(0);
                    }
                    mScoreListAdapter.notifyItemChanged(position);
                }
            }
        });
        mFlatAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean.OddsBean item = mFlatAdapter.getItem(position);
                if (item != null) {
                    if(item.getType()==0){
                        item.setType(1);
                    }else {
                        item.setType(0);
                    }
                    mFlatAdapter.notifyItemChanged(position);
                }

            }
        });
        mLosrAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean.OddsBean item = mLosrAdapter.getItem(position);
                if (item != null) {
                    if(item.getType()==0){
                        item.setType(1);
                    }else {
                        item.setType(0);
                    }
                    mLosrAdapter.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_cancel, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                dismiss();
                break;
            case R.id.bt_sure:
                List<ScoreList.DataBean.MatchBean.OddsBean> win = mScoreListAdapter.getData();
                List<ScoreList.DataBean.MatchBean.OddsBean> flat = mFlatAdapter.getData();
                List<ScoreList.DataBean.MatchBean.OddsBean> losr = mLosrAdapter.getData();
                List<List<ScoreList.DataBean.MatchBean.OddsBean>> odd=new ArrayList<>();
                odd.add(win);
                odd.add(flat);
                odd.add(losr);
                bean.setOdds(odd);
                StringBuilder sb=new StringBuilder();
                for(ScoreList.DataBean.MatchBean.OddsBean s:win){
                    if(s.getType()==1){
                        sb.append(s.getName()).append(" ");
                    }
                }

                for(ScoreList.DataBean.MatchBean.OddsBean s:flat){
                    if(s.getType()==1){
                        sb.append(" ").append(s.getName());
                    }
                }

                for(ScoreList.DataBean.MatchBean.OddsBean s:losr){
                    if(s.getType()==1){
                        sb.append(" ").append(s.getName());
                    }
                }

                bean.setSelect(sb.toString());
                callback.onDefeateComplete(mIndex,bean);
                dismiss();
                break;
            default:
                break;
        }
    }
}
