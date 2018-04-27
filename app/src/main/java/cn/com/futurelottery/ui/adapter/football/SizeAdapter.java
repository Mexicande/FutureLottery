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

public class SizeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private OnTopRightMenuItemClickListener mItemClickListener;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public SizeAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.football_item);
        addItemType(1, R.layout.size_item);
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
                final List<FootBallList.DataBean.MatchBean.OddsBean> odds = matchBean.getOdds();


                helper.setText(R.id.tv_name,matchBean.getLeague())
                        .setText(R.id.tv_week,matchBean.getWeek()+matchBean.getTeamid())
                        .setText(R.id.tv_endTime,matchBean.getEndtime()+"截止")
                        .setText(R.id.home_odds,matchBean.getHomeTeam())
                        .setText(R.id.away_odd,matchBean.getAwayTeam())

                        .setText(R.id.name1,odds.get(0).getName())
                        .setText(R.id.name2,odds.get(1).getName())
                        .setText(R.id.name3,odds.get(2).getName())
                        .setText(R.id.name4,odds.get(3).getName())
                        .setText(R.id.name5,odds.get(4).getName())
                        .setText(R.id.name6,odds.get(5).getName())
                        .setText(R.id.name7,odds.get(6).getName())
                        .setText(R.id.name8,odds.get(7).getName())

                        .setText(R.id.odd1,odds.get(0).getOdds())
                        .setText(R.id.odd2,odds.get(1).getOdds())
                        .setText(R.id.odd3,odds.get(2).getOdds())
                        .setText(R.id.odd4,odds.get(3).getOdds())
                        .setText(R.id.odd5,odds.get(4).getOdds())
                        .setText(R.id.odd6,odds.get(5).getOdds())
                        .setText(R.id.odd7,odds.get(6).getOdds())
                        .setText(R.id.odd8,odds.get(7).getOdds())
                        /*.addOnClickListener(R.id.layout_home)
                        .addOnClickListener(R.id.layout_vs)
                        .addOnClickListener(R.id.layout_away)*/
                        ;
                View form1 = helper.getView(R.id.form1);
                View form2 = helper.getView(R.id.form2);
                View form3 = helper.getView(R.id.form3);
                View form4 = helper.getView(R.id.form4);
                View form5 = helper.getView(R.id.form5);
                View form6 = helper.getView(R.id.form6);
                View form7 = helper.getView(R.id.form7);
                View form8 = helper.getView(R.id.form8);

                form1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getFistfrom()==0){
                            matchBean.setFistfrom(1);
                            odds.get(0).setType(1);
                        }else {
                            matchBean.setFistfrom(0);
                            odds.get(0).setType(0);
                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());
                    }

                });
                form2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getSecondfrom()==0){
                            matchBean.setSecondfrom(1);
                            odds.get(1).setType(1);
                        }else {
                            matchBean.setSecondfrom(0);
                            odds.get(1).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                form3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getThirdfrom()==0){
                            matchBean.setThirdfrom(1);
                            odds.get(2).setType(1);

                        }else {
                            matchBean.setThirdfrom(0);
                            odds.get(2).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                form4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getFourthfrom()==0){
                            matchBean.setFourthfrom(1);
                            odds.get(3).setType(1);

                        }else {
                            matchBean.setFourthfrom(0);
                            odds.get(3).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                    }
                });
                form5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getFifthfrom()==0){
                            matchBean.setFifthfrom(1);
                            odds.get(4).setType(1);

                        }else {
                            matchBean.setFifthfrom(0);
                            odds.get(4).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                form6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getSixthfrom()==0){
                            matchBean.setSixthfrom(1);
                            odds.get(5).setType(1);

                        }else {
                            matchBean.setSixthfrom(0);
                            odds.get(5).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                form7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getSeventhfrom()==0){
                            matchBean.setSeventhfrom(1);
                            odds.get(6).setType(1);

                        }else {
                            matchBean.setSeventhfrom(0);
                            odds.get(6).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });
                form8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(matchBean.getEighthfrom()==0){
                            matchBean.setEighthfrom(1);
                            odds.get(7).setType(1);

                        }else {
                            matchBean.setEighthfrom(0);
                            odds.get(7).setType(0);

                        }
                        notifyItemChanged(helper.getAdapterPosition());
                        mItemClickListener.onTopRightMenuItemClick(helper.getAdapterPosition());

                    }
                });


                if (matchBean.getFistfrom()==0){
                        form1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name1,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd1,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form1.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name1,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd1,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getSecondfrom()==0){
                    form2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name2,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd2,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form2.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name2,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd2,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getThirdfrom()==0){
                    form3.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name3,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd3,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form3.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name3,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd3,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getFourthfrom()==0){
                    form4.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name4,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd4,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form4.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name4,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd4,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getFifthfrom()==0){
                    form5.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name5,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd5,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form5.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name5,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd5,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getSixthfrom()==0){
                    form6.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name6,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd6,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form6.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name6,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd6,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getSeventhfrom()==0){
                    form7.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name7,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd7,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form7.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name7,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd7,mContext.getResources().getColor(R.color.white));
                }

                if (matchBean.getEighthfrom()==0){
                    form8.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                        helper.setTextColor(R.id.name8,mContext.getResources().getColor(R.color.black));
                        helper.setTextColor(R.id.odd8,mContext.getResources().getColor(R.color.color_333));
                }else {
                    form8.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setTextColor(R.id.name8,mContext.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.odd8,mContext.getResources().getColor(R.color.white));
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
