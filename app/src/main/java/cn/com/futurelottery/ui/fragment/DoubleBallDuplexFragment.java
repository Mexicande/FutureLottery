package cn.com.futurelottery.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.futurelottery.R;
import cn.com.futurelottery.ui.adapter.DoubleBallBlueAdapter;
import cn.com.futurelottery.ui.adapter.DoubleBallRedAdapter;
import cn.com.futurelottery.utils.Calculator;
import cn.com.futurelottery.utils.ToastUtils;
import cn.com.futurelottery.utils.ViewSetHinghUtil;


/**
 * 双色球胆拖
 */
public class DoubleBallDuplexFragment extends Fragment {

    @BindView(R.id.danhao_red_gv)
    GridView danhaoRedGv;
    @BindView(R.id.tuohao_red_gv)
    GridView tuohaoRedGv;
    @BindView(R.id.bule_gv)
    GridView buleGv;
    @BindView(R.id.bottom_result_clear_tv)
    TextView bottomResultClearTv;
    @BindView(R.id.bottom_result_count_tv)
    TextView bottomResultCountTv;
    @BindView(R.id.bottom_result_money_tv)
    TextView bottomResultMoneyTv;
    @BindView(R.id.bottom_result_next_btn)
    Button bottomResultNextBtn;
    Unbinder unbinder;
    @BindView(R.id.bottom_result_choose_tv)
    TextView bottomResultChooseTv;
    private View view;
    private ArrayList<String> chooseDanBalls = new ArrayList<>();
    private ArrayList<String> chooseTuoBalls = new ArrayList<>();
    private ArrayList<String> chooseBlueBalls = new ArrayList<>();
    //是否遗漏
    private int isShow;
    private DoubleBallRedAdapter danBallAdapter;
    private DoubleBallRedAdapter tuoBallAdapter;
    private DoubleBallBlueAdapter blueBallAdapter;
    private int selectDanNumber,selectTuoNumber,selectBlueNumber;
    //总共的注数
    private long zhushu;

