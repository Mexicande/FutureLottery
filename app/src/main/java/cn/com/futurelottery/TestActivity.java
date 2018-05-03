package cn.com.futurelottery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.flexbox_layout)
    FlexboxLayout flexboxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ssq_layout_item);
        ButterKnife.bind(this);
    }
}
