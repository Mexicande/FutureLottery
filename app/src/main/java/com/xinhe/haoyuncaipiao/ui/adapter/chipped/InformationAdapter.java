package com.xinhe.haoyuncaipiao.ui.adapter.chipped;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ChippedDetail;

/**
 * Created by tantan on 2018/5/18.
 */

public class InformationAdapter extends BaseQuickAdapter<ChippedDetail.DataProduct,BaseViewHolder>{

    public InformationAdapter(@Nullable List<ChippedDetail.DataProduct> data) {
        super(R.layout.information_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChippedDetail.DataProduct item) {
        String playType = "";
        //101=单式 102=复式 103=胆拖 大乐透 101=单式 102=复式 106=胆拖 103=追加单式 104=追加复式 107=追加胆拖
        switch (item.getPlay_type()){
            case "101":
                playType="单式";
                break;
            case "102":
                playType="复式";
                break;
            case "103":
                playType="胆拖";
                break;
            case "104":
                playType="追加复式";
                break;
            case "106":
                playType="胆拖";
                break;
            case "107":
                playType="追加胆拖";
                break;
            case "201":
                playType="直选";
                break;
            case "202":
                playType="组六单式";
                break;
            case "203":
                playType="组三复式";
                break;
            case "204":
                playType="组六复式";
                break;
            case "221":
                playType="直选复式";
                break;
            case "231":
                playType="组三复式";
                break;
            case "233":
                playType="组六复式 ";
                break;
            case "215":
                playType="直选位选";
                break;
        }

        String red = "";
        String blue = "";
        String name="";
        String chooseBalls = item.getBouns();

        switch (item.getLotid()){
            case "ssq":
                name="双色球";
                String[] arrBalls1 = chooseBalls.split("#");
                String[] redBalls1 = arrBalls1[0].split("[$]");
                String[] blueBalls1 = arrBalls1[1].split("[$]");
                if (redBalls1.length>1){
                    red="("+redBalls1[0].replace(","," ")+")"+redBalls1[1].replace(","," ");
                }else {
                    red=redBalls1[0].replace(","," ");
                }
                if (blueBalls1.length>1){
                    blue="("+blueBalls1[0].replace(","," ")+")"+blueBalls1[1].replace(","," ");
                }else {
                    blue=blueBalls1[0].replace(","," ");
                }
                if(!TextUtils.isEmpty(blue)){
                    SpannableString spannableString1 = new SpannableString(red+"\n"+blue);
                    spannableString1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_ball)), red.length(), red.length()+blue.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.ball_red_tv,spannableString1);
                }else {
                    helper.setText(R.id.ball_red_tv,red);
                }
                break;
            case "dlt":
                name="大乐透";
                String[] arrBalls = chooseBalls.split("#");
                String[] redBalls = arrBalls[0].split("[$]");
                String[] blueBalls = arrBalls[1].split("[$]");
                if (redBalls.length>1){
                    red="("+redBalls[0].replace(","," ")+")"+redBalls[1].replace(","," ");
                }else {
                    red=redBalls[0].replace(","," ");
                }
                if (blueBalls.length>1){
                    blue="("+blueBalls[0].replace(","," ")+")"+blueBalls[1].replace(","," ");
                }else {
                    blue=blueBalls[0].replace(","," ");
                }
                if(!TextUtils.isEmpty(blue)){
                    SpannableString spannableString1 = new SpannableString(red+"\n"+blue);
                    spannableString1.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.blue_ball)), red.length(), red.length()+blue.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    helper.setText(R.id.ball_red_tv,spannableString1);
                }else {
                    helper.setText(R.id.ball_red_tv,red);
                }
                break;
            case "p3":
                //篮球消失
                name="排列3";
                if ("201".equals(item.getPlay_type())){
                    String[] balls = chooseBalls.split(",");
                    for (int i=0;i<balls.length;i++){
                        char[] cha = balls[i].toCharArray();
                        String a="";
                        for (int j=0;j<cha.length;j++){
                            if (j==0){
                                a=a+cha[j];
                            }else {
                                a=a+","+cha[j];
                            }
                        }
                        if (i==0){
                            red=red+a;
                        }else {
                            red=red+"|"+a;
                        }
                    }
                }else {
                    red=chooseBalls;
                }
                helper.setText(R.id.ball_red_tv,red);
                helper.setText(R.id.ball_red_tv,red);

                break;
            case "p5":
                //篮球消失
                name="排列5";
                String[] balls1 = chooseBalls.split(",");
                for (int i=0;i<balls1.length;i++){
                    char[] cha = balls1[i].toCharArray();
                    String a="";
                    for (int j=0;j<cha.length;j++){
                        if (j==0){
                            a=a+cha[j];
                        }else {
                            a=a+","+cha[j];
                        }
                    }
                    if (i==0){
                        red=red+a;
                    }else {
                        red=red+"|"+a;
                    }
                }
                helper.setText(R.id.ball_red_tv,red);

                break;
            case "3d":
                //篮球消失
                name="3D";
                if ("201".equals(item.getPlay_type())||"215".equals(item.getPlay_type())||"221".equals(item.getPlay_type())){
                    String[] balls = chooseBalls.split(",");
                    for (int i=0;i<balls.length;i++){
                        char[] cha = balls[i].toCharArray();
                        String a="";
                        for (int j=0;j<cha.length;j++){
                            if (j==0){
                                a=a+cha[j];
                            }else {
                                a=a+","+cha[j];
                            }
                        }
                        if (i==0){
                            red=red+a;
                        }else {
                            red=red+"|"+a;
                        }
                    }
                }else {
                    red=chooseBalls;
                }
                helper.setText(R.id.ball_red_tv,red);

                break;
        }

        //是否中奖
        if ("2".equals(item.getType())){
            helper.setVisible(R.id.win_iv,true);
        }else {
            helper.setVisible(R.id.win_iv,false);
        }

        helper.setText(R.id.type_tv,name)
        .setText(R.id.count_tv,playType+item.getMultiple()+"倍");


    }
}
