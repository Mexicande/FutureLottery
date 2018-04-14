package cn.com.futurelottery.ui.dialog;


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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.futurelottery.R;
import cn.com.futurelottery.base.Contacts;
import cn.com.futurelottery.model.Popup;
import cn.com.futurelottery.ui.activity.WebViewActivity;
import cn.com.futurelottery.utils.ActivityUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class AdialogFragment extends DialogFragment {
    private static String popup = null;
    @BindView(R.id.fl_content_container)
    ImageView flContentContainer;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private Popup popupBean;


    public static AdialogFragment newInstance(Popup mPopupBean) {
        AdialogFragment instance = new AdialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(popup, mPopupBean);
        instance.setArguments(args);
        return instance;

    }

    public AdialogFragment() {

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
        View view = inflater.inflate(R.layout.fragment_adialog, container, false);
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        ButterKnife.bind(this, view);
        initDialog();

        return view;
    }

    private void initDialog() {
        popupBean = (Popup) getArguments().getSerializable(popup);
        if (popupBean != null && popupBean.getImg() != null) {
            Glide.with(this)
                    .load(popupBean.getImg())
                    .into(flContentContainer);
        }
    }


    @OnClick({R.id.fl_content_container, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_content_container:
                if (popupBean.getLink_url() != null) {
                    Bundle temp = new Bundle();
                    temp.putString("title", popupBean.getTitle());
                    temp.putString("jump", popupBean.getLink_url());
                    ActivityUtils.startActivity(temp, WebViewActivity.class);
                    dismiss();
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;
        }
    }
}
