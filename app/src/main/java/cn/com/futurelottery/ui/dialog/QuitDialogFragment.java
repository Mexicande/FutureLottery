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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.listener.SaveDialogListener;


/**
 * A simple {@link Fragment} subclass.
 * 退出dialog
 *
 * @author apple
 */
public class QuitDialogFragment extends DialogFragment {

    @BindView(R.id.bt_save)
    TextView btSave;
    @BindView(R.id.bt_clear)
    TextView btClear;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.message)
    TextView message;
    private SaveDialogListener listener;
    Unbinder unbinder;

    public QuitDialogFragment() {
        // Required empty public constructor
    }

    public static QuitDialogFragment newInstance(int type) {
        QuitDialogFragment instance = new QuitDialogFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SaveDialogListener) context;
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
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        unbinder = ButterKnife.bind(this, view);
        int type = getArguments().getInt("type");


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
                listener.saveDate();
                dismiss();
                break;
            case R.id.bt_clear:
                listener.clearDate();
                dismiss();
                break;
            default:
                break;
        }
    }
}
