package cn.com.futurelottery.ui.adapter.football;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.model.ScoreList;

/**
 * Created by apple on 2018/4/22.
 */

public class FootChooseScoreAdapter extends BaseQuickAdapter<ScoreList.DataBean.MatchBean,BaseViewHolder> {
    public FootChooseScoreAdapter(@Nullable List<ScoreList.DataBean.MatchBean> data) {
        super(R.layout.score_item_bet, data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final ScoreList.DataBean.MatchBean item) {

        List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = item.getOdds();

        List<ScoreList.DataBean.MatchBean.OddsBean> oddsHome = odds.get(0);
        List<ScoreList.DataBean.MatchBean.OddsBean> oddsVs = odds.get(1);
        List<ScoreList.DataBean.MatchBean.OddsBean> oddsAway = odds.get(2);

        StringBuilder sb=new StringBuilder();
        for(ScoreList.DataBean.MatchBean.OddsBean s:oddsHome){
            if(s.getType()==1){
                sb.append(s.getName()).append(" ");
            }
        }

        for(ScoreList.DataBean.MatchBean.OddsBean s:oddsVs){
            if(s.getType()==1){
                sb.append(" ").append(s.getName());
            }
        }

        for(ScoreList.DataBean.MatchBean.OddsBean s:oddsAway){
            if(s.getType()==1){
                sb.append(" ").append(s.getName());
            }
        }
        helper.setText(R.id.tv_name,item.getHomeTeam())
                .setText(R.id.tv_away,item.getAwayTeam())
                .setText(R.id.tv_score,sb.toString());
    }
}
