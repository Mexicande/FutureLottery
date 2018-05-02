package cn.com.futurelottery.ui.dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.inter.ClearDialogListener;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 *         清除数据
 */
public class ClearDialogFragment extends DialogFragment {


    Unbinder unbinder;
    private ClearDialogListener listener;

    public static ClearDialogFragment newInstance(int type) {
        ClearDialogFragment instance = new ClearDialogFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ClearDialogListener) context;
    }

    public ClearDialogFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_half_bet_dialog, container, false);
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
                dismiss();
                int type = getArguments().getInt("type");
                if (type == 1) {
                    listener.sure(1);
                } else {
                    listener.sure(2);
                }
                break;
            default:
                break;
        }
    }
}
