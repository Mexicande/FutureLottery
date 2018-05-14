package cn.com.futurelottery.ui.adapter.football;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.futurelottery.R;
import cn.com.futurelottery.model.FootBallList;
import cn.com.futurelottery.view.topRightMenu.OnTopRightMenuItemClickListener;

/**
 * Created by apple on 2018/4/18.
 * football 胜平负
 */

public class WinAndLoseAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private OnTopRightMenuItemClickListener mItemClickListener;

    private int nu=0;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public WinAndLoseAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.football_item);
        addItemType(1, R.layout.win_lose_item);
    }
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case TYPE_LEVEL_0:
                final FootBallList.DataBean bean= (FootBallList.DataBean) item;
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
                final FootBallList.DataBean.MatchBean matchBean= (FootBallList.DataBean.MatchBean) item;
                List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();
                helper.setText(R.id.tv_name,matchBean.getLeague())
                        .setText(R.id.tv_week,matchBean.getWeek()+matchBean.getTeamid())
                        .setText(R.id.tv_endTime,matchBean.getEndtime()+"截止")
                        .setText(R.id.home_odds,matchBean.getHomeTeam())
                        .setText(R.id.tv_homedescribe,"主胜 "+odds.get(0).getOdds())
                        .setText(R.id.vs_describe,"平 "+odds.get(1).getOdds())
                        .setText(R.id.away_odd,matchBean.getAwayTeam())
                        .setText(R.id.away_describe,"客胜 "+odds.get(2).getOdds())
                        ;
                View home = helper.getView(R.id.layout_home);
                View vs = helper.getView(R.id.layout_vs);
                View awaw = helper.getView(R.id.layout_away);

                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getHomeType()==0){
                            matchBean.setHomeType(1);
                        }else {
                            matchBean.setHomeType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());
                    }
                });
                vs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getVsType()==0){
                            matchBean.setVsType(1);
                        }else {
                            matchBean.setVsType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                awaw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getAwayType()==0){
                            matchBean.setAwayType(1);
                        }else {
                            matchBean.setAwayType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });

                if (matchBean.getHomeType()==0){
                        home.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.home_odds,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.tv_homedescribe,mContext.getResources().getColor(R.color.color_333));
                }else {
                    home.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.home_odds,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.tv_homedescribe,mContext.getResources().getColor(R.color.white));
                }
                if (matchBean.getVsType()==0){
                        vs.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.vs_odd,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.vs_describe,mContext.getResources().getColor(R.color.color_333));
                }else {
                    vs.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.vs_odd,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.vs_describe,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getAwayType()==0){
                    awaw.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.away_odd,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.away_describe,mContext.getResources().getColor(R.color.color_333));
                }else {
                    awaw.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.away_odd,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.away_describe,mContext.getResources().getColor(R.color.white));
                }
                if(matchBean.getOdds().size()>3){
                    helper.setVisible(R.id.layout_odd,true);
                    FootBallList.DataBean.MatchBean.OddsBean oddsBean = matchBean.getOdds().get(3);
                    int odd = Integer.parseInt(oddsBean.getOdds());
                    if(odd>0){
                        helper.setText(R.id.tv_conBall,"+"+odd);
                        helper.setBackgroundColor(R.id.layout_odd,mContext.getResources().getColor(R.color.red_ball));
                    }else {
                        helper.setText(R.id.tv_conBall,odd+"");
                        helper.setBackgroundColor(R.id.layout_odd,mContext.getResources().getColor(R.color.green_1A));
                    }
                }

                break;
            default:
                break;
        }

    }

    public void setOnTopRightMenuItemClickListener(OnTopRightMenuItemClickListener listener) {
        mItemClickListener = listener;
    }

}