    public DoubleBallDuplexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_double_ball_duplex, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setListener();
        return view;
    }

    private void initView() {
        danBallAdapter = new DoubleBallRedAdapter(getContext(), chooseDanBalls, isShow, 33);
        tuoBallAdapter = new DoubleBallRedAdapter(getContext(), chooseTuoBalls, isShow, 33);
        blueBallAdapter = new DoubleBallBlueAdapter(getContext(), chooseBlueBalls, isShow, 16);

        danhaoRedGv.setAdapter(danBallAdapter);
        tuohaoRedGv.setAdapter(tuoBallAdapter);
        buleGv.setAdapter(blueBallAdapter);

        //重新设置高度
        ViewSetHinghUtil.resetGridViewHight7(danhaoRedGv);
        ViewSetHinghUtil.resetGridViewHight7(tuohaoRedGv);
        ViewSetHinghUtil.resetGridViewHight7(buleGv);

        //底部显示清空
        bottomResultChooseTv.setVisibility(View.GONE);
        bottomResultClearTv.setVisibility(View.VISIBLE);
    }

    private void setListener() {
        // 对胆号GridView进行监听，获得红球胆号选中的数。
        danhaoRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //限制球数
                if (limit(5,position,chooseDanBalls)){
                    ToastUtils.showToast("最多选择5个胆码");
                    return;
                }
                //判断是否包含此位置的球
                for (int i=0;i<chooseTuoBalls.size();i++){
                    if (chooseTuoBalls.get(i).equals(position+"")){
                        chooseTuoBalls.remove(i);
                        selectTuoNumber--;
                        tuoBallAdapter.updateData(isShow,chooseTuoBalls);
                    }
                }
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                danBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseDanBalls.clear();
                for (int i = 0; i < danhaoRedGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) danhaoRedGv.getChildAt(i).getTag();
                    if (danBallAdapter.hisSelected.get(i)) {
                        ++tempRed;
                        selectDanNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (danBallAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        chooseDanBalls.add(i + "");
                    }
                }

                //计算
                calculatorResult();
            }
        });
        // 对拖号GridView进行监听，获得红球拖号选中的数。
        tuohaoRedGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //限制球数
                if (limit(20,position,chooseDanBalls)){
                    ToastUtils.showToast("最多选择20个拖码");
                    return;
                }
                //判断是否此位置的球
                for (int i=0;i<chooseDanBalls.size();i++){
                    if (chooseDanBalls.get(i).equals(position+"")){
                        chooseDanBalls.remove(i);
                        selectDanNumber--;
                        danBallAdapter.updateData(isShow,chooseDanBalls);
                    }
                }
                // 在每次获取点击的item时将对应的checkbox状态改变，同时修改map的值。
                DoubleBallRedAdapter.redGridViewHolder vHolder = (DoubleBallRedAdapter.redGridViewHolder) view.getTag();
                vHolder.chkRed.toggle();
                tuoBallAdapter.getSelected().put(position, vHolder.chkRed.isChecked());
                int tempRed = 0;
                String hq = "";
                chooseTuoBalls.clear();
                for (int i = 0; i < tuohaoRedGv.getCount(); i++) {
                    DoubleBallRedAdapter.redGridViewHolder vHolder_red = (DoubleBallRedAdapter.redGridViewHolder) tuohaoRedGv.getChildAt(i).getTag();
                    if (tuoBallAdapter.hisSelected.get(i)) {
                        ++tempRed;
                        selectTuoNumber = tempRed;
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
                    } else {
                        vHolder_red.chkRed.setTextColor(ContextCompat.getColor(getContext(), R.color.red_ball));
                    }
                    if (tuoBallAdapter.getSelected().get(i)) {
                        hq = hq + (i + 1) + "  ";
                        chooseTuoBalls.add(i + "");
                    }
                }
                //计算
                calculatorResult();
            }
        });

        // 对篮球GridView进行监听，获取选球状态。
        buleGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DoubleBallBlueAdapter.LanGridViewHolder vHollder = (DoubleBallBlueAdapter.LanGridViewHolder) view.getTag();
                vHollder.chkBlue.toggle();
                blueBallAdapter.getSelected().put(position, vHollder.chkBlue.isChecked());
                int tempBlue = 0;
                String lq = "";
                chooseBlueBalls.clear();
                for (int i = 0; i < buleGv.getCount(); i++) {
                    DoubleBallBlueAdapter.LanGridViewHolder vHolder_Blue = (DoubleBallBlueAdapter.LanGridViewHolder) buleGv.getChildAt(i).getTag();
                    if (blueBallAdapter.lisSelected.get(i)) {
                        ++tempBlue;
                        selectBlueNumber = tempBlue;
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(android.R.color.white));
                    } else {
                        vHolder_Blue.chkBlue.setTextColor(getResources().getColor(R.color.blue_ball));
                    }

                    if (blueBallAdapter.getSelected().get(i)) {
                        lq = lq + (i + 1) + "  ";
                        chooseBlueBalls.add(i + "");
                    }
                }

                //计算
                calculatorResult();
            }
        });
    }


    private boolean limit(int count, int position, ArrayList<String> balls) {
        boolean isLimit = true;
        if (balls.size()>=count){
            for (int i=0;i<balls.size();i++){
                if (balls.get(i).equals(position+"")){
                    isLimit=false;
                }
            }
        }else {
            isLimit=false;
        }
        return isLimit;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bottom_result_clear_tv, R.id.bottom_result_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_result_clear_tv:
                //数据清空
                chooseDanBalls.clear();
                chooseTuoBalls.clear();
                chooseBlueBalls.clear();
                selectDanNumber = 0;
                selectTuoNumber = 0;
                selectBlueNumber = 0;
                //计算
                calculatorResult();
                //清除选中的球
                danBallAdapter.clearData();
                tuoBallAdapter.clearData();
                blueBallAdapter.clearData();
                break;
            case R.id.bottom_result_next_btn:
                break;
        }
    }

    //计算注数并显示
    private void calculatorResult() {
        zhushu = Calculator.calculateDanTuoNum(selectDanNumber,selectTuoNumber, selectBlueNumber);
        bottomResultCountTv.setText(String.valueOf(zhushu));
        bottomResultMoneyTv.setText(String.valueOf(zhushu * 2));
    }

}
