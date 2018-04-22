package cn.com.futurelottery.ui.dialog;


import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.futurelottery.R;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         半全场
 */
public class HalfDialogFragment extends DialogFragment {


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
    Unbinder unbinder;

    public HalfDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_half2, container, false);
        unbinder = ButterKnife.bind(this, view);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
