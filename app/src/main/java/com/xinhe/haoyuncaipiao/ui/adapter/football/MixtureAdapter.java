package com.xinhe.haoyuncaipiao.ui.adapter.football;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ScoreList;
import com.xinhe.haoyuncaipiao.view.topRightMenu.OnTopRightMenuItemClickListener;

/**
 * Created by tantan on 2018/5/10.
 * 混合投注
 */

public class MixtureAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private OnTopRightMenuItemClickListener mItemClickListener;

    private int nu=0;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public MixtureAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.football_item);
        addItemType(1, R.layout.football_mixture_item);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_LEVEL_0:
                final ScoreList.DataBean bean= (ScoreList.DataBean) item;
                final int pos = helper.getAdapterPosition();
                helper.setImageResource(R.id.iv_arrow, bean.isExpanded() ? R.mipmap.iv_up_arrow : R.mipmap.iv_down_arrow);
                helper.setText(R.id.tv_title,bean.getDate()+" "+bean.getWeek()+" "+bean.getCount()+"场比赛可投");
                if(helper.getAdapterPosition()==0){
                    helper.setVisible(R.id.tv_hot,true);
                }else {
                    helper.setVisible(R.id.tv_hot,false);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bean.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }});
                break;
            case TYPE_LEVEL_1:
                final ScoreList.DataBean.MatchBean matchBean= (ScoreList.DataBean.MatchBean) item;
                final List<ScoreList.DataBean.MatchBean.OddsBean> odds1 = matchBean.getOdds().get(0);
                final List<ScoreList.DataBean.MatchBean.OddsBean> odds2 = matchBean.getOdds().get(1);
                helper.setText(R.id.tv_name,matchBean.getLeague())
                        .setText(R.id.tv_week,matchBean.getWeek()+matchBean.getTeamid())
                        .setText(R.id.tv_endTime,matchBean.getEndtime()+"截止")
                        .setText(R.id.team_name1,matchBean.getHomeTeam())
                        .setText(R.id.team_name2,matchBean.getAwayTeam())
                        .addOnClickListener(R.id.unfold_tv);
                if (null!=odds1&&odds1.size()>0){
                    helper.setText(R.id.tv_home_odds1,"主胜 "+odds1.get(0).getOdds())
                            .setText(R.id.tv_equal_odds1,"平 "+odds1.get(1).getOdds())
                            .setText(R.id.tv_customer_odds1,"客胜 "+odds1.get(2).getOdds());
                }
                if (null!=odds2&&odds2.size()>0){
                    helper.setText(R.id.tv_home_odds2,"主胜"+odds2.get(0).getOdds())
                            .setText(R.id.tv_equal_odds2,"平"+odds2.get(1).getOdds())
                            .setText(R.id.tv_customer_odds2,"客胜"+odds2.get(2).getOdds());
                }


                TextView home1 = helper.getView(R.id.tv_home_odds1);
                TextView vs1 = helper.getView(R.id.tv_equal_odds1);
                TextView awaw1 = helper.getView(R.id.tv_customer_odds1);
                TextView home2 = helper.getView(R.id.tv_home_odds2);
                TextView vs2 = helper.getView(R.id.tv_equal_odds2);
                TextView awaw2 = helper.getView(R.id.tv_customer_odds2);

                home1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getHomeType1()==0){
                            matchBean.setHomeType1(1);
                            odds1.get(0).setType(1);
                        }else {
                            matchBean.setHomeType1(0);
                            odds1.get(0).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());
                    }
                });
                vs1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getVsType1()==0){
                            matchBean.setVsType1(1);
                            odds1.get(1).setType(1);
                        }else {
                            matchBean.setVsType1(0);
                            odds1.get(1).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                awaw1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getAwayType1()==0){
                            matchBean.setAwayType1(1);
                            odds1.get(2).setType(1);
                        }else {
                            matchBean.setAwayType1(0);
                            odds1.get(2).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });

                home2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getHomeType2()==0){
                            matchBean.setHomeType2(1);
                            odds2.get(0).setType(1);
                        }else {
                            matchBean.setHomeType2(0);
                            odds2.get(0).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());
                    }
                });
                vs2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getVsType2()==0){
                            matchBean.setVsType2(1);
                            odds2.get(1).setType(1);
                        }else {
                            matchBean.setVsType2(0);
                            odds2.get(1).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                awaw2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getAwayType2()==0){
                            matchBean.setAwayType2(1);
                            odds2.get(2).setType(1);
                        }else {
                            matchBean.setAwayType2(0);
                            odds2.get(2).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });

                if (matchBean.getHomeType1()==0){
                    home1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    home1.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    home1.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    home1.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                if (matchBean.getVsType1()==0){
                    vs1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    vs1.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    vs1.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    vs1.setTextColor(mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getAwayType1()==0){
                    awaw1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    awaw1.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    awaw1.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    awaw1.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                if (matchBean.getHomeType2()==0){
                    home2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    home2.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    home2.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    home2.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                if (matchBean.getVsType2()==0){
                    vs2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    vs2.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    vs2.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    vs2.setTextColor(mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getAwayType2()==0){
                    awaw2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    awaw2.setTextColor(mContext.getResources().getColor(R.color.black));
                }else {
                    awaw2.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    awaw2.setTextColor(mContext.getResources().getColor(R.color.white));
                }
                //让球
                if(matchBean.getOdds().size()>3){
                    ScoreList.DataBean.MatchBean.OddsBean oddsBean = matchBean.getOdds().get(1).get(3);
                    int odd = Integer.parseInt(oddsBean.getOdds());
                    if(odd>0){
                        helper.setText(R.id.tv_con1,"+"+odd);
                        helper.setBackgroundColor(R.id.tv_con1,mContext.getResources().getColor(R.color.red_ball));
                    }else {
                        helper.setText(R.id.tv_con1,"-"+odd);
                        helper.setBackgroundColor(R.id.tv_con1,mContext.getResources().getColor(R.color.green_1A));
                    }
                }
                //展开全部
                TextView select = helper.getView(R.id.unfold_tv);
                int tvSelect = 0;
                if (null!=matchBean){
                    List<List<ScoreList.DataBean.MatchBean.OddsBean>> odds = matchBean.getOdds();
                    if (null!=odds){
                        for (int i=0;i<odds.size();i++){
                            List<ScoreList.DataBean.MatchBean.OddsBean> ods = odds.get(i);
                            if (null!=ods){
                                for (ScoreList.DataBean.MatchBean.OddsBean s : ods) {
                                    if (s.getType() == 1) {
                                        tvSelect+=1;
                                    }
                                }
                            }
                        }
                    }
                }

                if(0!=tvSelect){
                    select.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color_b3));
                    helper.setText(R.id.unfold_tv,"已选"+tvSelect+"项");
                }else {
                    select.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    select.setBackground(mContext.getResources().getDrawable(R.drawable.bg_foot_layout));
                    helper.setText(R.id.unfold_tv,"展开全部");
                }

                break;
        }

    }

    public void setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mItemClickListener = listener;
    }
}
