package cn.com.futurelottery.ui.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.model.Popup;
import cn.com.futurelottery.ui.adapter.ScoreDialogAdapter;
import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;


/**
 * A simple {@link Fragment} subclass.
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
    private ScoreDialogAdapter mScoreListAdapter;
    private int width;
    public ScoreDialogFragment() {
        // Required empty public constructor
    }
    public static AdialogFragment newInstance(Popup mPopupBean) {
        AdialogFragment instance = new AdialogFragment();
        Bundle args = new Bundle();
        //args.putSerializable(popup, mPopupBean);
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

    }

    private void initView() {
        ArrayList<Integer>strList=new ArrayList<>();
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(0);
        strList.add(1);
        oneRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                width = oneRecyclerView.getMeasuredWidth();
            }
        });
        mScoreListAdapter=new ScoreDialogAdapter(strList,width );
        FlexboxLayoutManager manager = new FlexboxLayoutManager(getActivity());
        //设置主轴排列方式
        manager.setFlexDirection(FlexDirection.ROW);
        //设置是否换行
        manager.setFlexWrap(FlexWrap.WRAP);
        manager.setAlignItems(AlignItems.STRETCH);
        manager.setJustifyContent(JustifyContent.CENTER);
        oneRecyclerView.setLayoutManager(manager);
        oneRecyclerView.setAdapter(mScoreListAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
