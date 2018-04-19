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
import cn.com.futurelottery.inter.DialogListener;


/**
 * A simple {@link Fragment} subclass.
 * 退出dialog
 * @author apple
 */
public class QuitDialogFragment extends DialogFragment {


    Unbinder unbinder;
    private DialogListener callback;

    public QuitDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (DialogListener) context;
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
        View view = inflater.inflate(R.layout.fragment_quit_dialog, container, false);
        final Window window = getDialog().getWindow();
        if(window!=null){
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

    @OnClick({R.id.bt_save, R.id.bt_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_save:
                dismiss();
                break;
            case R.id.bt_clear:
                dismiss();
                break;
            default:
                break;
        }
    }
}
