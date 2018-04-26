package cn.com.futurelottery.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.BallInformation;

/**
 * Created by tantan on 2018/4/24.
 */

public class BallInformationAdapter extends BaseQuickAdapter<BallInformation,BaseViewHolder>{

    private ArrayList<BallInformation> data;

    public BallInformationAdapter(@Nullable ArrayList<BallInformation> data) {
        super(R.layout.ball_information_item, data);
        this.data=data;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final BallInformation item) {
        helper.setText(R.id.time_tv,"第"+item.getPhase()+"期 "+item.getEnd_time()+" ("+item.getWeek()+")");

        TextView ball1 = (TextView) helper.getView(R.id.ball_tv1);
        TextView ball2 = (TextView) helper.getView(R.id.ball_tv2);
        TextView ball3 = (TextView) helper.getView(R.id.ball_tv3);
        TextView ball4 = (TextView) helper.getView(R.id.ball_tv4);
        TextView ball5 = (TextView) helper.getView(R.id.ball_tv5);
        TextView ball6 = (TextView) helper.getView(R.id.ball_tv6);
        TextView ball7 = (TextView) helper.getView(R.id.ball_tv7);

        if (helper.getAdapterPosition()==0){
            ball1.setBackgroundResource(R.drawable.lottery_fragment_redball);
            ball1.setTextColor(mContext.getResources().getColor(R.color.white));
            ball2.setBackgroundResource(R.drawable.lottery_fragment_redball);
            ball2.setTextColor(mContext.getResources().getColor(R.color.white));
            ball3.setBackgroundResource(R.drawable.lottery_fragment_redball);
            ball3.setTextColor(mContext.getResources().getColor(R.color.white));
            ball4.setBackgroundResource(R.drawable.lottery_fragment_redball);
            ball4.setTextColor(mContext.getResources().getColor(R.color.white));
            ball5.setBackgroundResource(R.drawable.lottery_fragment_redball);
            ball5.setTextColor(mContext.getResources().getColor(R.color.white));
            if ("ssq".equals(item.getLotid())){
                ball6.setBackgroundResource(R.drawable.lottery_fragment_redball);
            }else {
                ball6.setBackgroundResource(R.drawable.lottery_fragment_blueball);
            }
            ball6.setTextColor(mContext.getResources().getColor(R.color.white));
            ball7.setBackgroundResource(R.drawable.lottery_fragment_blueball);
            ball7.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            ball1.setBackground(null);
            ball1.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            ball2.setBackground(null);
            ball2.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            ball3.setBackground(null);
            ball3.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            ball4.setBackground(null);
            ball4.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            ball5.setBackground(null);
            ball5.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            ball6.setBackground(null);
            if ("ssq".equals(item.getLotid())){
                ball6.setTextColor(mContext.getResources().getColor(R.color.red_ball));
            }else {
                ball6.setTextColor(mContext.getResources().getColor(R.color.blue_ball));
            }
            ball7.setBackground(null);;
            ball7.setTextColor(mContext.getResources().getColor(R.color.blue_ball));
        }
        String[] balls = item.getBonuscode().split(",");
        ball1.setText(balls[0]);
        ball2.setText(balls[1]);
        ball3.setText(balls[2]);
        ball4.setText(balls[3]);
        ball5.setText(balls[4]);
        ball6.setText(balls[5]);
        ball7.setText(balls[6]);
    }
}
