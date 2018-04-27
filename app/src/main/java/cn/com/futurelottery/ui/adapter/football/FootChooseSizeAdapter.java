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

public class FootChooseSizeAdapter extends BaseQuickAdapter<FootBallList.DataBean.MatchBean,BaseViewHolder> {
    public FootChooseSizeAdapter(@Nullable List<FootBallList.DataBean.MatchBean> data) {
        super(R.layout.score_item_bet, data);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final FootBallList.DataBean.MatchBean item) {

        List<FootBallList.DataBean.MatchBean.OddsBean> odds = item.getOdds();
        StringBuilder sb=new StringBuilder();
        if(item.getFistfrom()==1){
            sb.append(odds.get(0).getName()).append(" ");
        }
        if(item.getSecondfrom()==1){
            sb.append(odds.get(1).getName()).append(" ");
        }
        if(item.getThirdfrom()==1){
            sb.append(odds.get(2).getName()).append(" ");
        }
        if(item.getFourthfrom()==1){
            sb.append(odds.get(3).getName()).append(" ");
        }
        if(item.getFistfrom()==1){
            sb.append(odds.get(4).getName()).append(" ");
        }
        if(item.getSixthfrom()==1){
            sb.append(odds.get(5).getName()).append(" ");
        }
        if(item.getSeventhfrom()==1){
            sb.append(odds.get(6).getName()).append(" ");
        }
        if(item.getEighthfrom()==1){
            sb.append(odds.get(7).getName()).append(" ");
        }

        helper.setText(R.id.tv_home,item.getHomeTeam())
                .setText(R.id.tv_away,item.getAwayTeam())
                .setText(R.id.tv_score,sb.toString())
                .addOnClickListener(R.id.tv_score)
                .addOnClickListener(R.id.iv_delete)
                    ;
    }
}
