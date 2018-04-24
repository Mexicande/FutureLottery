package cn.com.futurelottery.ui.adapter.football;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.AwardPeriod;
import cn.com.futurelottery.model.FootBallList;

/**
 * Created by apple on 2018/4/22.
 */

public class FootChooseWinAdapter extends BaseQuickAdapter<FootBallList.DataBean.MatchBean,BaseViewHolder> {
    public FootChooseWinAdapter(@Nullable List<FootBallList.DataBean.MatchBean> data) {
        super(R.layout.foot_choose_win_item, data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final FootBallList.DataBean.MatchBean item) {
        int oddsBean = item.getOdds().size();
        //区别胜平负和让胜平负
        if(oddsBean>3){
            if(Integer.parseInt(item.getOdds().get(3).getOdds())>0){
                helper.setText(R.id.home_odds,item.getHomeTeam()+"+1");
            }else{
                helper.setText(R.id.home_odds,item.getHomeTeam()+"-1");
            }
        }else {
            helper.setText(R.id.home_odds,item.getHomeTeam());
        }
          helper .setText(R.id.tv_homedescribe,"主胜"+item.getOdds().get(0).getOdds())
                .setText(R.id.vs_describe,"平"+item.getOdds().get(1).getOdds())
                .setText(R.id.away_odd,item.getAwayTeam())
                .setText(R.id.away_describe,"客胜"+item.getOdds().get(2).getOdds())
                .addOnClickListener(R.id.iv_delete)
                .addOnClickListener(R.id.layout_home)
                .addOnClickListener(R.id.layout_vs)
                .addOnClickListener(R.id.layout_away)
                ;
        if(item.getHomeType()==1){
            helper.setBackgroundColor(R.id.layout_home,mContext.getResources().getColor(R.color.red_ball));
            helper.setTextColor(R.id.home_odds,mContext.getResources().getColor(R.color.white))
                    .setTextColor(R.id.tv_homedescribe,mContext.getResources().getColor(R.color.white));
        }else {
            helper.setBackgroundColor(R.id.layout_home,mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.home_odds,mContext.getResources().getColor(R.color.black))
                    .setTextColor(R.id.tv_homedescribe,mContext.getResources().getColor(R.color.color_333));
        }
        if(item.getAwayType()==1){
            helper.setBackgroundColor(R.id.layout_away,mContext.getResources().getColor(R.color.red_ball));
            helper.setTextColor(R.id.away_odd,mContext.getResources().getColor(R.color.white))
                    .setTextColor(R.id.away_describe,mContext.getResources().getColor(R.color.white));
        }else {
            helper.setBackgroundColor(R.id.layout_away,mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.away_odd,mContext.getResources().getColor(R.color.black))
                    .setTextColor(R.id.away_describe,mContext.getResources().getColor(R.color.color_333));
        }
        if(item.getVsType()==1){
            helper.setBackgroundColor(R.id.layout_vs,mContext.getResources().getColor(R.color.red_ball));
            helper.setTextColor(R.id.vs_odd,mContext.getResources().getColor(R.color.white))
                    .setTextColor(R.id.vs_describe,mContext.getResources().getColor(R.color.white));
        }else {
            helper.setBackgroundColor(R.id.layout_vs,mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.vs_odd,mContext.getResources().getColor(R.color.black))
                    .setTextColor(R.id.vs_describe,mContext.getResources().getColor(R.color.color_333));
        }

       /* View home = helper.getView(R.id.layout_home);
        View vs = helper.getView(R.id.layout_vs);
        View awaw = helper.getView(R.id.layout_away);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getHomeType()==0){
                    item.setHomeType(1);
                }else {
                    item.setHomeType(0);
                }
                notifyItemChanged(helper.getAdapterPosition());
            }
        });
        vs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getVsType()==0){
                    item.setVsType(1);
                }else {
                    item.setVsType(0);
                }
                notifyItemChanged(helper.getAdapterPosition());

            }
        });
        awaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getAwayType()==0){
                    item.setAwayType(1);
                }else {
                    item.setAwayType(0);
                }
                notifyItemChanged(helper.getAdapterPosition());
            }
        });*/
    }
}
