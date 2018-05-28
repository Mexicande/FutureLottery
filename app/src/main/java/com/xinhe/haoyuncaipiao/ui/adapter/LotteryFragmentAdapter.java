package com.xinhe.haoyuncaipiao.ui.adapter;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexboxLayout;
import com.xinhe.haoyuncaipiao.model.LotteryInformation;

import java.util.Arrays;
import java.util.List;

import com.xinhe.haoyuncaipiao.R;

/**
 * Created by tantan on 2018/5/12.
 */

public class LotteryFragmentAdapter extends BaseQuickAdapter<LotteryInformation.DataProduct,BaseViewHolder> {

    public LotteryFragmentAdapter(@Nullable List<LotteryInformation.DataProduct> data) {
        super(R.layout.lottery_fragment_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryInformation.DataProduct item) {

        FlexboxLayout flexboxLayout = helper.getView(R.id.dlt_FlexboxLayout);
        helper.setText(R.id.superLotto_name_tv,item.getName());
        switch (item.getLotid()) {
            case "ssq":
                helper.setVisible(R.id.superLotto_rl,true);
                helper.setVisible(R.id.footBall_rl,false);
                textFlex(item, 1, flexboxLayout);
                helper.setText(R.id.superLotto_time_tv,"第" + item.getPhase() + "期 " + item.getEnd_time() + " (" + item.getWeek() + ")");
                break;
            case "dlt":
                helper.setVisible(R.id.superLotto_rl,true);
                helper.setVisible(R.id.footBall_rl,false);
                textFlex(item, 2, flexboxLayout);
                helper.setText(R.id.superLotto_time_tv,"第" + item.getPhase() + "期 " + item.getEnd_time() + " (" + item.getWeek() + ")");
                break;
            case "p3":
                helper.setVisible(R.id.superLotto_rl,true);
                helper.setVisible(R.id.footBall_rl,false);
                textFlex(item, 0, flexboxLayout);
                helper.setText(R.id.superLotto_time_tv,"第" + item.getPhase() + "期 " + item.getEnd_time() + " (" + item.getWeek() + ")");
                break;
            case "p5":
                helper.setVisible(R.id.superLotto_rl,true);
                helper.setVisible(R.id.footBall_rl,false);
                textFlex(item, 0, flexboxLayout);
                helper.setText(R.id.superLotto_time_tv,"第" + item.getPhase() + "期 " + item.getEnd_time() + " (" + item.getWeek() + ")");
                break;
            case "3d":
                helper.setVisible(R.id.superLotto_rl,true);
                helper.setVisible(R.id.footBall_rl,false);
                textFlex(item, 0, flexboxLayout);
                helper.setText(R.id.superLotto_time_tv,"第" + item.getPhase() + "期 " + item.getEnd_time() + " (" + item.getWeek() + ")");
                break;
            case "ftb":
                helper.setVisible(R.id.superLotto_rl,false);
                helper.setVisible(R.id.footBall_rl,true);
                helper.setText(R.id.footBall_tv1,item.getFront());
                helper.setText(R.id.footBall_tv2,item.getResult());
                helper.setText(R.id.footBall_tv3,item.getAfter());
                break;
        }
    }
    private void textFlex(LotteryInformation.DataProduct dataProduct, int type, FlexboxLayout mFlexboxLayout) {
        String bonuscode = dataProduct.getBonuscode();
        String[] split = bonuscode.split(",");

        List<String> strings = Arrays.asList(split);
        for (int i = 0; i < strings.size(); i++) {
            TextView textView = new TextView(mContext);
            textView.setText(strings.get(i));
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(5, 5, 5, 5);
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            mFlexboxLayout.addView(textView);
            if (type == 1) {
                if (i < strings.size() - 1) {
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_redball));
                } else {
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_blueball));
                }
            } else if (type == 2) {
                if (i < strings.size() - 2) {
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_redball));
                } else {
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_blueball));
                }
            }else {
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_redball));
            }
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            if (params instanceof FlexboxLayout.LayoutParams) {
                FlexboxLayout.LayoutParams layoutParams = (FlexboxLayout.LayoutParams) params;
                layoutParams.setFlexGrow(0f);
                if(i==strings.size()-type){
                    layoutParams.setMargins(50, 0, 10, 0);
                }else {
                    layoutParams.setMargins(10, 0, 10, 0);
                }
            }
        }

    }
}
