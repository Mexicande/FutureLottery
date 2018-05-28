package com.xinhe.haoyuncaipiao.ui.adapter.football;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import com.xinhe.haoyuncaipiao.R;
import com.xinhe.haoyuncaipiao.model.ScoreList;
import com.xinhe.haoyuncaipiao.view.topRightMenu.OnTopRightMenuItemClickListener;

/**
 *
 * @author apple
 * @date 2018/4/18
 * football 比分
 */

public class ScoreListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private OnTopRightMenuItemClickListener mItemClickListener;


    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public ScoreListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.football_item);
        addItemType(1, R.layout.score_layout);
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
                helper.setText(R.id.tv_name,matchBean.getLeague())
                        .setText(R.id.tv_week,matchBean.getWeek()+matchBean.getTeamid())
                        .setText(R.id.tv_endTime,matchBean.getEndtime()+"截止")
                        .setText(R.id.home_odds,matchBean.getHomeTeam())
                        .setText(R.id.away_odd,matchBean.getAwayTeam())
                        .addOnClickListener(R.id.layout_select)
                        ;
                View select = helper.getView(R.id.layout_select);
                String tvSelect = matchBean.getSelect();
                if(!TextUtils.isEmpty(tvSelect)){
                    select.setBackgroundColor(mContext.getResources().getColor(R.color.red_ball));
                    helper.setText(R.id.tv_score,tvSelect);
                    helper.setTextColor(R.id.tv_score,mContext.getResources().getColor(R.color.white));
                }else {
                    select.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    select.setBackground(mContext.getResources().getDrawable(R.drawable.bg_foot_layout));
                    helper.setText(R.id.tv_score,"点击选择比分");
                    helper.setTextColor(R.id.tv_score,mContext.getResources().getColor(R.color.black));

                }

                break;
            default:
                break;
        }

    }


}
