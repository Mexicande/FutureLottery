package cn.com.futurelottery.ui.dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.futurelottery.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HalfBetDialogFragment extends Fragment {


    public HalfBetDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_half_bet_dialog, container, false);
    }

}
