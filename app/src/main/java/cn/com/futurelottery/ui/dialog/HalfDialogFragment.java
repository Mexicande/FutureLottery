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
public class HalfFragment extends Fragment {


    public HalfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_half2, container, false);
    }

}
