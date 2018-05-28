package com.xinhe.haoyuncaipiao.ui.adapter.football;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ScoreList;

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

        StringBuilder sb=new StringBuilder();

        if (null!=odds&&odds.size()>0){
            for (List<ScoreList.DataBean.MatchBean.OddsBean>ods:odds){
                if (null!=ods&&ods.size()>0){
                    for (ScoreList.DataBean.MatchBean.OddsBean od:ods){
                        if(od.getType()==1){
                            sb.append(od.getName()).append(" ");
                        }
                    }
                }
            }
        }


        helper.setText(R.id.tv_home,item.getHomeTeam())
                .setText(R.id.tv_away,item.getAwayTeam())
                .setText(R.id.tv_score,sb.toString())
                .addOnClickListener(R.id.tv_score)
                .addOnClickListener(R.id.iv_delete)
                    ;
    }
}
