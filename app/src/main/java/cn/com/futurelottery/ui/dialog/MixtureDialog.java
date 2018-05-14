package cn.com.futurelottery.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import cn.com.futurelottery.R;
import cn.com.futurelottery.listener.DialogListener;
import cn.com.futurelottery.model.ScoreList;
import cn.com.futurelottery.ui.adapter.ScoreDialogAdapter;

/**
 * Created by tantan on 2018/5/10.
 * 混合弹窗
 */

public class MixtureDialog extends DialogFragment {
    Unbinder unbinder;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_away)
    TextView tvAway;
    @BindView(R.id.one_recyclerView)
    RecyclerView oneRecyclerView;
    @BindView(R.id.all_balls_recyclerView)
    RecyclerView allBallsRecyclerView;
    @BindView(R.id.half_recyclerView)
    RecyclerView halfRecyclerView;
    @BindView(R.id.tv_con)
    TextView tvCon;
    @BindView(R.id.tv_con1)
    TextView tvCon1;
    @BindView(R.id.tv_home_odds1)
    TextView tvHomeOdds1;
    @BindView(R.id.tv_equal_odds1)
    TextView tvEqualOdds1;
    @BindView(R.id.tv_customer_odds1)
    TextView tvCustomerOdds1;
    @BindView(R.id.tv_home_odds2)
    TextView tvHomeOdds2;
    @BindView(R.id.tv_equal_odds2)
    TextView tvEqualOdds2;
    @BindView(R.id.tv_customer_odds2)
    TextView tvCustomerOdds2;
    private ScoreDialogAdapter mScoreListAdapter, mFlatAdapter, mLosrAdapter;
    private int width;
    private ScoreList.DataBean.MatchBean bean;

    private DialogListener callback;
    private int mIndex;
    private List<ScoreList.DataBean.MatchBean.OddsBean> odds1;
    private List<ScoreList.DataBean.MatchBean.OddsBean> odds2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DialogListener) getParentFragment();
    }

    public MixtureDialog() {

    }

    public static MixtureDialog newInstance(ScoreList.DataBean.MatchBean mPopupBean, int index, String foot) {
        MixtureDialog instance = new MixtureDialog();
        Bundle args = new Bundle();
        args.putSerializable("bean", mPopupBean);
        args.putInt("index", index);
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
        View view = inflater.inflate(R.layout.mixture_dialog, container, false);
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

        if (bean != null) {
            tvHome.setText(bean.getHomeTeam());
            tvAway.setText(bean.getAwayTeam());


            odds1 = bean.getOdds().get(0);
            odds2 = bean.getOdds().get(1);
            if (null!=odds1){
                tvHomeOdds1.setText("主胜"+odds1.get(0).getOdds());
                tvEqualOdds1.setText("平"+odds1.get(1).getOdds());
                tvCustomerOdds1.setText("客胜"+odds1.get(2).getOdds());
            }
            if (null!=odds2){
                tvHomeOdds2.setText("主胜"+odds2.get(0).getOdds());
                tvEqualOdds2.setText("平"+odds2.get(1).getOdds());
                tvCustomerOdds2.setText("客胜"+odds2.get(2).getOdds());
            }
            tvHomeOdds1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getHomeType1()==0){
                        bean.setHomeType1(1);
                        odds1.get(0).setType(1);
                    }else {
                        bean.setHomeType1(0);
                        odds1.get(0).setType(0);
                    }
                    refreshTopTwo();
                }
            });
            tvEqualOdds1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getVsType1()==0){
                        bean.setVsType1(1);
                        odds1.get(1).setType(1);
                    }else {
                        bean.setVsType1(0);
                        odds1.get(1).setType(0);
                    }
                    refreshTopTwo();
                }
            });
            tvCustomerOdds1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getAwayType1()==0){
                        bean.setAwayType1(1);
                        odds1.get(2).setType(1);
                    }else {
                        bean.setAwayType1(0);
                        odds1.get(2).setType(0);
                    }
                    refreshTopTwo();
                }
            });

            tvHomeOdds2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getHomeType2()==0){
                        bean.setHomeType2(1);
                        odds2.get(0).setType(1);
                    }else {
                        bean.setHomeType2(0);
                        odds2.get(0).setType(0);
                    }
                    refreshTopTwo();
                }
            });
            tvEqualOdds2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getVsType2()==0){
                        bean.setVsType2(1);
                        odds2.get(1).setType(1);
                    }else {
                        bean.setVsType2(0);
                        odds2.get(1).setType(0);
                    }
                    refreshTopTwo();
                }
            });
            tvCustomerOdds2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getAwayType2()==0){
                        bean.setAwayType2(1);
                        odds2.get(2).setType(1);
                    }else {
                        bean.setAwayType2(0);
                        odds2.get(2).setType(0);
                    }
                    refreshTopTwo();
                }
            });

            refreshTopTwo();
            //让球
            if(bean.getOdds().size()>3){
                ScoreList.DataBean.MatchBean.OddsBean oddsBean = bean.getOdds().get(1).get(3);
                int odd = Integer.parseInt(oddsBean.getOdds());
                if(odd>0){
                    tvCon1.setText("+"+odd);
                    tvCon1.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
                }else {
                    tvCon1.setText("-"+odd);
                    tvCon1.setBackgroundColor(getContext().getResources().getColor(R.color.green_1A));
                }
            }


            if (null != bean.getOdds().get(2)) {
                mScoreListAdapter = new ScoreDialogAdapter(bean.getOdds().get(2));
                ((SimpleItemAnimator) oneRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                oneRecyclerView.setLayoutManager(oneManager);
                oneRecyclerView.setAdapter(mScoreListAdapter);
            }
            if (null != bean.getOdds().get(3)) {
                mFlatAdapter = new ScoreDialogAdapter(bean.getOdds().get(3));
                ((SimpleItemAnimator) allBallsRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                allBallsRecyclerView.setLayoutManager(twoManager);
                allBallsRecyclerView.setAdapter(mFlatAdapter);
            }
            if (null != bean.getOdds().get(4)) {
                mLosrAdapter = new ScoreDialogAdapter(bean.getOdds().get(4));
                ((SimpleItemAnimator) halfRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
                halfRecyclerView.setLayoutManager(threeManager);
                halfRecyclerView.setAdapter(mLosrAdapter);
            }

        }


    }

    private void refreshTopTwo() {
        if (bean.getHomeType1()==0){
            tvHomeOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvHomeOdds1.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvHomeOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvHomeOdds1.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        if (bean.getVsType1()==0){
            tvEqualOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvEqualOdds1.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvEqualOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvEqualOdds1.setTextColor(getContext().getResources().getColor(R.color.white));
        }

        if (bean.getAwayType1()==0){
            tvCustomerOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvCustomerOdds1.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvCustomerOdds1.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvCustomerOdds1.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        if (bean.getHomeType2()==0){
            tvHomeOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvHomeOdds2.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvHomeOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvHomeOdds2.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        if (bean.getVsType2()==0){
            tvEqualOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvEqualOdds2.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvEqualOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvEqualOdds2.setTextColor(getContext().getResources().getColor(R.color.white));
        }

        if (bean.getAwayType2()==0){
            tvCustomerOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            tvCustomerOdds2.setTextColor(getContext().getResources().getColor(R.color.black));
        }else {
            tvCustomerOdds2.setBackgroundColor(getContext().getResources().getColor(R.color.red_ball));
            tvCustomerOdds2.setTextColor(getContext().getResources().getColor(R.color.white));
        }
    }


    private void setListener() {
        mScoreListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreList.DataBean.MatchBean.OddsBean item = mScoreListAdapter.getItem(position);
                if (item != null) {
                    if (item.getType() == 0) {
                        item.setType(1);
                    } else {
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
                    if (item.getType() == 0) {
                        item.setType(1);
                    } else {
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
                    if (item.getType() == 0) {
                        item.setType(1);
                    } else {
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
                odd.add(odds1);
                odd.add(odds2);
                odd.add(win);
                odd.add(flat);
                odd.add(losr);
                bean.setOdds(odd);
                StringBuilder sb = new StringBuilder();
                List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = bean.getOdds();
                if (null!=odds){
                    for (int i=0;i<odds.size();i++){
                        List<ScoreList.DataBean.MatchBean.OddsBean> ods = odds.get(i);
                        if (null!=ods){
                            for (ScoreList.DataBean.MatchBean.OddsBean s : ods) {
                                if (s.getType() == 1) {
                                    sb.append(s.getName()).append(" ");
                                }
                            }
                        }
                    }
                }

                bean.setSelect(sb.toString());
                callback.onDefeateComplete(mIndex, bean);
                dismiss();
                break;
            default:
                break;
        }
    }
}
