package cn.com.futurelottery.ui.dialog;


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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.listener.SizeDialogListener;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.ui.adapter.football.SizeDialogAdapter;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         总比分
 */
public class SizeBetDialogFragment extends DialogFragment {


    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_away)
    TextView tvAway;
    @BindView(R.id.lion)
    LinearLayout lion;
    @BindView(R.id.two_recyclerView)
    RecyclerView twoRecyclerView;
    Unbinder unbinder;
    private SizeDialogAdapter mSizeDialogAdapter;
    private FootBallList.DataBean.MatchBean beans;
    private int mIndex;

    private SizeDialogListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (SizeDialogListener) context;
    }

    public SizeBetDialogFragment() {
        // Required empty public constructor
    }

    public static SizeBetDialogFragment newInstance(FootBallList.DataBean.MatchBean mPopupBean, int index) {
        SizeBetDialogFragment instance = new SizeBetDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_size_bet_dialog, container, false);
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

    private void setListener() {
        mSizeDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FootBallList.DataBean.MatchBean.OddsBean item = mSizeDialogAdapter.getItem(position);
                if (item != null) {
                    if (item.getType() == 0) {
                        item.setType(1);
                    } else {
                        item.setType(0);
                    }
                    mSizeDialogAdapter.notifyItemChanged(position);
                }

            }
        });

    }

    private void initView() {

        beans = (FootBallList.DataBean.MatchBean) getArguments().getSerializable("bean");

        mIndex = getArguments().getInt("index");
        if (beans != null) {
            tvHome.setText(beans.getHomeTeam());
            tvAway.setText(beans.getAwayTeam());
        }

        FlexboxLayoutManager threeManager = new FlexboxLayoutManager(getActivity());
        //设置主轴排列方式
        threeManager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        threeManager.setFlexWrap(FlexWrap.WRAP);
        threeManager.setAlignItems(AlignItems.STRETCH);
        threeManager.setJustifyContent(JustifyContent.FLEX_START);
        mSizeDialogAdapter = new SizeDialogAdapter(beans.getOdds());
        ((SimpleItemAnimator) twoRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        twoRecyclerView.setLayoutManager(threeManager);
        twoRecyclerView.setAdapter(mSizeDialogAdapter);

    }


    @OnClick({R.id.bt_cancel, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                dismiss();
                break;
            case R.id.bt_sure:
                callback.onDefeateComplete(mIndex, beans);
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
