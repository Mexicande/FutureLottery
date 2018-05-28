package com.xinhe.haoyuncaipiao.ui.adapter;

import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.SuperLotto;

/**
 * Created by tantan on 2018/4/18.
 */

public class SuperLottoPaymentAdapter extends BaseQuickAdapter<SuperLotto,BaseViewHolder>{

    private String[] type=new String[]{"单式","复式","胆拖"};
    private ArrayList<SuperLotto> data;
    private int perMoney=2;

    public SuperLottoPaymentAdapter( @Nullable ArrayList<SuperLotto> data) {
        super(R.layout.choose_ball_payment_item, data);
        this.data=data;
    }

    public void updatePermoney(int perMoney){
        this.perMoney=perMoney;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SuperLotto item) {
        String balls="";
        String redBalls="";
        String blueBalls="";
        if (TextUtils.isEmpty(item.getDanRed())){
            redBalls=item.getRed().replace(","," ");
        }else {
            redBalls="("+item.getDanRed().replace(","," ")+")"+item.getRed().replace(","," ");
        }
        if (TextUtils.isEmpty(item.getDanBlu())){
            blueBalls=item.getBlu().replace(","," ");
        }else {
            blueBalls="("+item.getDanBlu().replace(","," ")+")"+item.getBlu().replace(","," ");
        }
        balls=redBalls+" "+blueBalls;
        //改变篮球的颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(balls);
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_ball));
        builder.setSpan(yellowSpan, balls.length()-blueBalls.length(),balls.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        helper.setText(R.id.payment_item_balls,builder)
                .addOnClickListener(R.id.payment_item_delet_iv);

        if (perMoney==2){
            helper.setText(R.id.payment_item_balls_type,type[item.getType()]+"  "+item.getZhushu()+"注 "+item.getMoney()+"元");
        }else {
            helper.setText(R.id.payment_item_balls_type,type[item.getType()]+"-追加  "+item.getZhushu()+"注 "+item.getZhushu()*perMoney+"元");
        }

    }
}
